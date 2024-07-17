package com.venkat.my_redis_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("User")
public class User implements Serializable {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
}
