package com.profile.profile_back.rating.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;
import com.profile.profile_back.rating.entities.ProfileView;
import com.profile.profile_back.rating.repositories.ViewsRepository;

@Service
public class ViewsService {
    public ViewsRepository viewsRepository;
    public ProfileRepository profileRepository;

    public ViewsService(ViewsRepository viewsRepository, ProfileRepository profileRepository) {
        this.viewsRepository = viewsRepository;
        this.profileRepository = profileRepository;
    }

    public Profile checkProfile(Long profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }

    public String dateInString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public ProfileView findByProfileIdAndDate(Long profileId, String date) {
        return viewsRepository.findByProfileIdAndDate(profileId, date);
    }

    public ProfileView findByProfileIdAndDateAndIp(Long profileId, String date, String ip) {
        return viewsRepository.findByProfileIdAndDateAndIp(profileId, date, ip);
    }

    public void createNewProfileViews(Profile profile, String date, String ip) {
        ProfileView profileView = new ProfileView(profile, date, ip);
        profileView.setLookUpCount(Long.parseLong("1"));
        viewsRepository.save(profileView);
    }

    public Long sumLookUps(Long profileId, String date) {
        return viewsRepository.sumLookUps(profileId, date);
    }

    public ProfileView toUpdateViews(ProfileView views, Long totalLookUps) {
        views.setLookUpCount(totalLookUps + 1);
        views.setUpdatedAt(LocalDateTime.now());
        return views;
    }

    public void saveViews(ProfileView views) {
        viewsRepository.save(views);
    }
}