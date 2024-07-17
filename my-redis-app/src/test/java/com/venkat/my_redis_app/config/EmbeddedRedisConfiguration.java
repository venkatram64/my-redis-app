package com.venkat.my_redis_app.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;

@TestConfiguration
@Profile("test")
public class EmbeddedRedisConfiguration {

    private RedisServer redisServer;

    @Autowired
    public EmbeddedRedisConfiguration(RedisProperties redisProperties){
        try{
            this.redisServer = new RedisServer(redisProperties.getPort());
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}
