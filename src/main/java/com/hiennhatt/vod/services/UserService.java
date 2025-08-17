package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;

public interface UserService {
    void registerUser(RegisterUserValidation registerUser);
    PublicUserInformProjection getUserInformByUsername(String username);
    SelfUserInformProjection getUserInformByUser(User user);
    void updateProfile(UpdateProfileValidation newProfile, User user);
}
