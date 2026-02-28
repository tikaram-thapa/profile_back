package com.profile.profile_back.user.dtos;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @AllArgsConstructor
@NoArgsConstructor
@Getter
public class Greeting {
    private int statusCode;
    private String message;

    public Greeting(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
