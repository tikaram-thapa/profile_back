package com.profile.profile_back.profile.dtos;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

// @AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileDto {
    @JsonProperty("firstName")
    private String fname;
    @JsonProperty("lastName")
    private String lname;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    @JsonProperty("title")
    private String jobTitle;
    private String bio;
    private String avatarUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Autowired
    public ProfileDto(
        String fname,
        String lname,
        String fullName,
        String phone,
        String email,
        String address,
        String jobTitle,
        String bio,
        String avatarUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.fname = fname;
        this.lname = lname;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getFullName() {
        return fullName;
    }
}
