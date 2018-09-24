package com.meetups.todoservice.repository;

import com.meetups.todoservice.pojo.Todo;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.boot.actuate.health.Health.*;

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
               return up().build();
           }else{
               return down().build();
           }
       }catch (Exception e){
           return down(e).build();
       }
    }

}
