package com.profile.profile_back.rating.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
// import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class ViewsResponseDto {
    private int statusCode;
    private String message;
    private ViewsDto viewsDto;
    private ViewsStatsDto viewsStatsDto;

    @Autowired
    public ViewsResponseDto(int statusCode, String message, ViewsDto viewsDto, ViewsStatsDto viewsStatsDto) {
        this.statusCode = statusCode;
        this.message = message;
        this.viewsDto = viewsDto;
        this.viewsStatsDto = viewsStatsDto;
    }

    @Autowired
    public ViewsResponseDto(int statusCode, String message, ViewsDto viewsDto) {
        this.statusCode = statusCode;
        this.message = message;
        this.viewsDto = viewsDto;
    }
}
