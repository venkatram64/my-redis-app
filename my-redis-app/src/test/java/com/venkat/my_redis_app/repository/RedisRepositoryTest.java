package com.venkat.my_redis_app.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.venkat.my_redis_app.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;
import redis.embedded.core.RedisServerBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisRepositoryTest {

    private static RedisServer redisServer;

    @Autowired
    private RedisRepository<User> repository;

    private static String USERS_FILE_PATH = "src/test/resources/users.csv";

    private static final String collectionName = "User";

    List<User> users = null;

    public List<User> reader(){
        List<User> users = new ArrayList<>();
        try(Reader reader = Files.newBufferedReader(Paths.get(USERS_FILE_PATH));) {
            CsvToBean<User> userCsvToBean = new CsvToBeanBuilder(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<User> iterator = userCsvToBean.iterator();
            while(iterator.hasNext()){
                User csvUser = iterator.next();
                users.add(csvUser);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return users;
    }

    //starting redis server
    @BeforeAll
    public void setup() throws IOException {
        System.out.println("Server is going to start ");
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 256M").build();
        redisServer.start();
        users = reader();
        System.out.println("Server is started on port # 6379");
    }

    //stopping redis server
    @AfterAll
    public static void stopRedisServer() throws IOException {
        System.out.println("Server is going to stop ");
        if(redisServer != null) {
            redisServer.stop();
        }
        System.out.println("Server is stopped ");
    }

    @Test
    void save() {
        System.out.println("Size of Users "+ users.size());
        for(User user : users){
            User savedUser = repository.save(collectionName, user.getUserId(), user);
            System.out.println("User Object is "+ savedUser);
            assertNotNull(savedUser);
        }
    }

    @Test
    void update() {
        User user = new User("3", "Ram", "Ram", "ram@gmail.com");
        User updatedUser = repository.update(collectionName, user.getUserId(), user);
        System.out.println("Updated user " + updatedUser);
    }

    @Test
    void deleteObjectByKey() {
        boolean deletedUser = repository.deleteObjectByKey(collectionName, "3");
        assertTrue(deletedUser);
    }

    @Test
    void getAll() {
        for(User user : users){
            User savedUser = repository.save(collectionName, user.getUserId(), user);
            System.out.println("User Object is "+ savedUser);
        }
        List<User> users = repository.getAll(collectionName);
        assertTrue(users.size() > 0);
    }
}