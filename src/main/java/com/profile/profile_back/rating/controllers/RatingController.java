package com.profile.profile_back.rating.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;
import com.profile.profile_back.rating.dtos.CreateRatingRequest;
import com.profile.profile_back.rating.dtos.RatingDtoResponse;
import com.profile.profile_back.rating.entities.Rating;
import com.profile.profile_back.rating.repositories.RatingRepository;
import com.profile.profile_back.rating.services.RatingService;


@RestController
@RequestMapping("ratings")
public class RatingController {
    private final RatingService ratingService;
    private final RatingRepository ratingRepository;
    private final ProfileRepository profileRepository;

    public RatingController(RatingService ratingService, RatingRepository ratingRepository, ProfileRepository profileRepository) {
        this.ratingService = ratingService;
        this.ratingRepository = ratingRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping()
    public ResponseEntity<RatingDtoResponse> createRating(
        @RequestBody CreateRatingRequest request,
        UriComponentsBuilder builder
    ) {
        try {
            Rating rating = ratingService.toProfileEntity(request);
            if (rating == null) {
                return ResponseEntity.ok(new RatingDtoResponse(404, "Profile not found", null));
            }
            ratingRepository.save(rating);
            var uri = builder.path("/profiles").buildAndExpand(rating.getId()).toUri();
            return ResponseEntity.created(uri).body(new RatingDtoResponse(201, "Rating created successfully", ratingService.toRatingDto(rating)));
        } catch (Exception e) {
            return ResponseEntity.ok(new RatingDtoResponse(500, "Failed to create rating: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDtoResponse> getRating(@PathVariable Long id) { // Get rating by ID
        try {
            Rating rating = ratingRepository.findById(id).orElse(null);
            if (rating == null) {
                return ResponseEntity.ok(new RatingDtoResponse(404, "Rating not found", null));
            }
            return ResponseEntity
                    .ok(new RatingDtoResponse(200, "Rating retrieved successfully", ratingService.toRatingDto(rating)));
        } catch (Exception e) {
            return ResponseEntity.ok(new RatingDtoResponse(500, "Failed to retrieve rating: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<RatingDtoResponse> average(@PathVariable Long id) { // Get average rating for a profile
        try {
            Profile profile = profileRepository.findById(id).orElse(null);
            if (profile == null) {
                return ResponseEntity.ok(new RatingDtoResponse(404, "Profile not found", null));
            }
            double averageRating = ratingService.getAverageRatingForProfile(profile);
            return ResponseEntity.ok(new RatingDtoResponse(200, "Average rating retrieved successfully", null, averageRating));
        } catch (Exception e) {
            return ResponseEntity.ok(new RatingDtoResponse(500, "Failed to retrieve rating stats: " + e.getMessage(), null));
        }
    }
    

}
