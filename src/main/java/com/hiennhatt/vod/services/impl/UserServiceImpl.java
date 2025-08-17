package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.UserInform;
import com.hiennhatt.vod.repositories.UserInformRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInformRepository userInformRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(RegisterUserValidation registerUser) {
        User user = registerUser.toUser();
        user.setRole(User.Role.ROLE_USER);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase());
        user.setStatus(User.Status.ACTIVE);
        user.setIsVerified(false);
        Instant now = Instant.now();
        user.setCreatedOn(now);
        user.setUpdatedOn(now);
        this.userRepository.save(user);

        UserInform userInform = new UserInform();
        userInform.setUser(user);
        userInform.setCreatedOn(now);
        userInform.setUpdatedOn(now);
        this.userInformRepository.save(userInform);
    }

    @Override
    public PublicUserInformProjection getUserInformByUsername(String username) {
        PublicUserInformProjection userInform = userInformRepository.findPublicUserInformProjectionByUserUsername(username);
        if (userInform == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return userInform;
    }

    @Override
    public SelfUserInformProjection getUserInformByUser(User user) {
        SelfUserInformProjection userInform = userInformRepository.findSelfUserInformProjectionByUser(user);
        if (userInform == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return userInform;
    }

    @Override
    @Transactional
    public void updateProfile(UpdateProfileValidation newProfile, User user) {
        UserInform userInform = userInformRepository.findUserInformByUser(user);
        if (userInform == null) {
            createUserInformFromUpdateProfile(user, newProfile);
            return;
        }

        userInform.setFirstName(newProfile.getFirstName());
        userInform.setLastName(newProfile.getLastName());
        userInform.setMiddleName(newProfile.getMiddleName());
        userInform.setDescription(newProfile.getDescription());
        userInform.setDateOfBirth(newProfile.getDateOfBirth());
    }

    private void createUserInformFromUpdateProfile(User user, UpdateProfileValidation updateProfile) {
        UserInform userInform = new UserInform();
        userInform.setUser(user);
        userInform.setLastName(updateProfile.getLastName());
        userInform.setFirstName(updateProfile.getFirstName());
        userInform.setMiddleName(updateProfile.getMiddleName());
        userInform.setDescription(updateProfile.getDescription());
        userInform.setDateOfBirth(updateProfile.getDateOfBirth());
        userInformRepository.save(userInform);
    }
}
