package com.profile.profile_back.profile.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
// @AllArgsConstructor
public class ProfileDtoResponse {
    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfileDto data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatarUrl;

    @Autowired
    public ProfileDtoResponse(int statusCode, String message, ProfileDto data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    @Autowired
    public ProfileDtoResponse(int statusCode, String message, ProfileDto data, String avatarUrl) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.avatarUrl = avatarUrl;
    }
}
