package com.meetups.userservice.repository;

import com.meetups.userservice.pojo.User;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoHealth implements HealthIndicator {

    private final UserRepository repository;

    public MongoHealth(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health(){
       try {
           List<User> users = this.repository.findAll();
           if (users != null){
               return Health.up().build();
           }else{
               return Health.down().build();
           }
       }catch (Exception e){
           return Health.down(e).build();
       }
    }

}
