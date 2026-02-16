package com.profile.profile_back.profile.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.profile.profile_back.profile.dtos.CreateProfileRequest;
import com.profile.profile_back.profile.dtos.ProfileDto;
import com.profile.profile_back.profile.dtos.ProfileDtoResponse;
import com.profile.profile_back.profile.dtos.UpdateProfileRequest;
import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;
import com.profile.profile_back.profile.services.ImageService;
import com.profile.profile_back.profile.services.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;
    private final ImageService imageService;

    public ProfileController(
        ProfileRepository profileRepository,
        ProfileService profileService,
        ImageService imageService
    ) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.imageService = imageService;
    }

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

    @PostMapping("/{id}/avatar")
    public ResponseEntity<ProfileDtoResponse> uploadAvatarImage(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            int responseCode = imageService.uploadAvatar(id, file);
            if (responseCode == 200) {
                return ResponseEntity.ok().body(new ProfileDtoResponse(200, "Avatar uploaded successfully.", null));
            } else {
                return ResponseEntity.ok().body(new ProfileDtoResponse(responseCode, "Failed to upload avatar.", null));
            }
        } catch (IOException e) {
            return ResponseEntity.ok().body(new ProfileDtoResponse(500, "Error on uploading avatar: " + e.getMessage(), null));
        }
    }

    @GetMapping("{id}/avatar-url")
    public ResponseEntity<ProfileDtoResponse> getAvatarUrl(@PathVariable Long id) {
        try {
            String avatarUrl = imageService.getAvatarUrl(id);
            if (avatarUrl == null) {
                return ResponseEntity.ok().body(new ProfileDtoResponse(404, "Avatar not found for profile.", null));
            } else {
                return ResponseEntity.ok().body(new ProfileDtoResponse(200, "Avatar URL retrieved successfully.", null, avatarUrl));
            }
            
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ProfileDtoResponse(500, "Error retrieving avatar URL: " + e.getMessage(), null));
        }
    }
    
    
}
