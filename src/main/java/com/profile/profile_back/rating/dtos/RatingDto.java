package com.profile.profile_back.rating.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingDto {
    private double ratingValue; // e.g., 1 to 5
    private String comment; // optional comment about the rating
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Autowired
    public RatingDto(double ratingValue, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
