package com.profile.profile_back.rating.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.profile.profile_back.rating.dtos.ViewsResponseDto;
import com.profile.profile_back.rating.dtos.ViewsStatsDto;
import com.profile.profile_back.rating.entities.ProfileView;
import com.profile.profile_back.rating.services.ViewsService;
import com.profile.profile_back.profile.entities.Profile;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("views")
public class ProfileViewCotroller {
    public ViewsService viewsService;

    public ProfileViewCotroller(ViewsService viewsService) {
        this.viewsService = viewsService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ViewsResponseDto> create(@PathVariable Long id, HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null) ip = request.getRemoteAddr();
            System.out.println("IP1: " + ip);
            if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
                ip = "127.0.0.1";
            }
            // String xfHeader = request.getHeader("X-Forwarded-For");
            // if (xfHeader == null) {
            //     ip = xfHeader.split(",")[0];
            // }
            System.out.println("IP2: " + ip);
            // check profile with id
            Profile profile = viewsService.checkProfile(id);
            if (profile == null) {
                return ResponseEntity.ok(new ViewsResponseDto(404, "Profile not found.", null));
            }
            String today = viewsService.dateInString(LocalDateTime.now());
            // check existance of views with profileId, date and ip
            ProfileView views = viewsService.findByProfileIdAndDateAndIp(profile.getId(), today, ip);
            if (views == null) {
                viewsService.createNewProfileViews(profile, today, ip);
                // return ResponseEntity.ok(new ViewsResponseDto(201, "Views created successfully", null));
            }
            // one ip has only one count and every ip has one per day
            // count total count of profile view for today
            // Long totalLookUps = viewsService.sumLookUps(profile.getId(), today);
            // ProfileView toUpdate = viewsService.toUpdateViews(views, totalLookUps);
            // viewsService.saveViews(toUpdate);
            return ResponseEntity.ok(new ViewsResponseDto(200, "Views created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ViewsResponseDto(500, "Error on create: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewsResponseDto> get(@PathVariable Long id) {
        try {
            Profile profile = viewsService.checkProfile(id);
            if (profile == null) {
                return ResponseEntity.ok(new ViewsResponseDto(404, "Profile not found.", null));
            }
            String today = viewsService.dateInString(LocalDateTime.now());
            Long totalLookUps = viewsService.sumLookUps(profile.getId(), today);
            Double thanLastMonth = 0.1;
            ViewsStatsDto statsDto = new ViewsStatsDto(Double.parseDouble(totalLookUps.toString()), thanLastMonth);
            return ResponseEntity.ok(new ViewsResponseDto(200, "Views created successfully", null, statsDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ViewsResponseDto(500, "Error on get: " + e.getMessage(), null));
        }
    }
    
    
}