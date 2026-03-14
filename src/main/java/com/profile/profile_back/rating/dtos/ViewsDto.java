package com.profile.profile_back.rating.dtos;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ViewsDto {
    @Column(name="ip_addr", nullable=false)
    private String ipAddr;
    @Column(name="look_up_date", nullable=false)
    private String lookUpDate; // yyyy-MM-dd monthly date, unique with profile and ipAddr
    private Long lookUpCount; // monthly lookup count
    @CreationTimestamp
    @Column(updatable=false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime updatedAt;

}
