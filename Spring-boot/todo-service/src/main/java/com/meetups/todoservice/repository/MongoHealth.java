package com.meetups.todoservice.repository;

import com.meetups.todoservice.pojo.Todo;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoHealth implements HealthIndicator {

    private final TodoRespository repository;

    public MongoHealth(TodoRespository repository) {
        this.repository = repository;
    }

    @Override
    public Health health(){
       try {
           List<Todo> todos = this.repository.findAll();
           if (todos != null){
               return Health.up().build();
           }else{
               return Health.down().build();
           }
       }catch (Exception e){
           return Health.down(e).build();
       }
    }

}
