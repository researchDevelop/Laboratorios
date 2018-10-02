package com.meetups.todoservice.repository;

import com.meetups.todoservice.pojo.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TodoRespository extends MongoRepository<Todo, String> {

    /**
     * Busqueda por Id de Usuario
     * @param userId
     * @return List<Todo>
     */
    @Query("{userId : ?0}")
    List<Todo> findAllByUserId(String userId);
}
