package com.profile.profile_back.profile.services;

import org.springframework.stereotype.Service;

import com.profile.profile_back.profile.dtos.CreateProfileRequest;
import com.profile.profile_back.profile.dtos.ProfileDto;
import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileUserRepository;
import com.profile.profile_back.user.entities.User;

@Service
public class ProfileService {
    private final ProfileUserRepository profileUserRepository;

    // Spring automatically injects this repository
    public ProfileService(ProfileUserRepository profileUserRepository) {
        this.profileUserRepository = profileUserRepository;
    }

    public Profile createProfile(CreateProfileRequest request) {
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
}
