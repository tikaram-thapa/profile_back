package com.profile.profile_back.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class UserDtoResponse {
    private final int statusCode;
    private final String response;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final UserDto user;

    public UserDtoResponse(int statusCode, String response, UserDto user) {
        this.statusCode = statusCode;
        this.response = response;
        this.user = user;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponse() {
        return response;
    }

    public UserDto getUser() {
        return user;
    }
}
