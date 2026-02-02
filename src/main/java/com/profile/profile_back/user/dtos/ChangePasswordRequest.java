package com.profile.profile_back.user.dtos;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }
}
