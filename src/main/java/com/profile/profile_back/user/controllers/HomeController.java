package com.profile.profile_back.user.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;

import com.profile.profile_back.user.dtos.Greeting;

@RestController
public class HomeController {
    @Value("${server.port}")
    private String applicationPort;

    @RequestMapping("/")
    public Greeting index() {
        System.out.println(
                "Application is running on http://localhost:" + (applicationPort != null ? applicationPort : "8080"));
        String greetingMessage = "Good Morning!";
        Long currentHour = LocalTime.now().getHour() * 1L; // Convert to Long for switch statement
        switch (currentHour.intValue()) {
            case 22, 23, 0, 1, 2, 3, 4, 5:
                greetingMessage = "Good Night!";
                break;
            case 6, 7, 8, 9, 10, 11:
                greetingMessage = "Good Morning!";
                break;
            case 12, 13, 14, 15, 16, 17:
                greetingMessage = "Good Afternoon!";
                break;
            case 18, 19, 20, 21:
                greetingMessage = "Good Evening!";
                break;
            default:
                break;
        }
        // System.out.println("Current hour: " + currentHour + ", Greeting: " + greetingMessage);
        return new Greeting(200, greetingMessage);
    }
}
