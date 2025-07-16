package com.hiennhatt.vod.services;

import com.hiennhatt.vod.validations.RegisterUserValidation;

public interface UserService {
    void registerUser(RegisterUserValidation registerUser);
}
