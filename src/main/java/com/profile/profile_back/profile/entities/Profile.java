package com.profile.profile_back.profile.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import com.profile.profile_back.user.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile { // One Profile belongs to One User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key, auto-increment
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID(); // Unique identifier for external use
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

    // @PrePersist
    // void onCreate() {
    //     if (uuid == null) {
    //         this.uuid = UUID.randomUUID();
    //     }
    // }

    @Autowired
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

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getUuid() {
        return uuid;
    }
}
