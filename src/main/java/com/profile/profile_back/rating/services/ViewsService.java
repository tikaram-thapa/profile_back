package com.profile.profile_back.rating.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

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

    public String firstDayOfMonth(LocalDateTime date) {
        LocalDateTime firstDayOfMonth = date.withDayOfMonth(1) // sets the day to the 1st of the month
        .with(LocalTime.MIN); // sets time to 00:00:00
        return dateInString(firstDayOfMonth);
    }

    public String lastDayOfMonth(LocalDateTime date) {
        LocalDateTime lastDayOfMonth = date.withDayOfMonth( // moves the date to the last day
            date.toLocalDate().lengthOfMonth()) // gets the total days in the current month
        .with(LocalTime.MAX); // sets time to 23:59:59.99
        return dateInString(lastDayOfMonth);
    }

    public String firstDayOfLastMonth(LocalDateTime date) {
        LocalDateTime firstDayOfLastMonth = date.minusMonths(1) // gets first dat of last month of current date
        .withDayOfMonth(1) // sets the day to the 1st of the month
        .with(LocalTime.MIN);
        return dateInString(firstDayOfLastMonth);
    }

    public String lastDayOfLastMonth(LocalDateTime date) {
        LocalDateTime lastDayOfLastMonth = LocalDateTime.now()
        .minusMonths(1)
        .with(TemporalAdjusters.lastDayOfMonth())
        .with(LocalTime.MAX);
        return dateInString(lastDayOfLastMonth);
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

    public Long sumLookUps(Long profileId, LocalDateTime date) {
        return viewsRepository.sumLookUps(profileId, firstDayOfMonth(date), lastDayOfMonth(date));
    }

    public Long lastMonthSumLookUps(Long profileId, LocalDateTime date) {
        return viewsRepository.sumLookUps(profileId, firstDayOfLastMonth(date), lastDayOfLastMonth(date));
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