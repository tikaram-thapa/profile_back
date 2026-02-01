package com.profile.profile_back.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String phone;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
