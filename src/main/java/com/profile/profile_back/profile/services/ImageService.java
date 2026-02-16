package com.profile.profile_back.profile.services;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.profile.profile_back.profile.entities.Profile;
import com.profile.profile_back.profile.repositories.ProfileRepository;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
// @AllArgsConstructor
public class ImageService {
    private final S3Client s3Client;
    private final S3Presigner presigner;
    private final String bucketName;
    private final ProfileRepository profileRepository;

    public ImageService(
        S3Client s3Client,
        S3Presigner presigner,
        @Value("${aws.bucket-name}")
        String bucketName,
        ProfileRepository profileRepository
    ) {
        this.s3Client = s3Client;
        this.presigner = presigner;
        this.bucketName = bucketName;
        this.profileRepository = profileRepository;
    }

    public int uploadAvatar(Long id, MultipartFile file) throws IOException {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            System.out.println("Profile not found for ID: " + id);
            return 404;
        }
        
        try {
            String key = "users/" + profile.getUuid() + "/avatar.webp";
        
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            profile.setAvatarUrl(key);
            profileRepository.save(profile);
        } catch (IOException | AwsServiceException | SdkClientException e) {
            System.out.println("Error uploading avatar: " + e.getMessage());
            return 500;
        }
        return 200;
    }

    // Generate pre-signed URL for image access
    public String getAvatarUrl(Long id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            System.out.println("Profile not found for ID: " + id);
            return null;
        }

        if (profile.getAvatarUrl() == null) return null;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(profile.getAvatarUrl())
                .build();

        String imageObjectUrl = presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .getObjectRequest(getObjectRequest)
                        .signatureDuration(Duration.ofMinutes(10))
                        .build()
        ).url().toString();
        // System.out.println("Generated pre-signed URL: " + imageObjectUrl);

        return imageObjectUrl;
    }

}