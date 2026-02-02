package com.profile.profile_back.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.profile_back.user.entities.User;

public interface ProfileUserRepository extends JpaRepository<User, Long> {
    
}
