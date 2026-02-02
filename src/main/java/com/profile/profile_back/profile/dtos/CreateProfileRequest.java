package com.profile.profile_back.profile.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateProfileRequest {
    private Long userId;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String address;
    @JsonProperty("title")
    private String jobTitle;
    private String bio;
    private String avatarUrl;

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fname + " " + lname;
    }
}
