package com.meetups.userservice.controller;

import com.meetups.userservice.pojo.User;
import com.meetups.userservice.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@Api(value = "User CRUD", description = "Operaciones para la administracion de Usuarios")
public class UserController {

    //inyectando dependencia hacia el repositorio
    @Autowired
    UserRepository userRepository;

    private ResponseEntity response = null;

    @GetMapping(value = {"", "/{id}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene usuario",
            notes = "Obtiene usuario",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved User", response = User.class),
            @ApiResponse(code = 204, message = "The resource you were trying to reach is not found", response = ResponseEntity.class)
    }
    )
    public ResponseEntity getUser(@PathVariable Optional<String> id) {
        Optional user;
        if (id.isPresent()) {
            user = this.userRepository.findById(id.get());
        } else {
            user = Optional.ofNullable(this.userRepository.findAll());
        }

        if (user.isPresent())
            response = new ResponseEntity(user.get(), HttpStatus.OK);
        else
            response = ResponseEntity.noContent().build();

        return response;
    }



    @PostMapping(produces = "application/json")
    @ApiOperation(value = "Inserta un nuevo usuario",
            notes = "Inserta un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created User", response = User.class),
            @ApiResponse(code = 204, message = "The resource you were trying to reach is not found", response = ResponseEntity.class)
    }
    )
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User insertUser = this.userRepository.insert(user);

        if (insertUser.get_id().isPresent())
            response = new ResponseEntity(insertUser, HttpStatus.CREATED);
        else
            response = new ResponseEntity(HttpStatus.NO_CONTENT);

        return response;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "Elimina un usuario",
            notes = "Elimina un usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted User"),
            @ApiResponse(code = 204, message = "The resource you were trying to reach is not found")
    }
    )
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean exist = this.userRepository.existsById(id);
        if (exist) {
            this.userRepository.deleteById(id);
            this.userRepository.deleteById(id);
            response = new ResponseEntity(HttpStatus.OK);
        } else
            response = ResponseEntity.noContent().build();

        return response;
    }

    @PatchMapping(produces = "application/json")
    @ApiOperation(value = "Actualiza un usuario",
            notes = "Actualiza un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated User", response = User.class),
            @ApiResponse(code = 204, message = "The resource you were trying to reach is not found", response = ResponseEntity.class)
    }
    )
    public ResponseEntity updateUser(@Valid @RequestBody User user) {
        User saveUser = this.userRepository.save(user);

        if (saveUser.get_id().isPresent())
            response = new ResponseEntity(saveUser, HttpStatus.OK);
        else
            response = new ResponseEntity(HttpStatus.NO_CONTENT);

        return response;
    }
}