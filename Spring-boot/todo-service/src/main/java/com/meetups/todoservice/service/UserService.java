package com.meetups.todoservice.service;

import com.meetups.todoservice.pojo.User;
import com.meetups.todoservice.utils.UserServiceProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserService {

    private final RestTemplate restTemplate;
    private final UserServiceProperties userServiceProperties;

    /**
     * verifica la existencia del usuario
     * @param id usuario
     * @return boolean
     */
    public boolean existUser(String id){
        try {
            ResponseEntity<User> userResponseEntity = this.restTemplate.getForEntity(userServiceProperties.getUrl(), User.class, id);
            if (userResponseEntity.getStatusCode().is2xxSuccessful())
                return true;
            else
                return false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * verifica la existencia del usuario
     * @return boolean
     */
    public boolean pingService(){
        try {
            ResponseEntity<User> userResponseEntity = this.restTemplate.getForEntity(userServiceProperties.getUrlTest(), User.class);
            if (userResponseEntity.getStatusCode().is2xxSuccessful())
                return true;
            else
                return false;
        }catch (Exception e){
            return false;
        }
    }



    public UserService(RestTemplateBuilder builder, UserServiceProperties userServiceProperties) {
        this.restTemplate = builder.build();
        this.userServiceProperties = userServiceProperties;
    }

}
