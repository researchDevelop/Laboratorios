package com.meetups.todoservice.repository;

import com.meetups.todoservice.pojo.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TodoRespository extends MongoRepository<Todo, String> {
}
