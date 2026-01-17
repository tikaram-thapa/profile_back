package com.profile.profile_back;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "Welcome to the Profile Back Application!";
    }
}
