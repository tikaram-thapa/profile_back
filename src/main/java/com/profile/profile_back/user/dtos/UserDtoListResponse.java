package com.profile.profile_back.user.dtos;

import lombok.Getter;

// @AllArgsConstructor
// @NoArgsConstructor
@Getter
public class UserDtoListResponse {
    private final int statusCode;
    private final String response;
    private final Iterable<UserDto> users;

    public UserDtoListResponse(int statusCode, String response, Iterable<UserDto> users) {
        this.statusCode = statusCode;
        this.response = response;
        this.users = users;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponse() {
        return response;
    }

    public Iterable<UserDto> getUsers() {
        return users;
    }
}
