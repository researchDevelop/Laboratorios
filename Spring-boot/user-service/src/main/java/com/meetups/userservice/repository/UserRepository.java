package com.meetups.userservice.repository;

import com.meetups.userservice.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // este repositorio genera las acciones por defecto, (findAll, findOne, save, delete, insert)
    // se quiere una accion mas customizada se debe crear aqui un metodo ahdoc
}