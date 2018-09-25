package com.meetups.todoservice;

import com.meetups.todoservice.utils.UserServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableConfigurationProperties(UserServiceProperties.class)
public class TodoServiceApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
    }
	public static void main(String[] args) {
		SpringApplication.run(TodoServiceApplication.class, args);
	}
}
