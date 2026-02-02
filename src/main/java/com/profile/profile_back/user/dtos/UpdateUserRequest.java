package com.profile.profile_back.user.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String phone;
    private String email;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
