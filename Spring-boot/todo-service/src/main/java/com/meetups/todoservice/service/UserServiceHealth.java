package com.meetups.todoservice.service;

import com.meetups.todoservice.pojo.Todo;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceHealth implements HealthIndicator {

    private final UserService userService;

    public UserServiceHealth(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Health health(){
       try {
           boolean ping = this.userService.pingService();
           if (ping){
               return Health.up().build();
           }else{
               return Health.down().build();
           }
       }catch (Exception e){
           return Health.down(e).build();
       }
    }

}
