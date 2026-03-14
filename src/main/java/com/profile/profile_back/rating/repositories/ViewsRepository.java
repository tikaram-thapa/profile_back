package com.profile.profile_back.rating.repositories;

import com.profile.profile_back.rating.entities.ProfileView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewsRepository extends JpaRepository<ProfileView, Long> {
    @Query("SELECT COALESCE(SUM(v.lookUpCount),0) FROM ProfileView v WHERE v.profile.id = :profileId AND v.lookUpDate = :lookUpDate")
    Long sumLookUps(@Param("profileId") Long profileId, @Param("lookUpDate") String lookUpDate);

    @Query("SELECT v FROM ProfileView v WHERE v.profile.id = :profileId AND v.lookUpDate = :lookUpDate")
    ProfileView findByProfileIdAndDate(@Param("profileId") Long profileId, @Param("lookUpDate") String lookUpDate);

    @Query("SELECT v FROM ProfileView v WHERE v.profile.id = :profileId AND v.lookUpDate = :lookUpDate AND v.ipAddr = :ipAddr")
    ProfileView findByProfileIdAndDateAndIp(@Param("profileId") Long profileId, @Param("lookUpDate") String lookUpDate, @Param("ipAddr") String ipAddr);
}
