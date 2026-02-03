package com.profile.profile_back.profile.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.profile.profile_back.profile.dtos.CreateProfileRequest;
import com.profile.profile_back.profile.dtos.ProfileDto;
import com.profile.profile_back.profile.dtos.ProfileDtoResponse;
import com.profile.profile_back.profile.dtos.UpdateProfileRequest;
import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;
import com.profile.profile_back.profile.services.ProfileService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("profiles")
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @PostMapping()
    public ResponseEntity<ProfileDtoResponse> createProfile(
        @RequestBody CreateProfileRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        try {
            Profile profileObject = profileService.toProfileEntity(request);
            if (profileObject == null) {
                // return ResponseEntity.internalServerError().body("Failed to create profile");
                return ResponseEntity.ok().body(new ProfileDtoResponse(500, "Failed to create profile, user not found.", null));
            }
            Profile profile = profileRepository.save(profileObject);
            ProfileDto profileDto = profileService.toProfileDto(profile);
            var uri = uriBuilder.path("/profiles/{id}").buildAndExpand(profile.getId()).toUri();
            return ResponseEntity.created(uri).body(new ProfileDtoResponse(201, "Profile created successfully.", profileDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ProfileDtoResponse(500, "Error on create profile: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDtoResponse> getProfileById(@PathVariable Long id) {
        try {
            Profile profile = profileRepository.findById(id).orElse(null);
            if (profile == null) {
                return ResponseEntity.ok().body(new ProfileDtoResponse(404, "Profile not found.", null));
            }
            ProfileDto profileDto = profileService.toProfileDto(profile);
            return ResponseEntity.ok().body(new ProfileDtoResponse(200, "Profile retrieved successfully.", profileDto));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ProfileDtoResponse(500, "Error retrieving profile: " + e.getMessage(), null));
        }
    
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDtoResponse> updateProfile (
        @PathVariable Long id, @RequestBody UpdateProfileRequest request
    ) {
        try {
            Profile profileObject = profileRepository.findById(id).orElse(null);
            if (profileObject == null) {
                return ResponseEntity.ok().body(new ProfileDtoResponse(404, "Profile not found.", null));
            }
            Profile profile = profileService.updateProfileEntity(profileObject, request);
            profileRepository.save(profile);
            ProfileDto profileDto = profileService.toProfileDto(profile);
            return ResponseEntity.ok().body(new ProfileDtoResponse(200, "Profile updated successfully.", profileDto));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ProfileDtoResponse(500, "Error on updating profile -> " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDtoResponse> deleteProfile(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.ok().body(new ProfileDtoResponse(404, "Profile not found.", null));
        }
        profileRepository.delete(profile);
        return ResponseEntity.ok().body(new ProfileDtoResponse(200, "Profile deleted successfully.", null));
    }
    
}
