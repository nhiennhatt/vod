package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.*;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void registerUser(RegisterUserValidation registerUser);
    PublicUserInformProjection getUserInformByUsername(String username);
    DetailUserProjection getUserInformByUser(User user);
    void updateProfile(UpdateProfileValidation newProfile, User user);
    String updateAvatar(MultipartFile avatar, User user);
    String updateCoverImage(MultipartFile cover, User user);
    BasicUserInformProjection getBasicUserInformDTO(User user);
    AuthorizationUserProjection updateUsername(String newUsername, User user);
    void updateEmail(String newEmail, User user);
}
