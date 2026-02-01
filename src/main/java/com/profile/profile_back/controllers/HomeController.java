package com.profile.profile_back.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.profile_back.entities.Greeting;

@RestController
public class HomeController {
    @Value("${server.port}")
    private String applicationPort;

    @RequestMapping("/")
    public Greeting index() {
        System.out.println("Application is running on http://localhost:" + applicationPort);
        return new Greeting("Good Morning!");
    }
}
