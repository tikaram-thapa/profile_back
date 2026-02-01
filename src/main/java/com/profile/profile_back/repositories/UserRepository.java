package com.profile.profile_back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.profile_back.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
