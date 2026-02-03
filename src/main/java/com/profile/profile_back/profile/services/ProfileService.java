package com.profile.profile_back.profile.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.profile.profile_back.profile.dtos.CreateProfileRequest;
import com.profile.profile_back.profile.dtos.ProfileDto;
import com.profile.profile_back.profile.dtos.UpdateProfileRequest;
import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.user.entities.User;
import com.profile.profile_back.user.repositories.UserRepository;

@Service
public class ProfileService {
    private final UserRepository profileUserRepository;

    // Spring automatically injects this repository
    public ProfileService(UserRepository profileUserRepository) {
        this.profileUserRepository = profileUserRepository;
    }

    public Profile toProfileEntity(CreateProfileRequest request) {
        User user = profileUserRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return null; // User not found
        }
        return new Profile(
            user,
            request.getFname(),
            request.getLname(),
            request.getFullName(),
            request.getPhone(),
            request.getEmail(),
            request.getAddress(),
            request.getJobTitle(),
            request.getBio(),
            request.getAvatarUrl()
        );
    }

    public ProfileDto toProfileDto(Profile profile) {
        return new ProfileDto(
            profile.getFname(),
            profile.getLname(),
            profile.getFullName(),
            profile.getPhone(),
            profile.getEmail(),
            profile.getAddress(),
            profile.getJobTitle(),
            profile.getBio(),
            profile.getAvatarUrl(),
            profile.getCreatedAt(),
            profile.getUpdatedAt()
        );
    }

    public Profile updateProfileEntity(Profile profile, UpdateProfileRequest request) {
        profile.setFname(request.getFname());
        profile.setLname(request.getLname());
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setEmail(request.getEmail());
        profile.setAddress(request.getAddress());
        profile.setJobTitle(request.getJobTitle());
        profile.setBio(request.getBio());
        profile.setUpdatedAt(LocalDateTime.now());
        return profile;
    }
}
