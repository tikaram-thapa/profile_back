package com.profile.profile_back.rating.dtos;

import org.springframework.beans.factory.annotation.Autowired;

// import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class RatingDtoResponse {
    private int statusCode;
    private String message;
    private RatingDto rating;
    private StatsDto statsDto;

    @Autowired
    public RatingDtoResponse(int statusCode, String message, RatingDto ratingDto) {
        this.statusCode = statusCode;
        this.message = message;
        this.rating = ratingDto;
    }

    @Autowired
    public RatingDtoResponse(int statusCode, String message, RatingDto ratingDto, StatsDto statsDto) {
        this.statusCode = statusCode;
        this.message = message;
        this.rating = ratingDto;
        this.statsDto = statsDto;
    }
}
