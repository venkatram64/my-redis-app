package com.venkat.springJdbc.config;

import com.venkat.springJdbc.model.Employee;
import com.venkat.springJdbc.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EmployeeService employeeService;

    @PostConstruct
    public void preloadCache(){
        logger.info("Initializing the cache ");
        Cache myAppCache = cacheManager.getCache("myEmpAppCache");
        List<Employee> employees = employeeService.findAll();
        employees.forEach(emp -> {
            myAppCache.put(emp.id(), emp);
        });
        logger.info("Cache size is {}", employees.size());
    }
    /*
        minute field,hour field, day of month field, month field, day of week field
    */
    @Scheduled(cron = "0 */2 * * * *")//clear the cache for every 2 minutes
    @CacheEvict(value="myEmpAppCache", allEntries = true)
    public void clearMyEmpAppCache(){
        logger.info("Clearing the cache from myEmpAppCache");
    }

    /*@Scheduled(fixedRate = 15000, initialDelay = 15000)
    public void clearCache(){
        logger.info("Clearing the cache ");
        cacheManager.getCacheNames()
                .parallelStream()
                .forEach(name -> cacheManager.getCache(name).clear());
    }*/
}
