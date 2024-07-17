package com.venkat.my_redis_app.service;

import com.venkat.my_redis_app.model.User;
import com.venkat.my_redis_app.repository.RedisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private String collection = "User";

    private RedisRepository<User> repository;

    public UserService(RedisRepository<User> repository){
        this.repository = repository;
    }

    public User save(User user){
        User savedUser = this.repository.save(collection, user.getUserId(), user);
        return savedUser;
    }

    public User update(User user){
        User updatedUser = this.repository.update(collection, user.getUserId(), user);
        return updatedUser;
    }

    public boolean deleteUserById(String id){
        boolean isDeleted = this.repository.deleteObjectByKey(collection,id);
        return isDeleted;
    }

    public User getUserById(String id){
        User user = this.repository.getObjectByKey(collection, id);
        return user;
    }

    public List<User> getAll(String collection){
        return this.repository.getAll(collection);
    }
}
