package com.profile.profile_back.rating.dtos;

import jakarta.persistence.Column;

public class ViewsRequest {
    @Column(name="profile_id", nullable=false)
    private Long profileId;

}
