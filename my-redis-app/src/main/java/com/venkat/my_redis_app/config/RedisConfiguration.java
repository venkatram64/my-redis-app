package com.venkat.my_redis_app.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Configuration
@Slf4j
@Profile("!test")
@EnableRedisRepositories
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.password}")
    private String redisPassword;

    /*@Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        log.info(getClass().getName() + " | jedisConnectionFactory() called....");
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        //configuration.setPassword(redisPassword);
        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder().usePooling().build();
        JedisConnectionFactory factory;
        try{
            factory = new JedisConnectionFactory(configuration, clientConfiguration);
            factory.afterPropertiesSet();
        }catch (Exception e){
            throw new JedisConnectionException(" Redis Connection error ", e.getCause());
        }
        return factory;
    }*/

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        log.info(getClass().getName() + " | jedisConnectionFactory() called....");
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);

        JedisConnectionFactory factory;
        try{
            factory = new JedisConnectionFactory(configuration);
        }catch (Exception e){
            throw new JedisConnectionException(" Redis Connection error ", e.getCause());
        }
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}
