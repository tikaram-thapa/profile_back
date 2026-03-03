package com.profile.profile_back.rating.services;

import org.springframework.stereotype.Service;

import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;
import com.profile.profile_back.rating.dtos.CreateRatingRequest;
import com.profile.profile_back.rating.dtos.RatingDto;
import com.profile.profile_back.rating.entities.Rating;
import com.profile.profile_back.rating.repositories.RatingRepository;

@Service
// @AllArgsConstructor
public class RatingService {
    private final ProfileRepository profileRepository;
    private final RatingRepository ratingRepository;

    public RatingService(ProfileRepository profileRepository, RatingRepository ratingRepository) {
        this.profileRepository = profileRepository;
        this.ratingRepository = ratingRepository;
    }

    public Rating toProfileEntity(CreateRatingRequest request) {
        Profile profile = profileRepository.findById(request.getProfileId()).orElse(null);
        if (profile == null) {
            return null; // Profile not found
        }
        return new Rating(
                profile,
                request.getRatingValue(),
                request.getComment());
    }

    public RatingDto toRatingDto(Rating rating) {
        return new RatingDto(
                rating.getRatingValue(),
                rating.getComment(),
                rating.getCreatedAt(),
                rating.getUpdatedAt());
    }

    public double getAverageRatingForProfile(Profile profile) {
        try {
            double average = ratingRepository.findAverageRatingByProfileId(profile.getId());
            if (average > 0) {
                return average;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public int getTotalReviews(Profile profile) {
        try {
            return ratingRepository.countByProfileId(profile.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
}
