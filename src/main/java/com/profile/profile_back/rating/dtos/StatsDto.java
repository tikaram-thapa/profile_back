package com.profile.profile_back.rating.dtos;

// import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class StatsDto {
    private Double averageRating;
    private Integer totalReviews;

    public StatsDto(Double averageRating, int totalReviews) {
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }
}
