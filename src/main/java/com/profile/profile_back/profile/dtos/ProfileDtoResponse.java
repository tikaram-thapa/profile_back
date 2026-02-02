package com.profile.profile_back.profile.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ProfileDtoResponse {
    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfileDto data;

    public ProfileDtoResponse(int statusCode, String message, ProfileDto data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
