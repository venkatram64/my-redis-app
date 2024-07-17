package com.venkat.springJdbc.service;

import com.venkat.springJdbc.model.Employee;
import com.venkat.springJdbc.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    //constructor injection
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee){
        return employeeRepository.save(employee);
    }

    public String delete(Integer id){
        employeeRepository.deleteById(id);
        return "deleted the record";
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }

    @Cacheable(value = "myEmpAppCache", key="#empId")
    public Employee findById(Integer empId){
        Optional<Employee> emp = employeeRepository.findById(empId);
        return emp.orElseThrow();
    }



}
