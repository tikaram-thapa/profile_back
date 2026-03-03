package com.profile.profile_back.rating.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.profile.profile_back.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.profile.id = :profileId")
    Double findAverageRatingByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT COUNT(*) FROM Rating r WHERE r.profile.id = :profileId")
    Integer countByProfileId(@Param("profileId") Long profileId);

}
