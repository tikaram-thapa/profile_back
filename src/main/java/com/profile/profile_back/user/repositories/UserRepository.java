package com.profile.profile_back.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.profile_back.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
