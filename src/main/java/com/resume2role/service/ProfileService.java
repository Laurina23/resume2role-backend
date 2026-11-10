package com.resume2role.service;

import com.resume2role.model.Profile;
import com.resume2role.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(String email) {

        return profileRepository.findByEmail(email)
                .orElse(
                        Profile.builder()
                                .email(email)
                                .fullName("")
                                .targetRole("")
                                .techStack("")
                                .phone("")
                                .city("")
                                .avatar("")
                                .build()
                );
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}