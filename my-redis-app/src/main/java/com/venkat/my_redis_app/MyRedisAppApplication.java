package com.venkat.my_redis_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyRedisAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRedisAppApplication.class, args);
	}

}
