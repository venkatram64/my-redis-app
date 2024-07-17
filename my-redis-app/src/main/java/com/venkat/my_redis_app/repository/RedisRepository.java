package com.venkat.my_redis_app.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class RedisRepository<T> {

    private RedisTemplate redisTemplate;
    private HashOperations<Object, Object, T> hashOperations;

    @Autowired
    public RedisRepository(RedisTemplate redisTemplate){
        log.info("| RedisRepository() constructor ");
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        log.info("redisTemplate = " + redisTemplate.toString() + " | hasOperations = " + this.hashOperations);
    }

    //saving the object into cache
    public T save(String collection, String key, T object){
        hashOperations.put(collection, key, object);
        return getObjectByKey(collection, key);
    }

    //updating the object in cache
    public T update(String collection, String key, T object){
        return save(collection, key, object);
    }

    //delete the object from cache
    public boolean deleteObjectByKey(String collection, String key){
        hashOperations.delete(collection, key);
        return !isExist(collection, key);
    }

    //get all object from cache
    public List<T> getAll(String collection){
        return hashOperations.values(collection);
    }

    /*public Map<String, T> getAll(String collection){
        return hashOperations.entries(collection);
    }*/
    public T getObjectByKey(String collection, String key){
        return hashOperations.get(collection, key);
    }

    public boolean isExist(String collection, String key){
        return hashOperations.hasKey(collection, key);
    }

}
