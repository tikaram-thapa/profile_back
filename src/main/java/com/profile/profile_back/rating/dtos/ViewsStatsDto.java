package com.profile.profile_back.rating.dtos;

// import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class ViewsStatsDto {
    private Double totalViews;
    private Double thanLastMonth;

    public ViewsStatsDto(Double totalViews, Double thanLastMonth) {
        this.totalViews = totalViews;
        this.thanLastMonth = thanLastMonth;
    }
}
