package com.profile.profile_back.rating.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import com.profile.profile_back.profile.entities.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "views",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"profile_id", "ip_addr", "look_up_data"})
    }
)
@AllArgsConstructor
@NoArgsConstructor
public class ProfileView {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="look_up_date", nullable=false)
    private String lookUpDate; // yyyy-MM-dd monthly date, unique with profile and ipAddr
    private Long lookUpCount; // monthly lookup count
    @Column(name="ip_addr", nullable=false)
    private String ipAddr;
    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;

    @Autowired
    public ProfileView(Profile profile, String lookUpDate, String ipAddr) {
        this.profile = profile;
        this.lookUpDate = lookUpDate;
        this.ipAddr = ipAddr;
        // this.lookUpCount = lookUpCount;
    }

    public void setLookUpCount(Long lookUpCount) {
        this.lookUpCount = lookUpCount;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getid() {
        return id;
    }

    public String getLookUpDate() {
        return lookUpDate;
    }

    public Long getLookUpCount() {
        return lookUpCount;
    }

}
