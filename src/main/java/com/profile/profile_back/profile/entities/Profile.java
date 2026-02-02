package com.profile.profile_back.profile.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.profile.profile_back.user.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Profile { // One Profile belongs to One User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key, auto-increment
    private Long id;
    private String fname;
    private String lname;
    private String fullName;
    private String phone;
    private String email;
    private String address; // e.g., "123 Main St, City, Country"
    private String jobTitle; // e.g., Software Engineer
    // @Column(columnDefinition="TEXT")
    private String bio; // Short biography
    private String avatarUrl; // URL to profile picture
    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique=true, nullable=false)
    private User user; // Association to User entity

    public Profile(User user, String fname, String lname, String fullName, String phone, String email, String address, String jobTitle, String bio, String avatarUrl) {
        this.user = user;
        this.fname = fname;
        this.lname = lname;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }
}
