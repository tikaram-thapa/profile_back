package com.profile.profile_back.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.profile_back.profile.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
