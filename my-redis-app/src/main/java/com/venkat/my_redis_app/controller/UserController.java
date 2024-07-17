package com.venkat.my_redis_app.controller;

import com.venkat.my_redis_app.model.User;
import com.venkat.my_redis_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private String collection = "User";

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAll(collection);
        return ResponseEntity.ok(users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId){
        boolean isDeleted = userService.deleteUserById(userId);
        if(isDeleted){
            return ResponseEntity.ok("User is deleted!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
