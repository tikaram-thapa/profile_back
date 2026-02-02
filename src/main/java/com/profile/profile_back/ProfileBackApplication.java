package com.profile.profile_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.profile.profile_back.user.controllers.HomeController;

@SpringBootApplication
public class ProfileBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileBackApplication.class, args);
		HomeController home = new HomeController();
		home.index();
	}

}
