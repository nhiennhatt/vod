package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.UserInform;
import com.hiennhatt.vod.repositories.UserInformRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
