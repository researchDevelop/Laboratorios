package com.meetups.todoservice.controller;

import com.meetups.todoservice.pojo.Todo;
import com.meetups.todoservice.repository.TodoRespository;
import com.meetups.todoservice.service.UserService;
import com.meetups.todoservice.utils.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.ResponseEntity.*;

@Component
@RequestMapping("/v1/todo")
@Api(value = "TODO's CRUD", description = "Operaciones para la administracion de Todos")
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoRespository respository;
    private final UserService userService;

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "Obtiene Todo",
            notes = "Obtiene Todo",
            response = Todo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Todo", response = Todo.class),
            @ApiResponse(code = 204, message = "The resource you were trying to reach is not found", response = ResponseEntity.class)
            }
    )
    public ResponseEntity getTodo(@PathVariable String id){
        logger.info("Ejecutando consulta al todo :" +id);
        try{
            Optional<Todo> todoOptional = respository.findById(id);
            if (todoOptional.isPresent())
                return ok().body(todoOptional.get());
            else
                return noContent().build();
        }catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping(produces = "application/json",consumes = "application/json")
    @ApiOperation(value = "Genera un Todo",
            notes = "Inserta un Todo",
            response = Todo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creado", response = Todo.class),
            @ApiResponse(code = 304, message = "No modificado", response = ResponseEntity.class)
    }
    )
    public ResponseEntity createTodo(@Validated @RequestBody Todo todo){
        logger.info("Insertando todo : " +todo.toString());
        try{
            boolean existUser = userService.existUser(todo.getUserId());
            if (!existUser)
                throw new UserNotFoundException("Usuario no existe : "+todo.getUserId());
            Todo todoInserted = respository.insert(todo);
            if (todoInserted.get_id().isPresent()) {
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(todoInserted.get_id()).toUri();
                return created(location).build();
            } else
                return new ResponseEntity(NOT_MODIFIED);
        }catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(), NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Elimina un Todo",
            notes = "Elimina un Todo",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado", response = ResponseEntity.class),
            @ApiResponse(code = 304, message = "No modificado", response = ResponseEntity.class)
    }
    )
    public ResponseEntity deleteTodo(@PathVariable String id){
        logger.info("Eliminando todo : " +id);
        try{
            respository.deleteById(id);
            Optional<Todo> todoOptional = respository.findById(id);
            if (!todoOptional.isPresent())
                return ok().build();
            else
                return new ResponseEntity(NOT_MODIFIED);
        }catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(), NOT_MODIFIED);
        }
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ApiOperation(value = "Actualiza un Todo",
            notes = "Actualiza un Todo",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado", response = Todo.class),
            @ApiResponse(code = 204, message = "Not found", response = ResponseEntity.class),
            @ApiResponse(code = 304, message = "No modificado", response = ResponseEntity.class)
    }
    )
    public ResponseEntity updateTodo(@Validated @RequestBody Todo todo, @PathVariable String id){
        logger.info("Actualizando todo id : " +id);
        logger.info("Actualizando todo : " +todo.toString());
        try{
            Optional<Todo> todoOptional = this.respository.findById(id);
            if (!todoOptional.isPresent())
                return noContent().build();
            else{
                todo.set_id(new ObjectId(id));
                Todo savedTodo = respository.save(todo);
                return ok(savedTodo);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(), NOT_MODIFIED);
        }
    }

    @DeleteMapping
    @ApiOperation(value = "Elimina All",
            notes = "Elimina All",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado", response = ResponseEntity.class),
            @ApiResponse(code = 304, message = "No modificado", response = ResponseEntity.class)
    }
    )
    public ResponseEntity deleteAllTodo(){
        logger.info("Eliminando todo");
        try{
            respository.deleteAll();
            List<Todo> todoOptional = respository.findAll();
            if (todoOptional.isEmpty())
                return ok().build();
            else
                return new ResponseEntity(NOT_MODIFIED);
        }catch (Exception e){
            return new ResponseEntity(e.getLocalizedMessage(), NOT_MODIFIED);
        }
    }

    public TodoController(TodoRespository respository, UserService userService) {
        this.respository = respository;
        this.userService = userService;
    }
}

