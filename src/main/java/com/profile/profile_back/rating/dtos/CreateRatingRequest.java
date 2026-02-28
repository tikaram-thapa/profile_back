package com.profile.profile_back.rating.dtos;

import lombok.Data;

@Data
public class CreateRatingRequest {
    private double ratingValue; // e.g., 1 to 5
    private String comment; // optional comment about the rating
    private Long profileId; // ID of the profile being rated

    public Long getProfileId() {
        return profileId;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public String getComment() {
        return comment;
    }
}
