package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateAvatarValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterUserValidation body) {
        this.userService.registerUser(body);
    }

    @GetMapping("/{username}")
    public PublicUserInformProjection getUserInform(@PathVariable String username) {
        return userService.getUserInformByUsername(username);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public SelfUserInformProjection getCurrentUserInform(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserInformByUser(user.getUser());
    }

    @PutMapping("/")
    @PreAuthorize("isAuthenticated()")
    public void updateProfile(@RequestBody @Valid UpdateProfileValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateProfile(body, user.getUser());
    }

    @PostMapping("/avatar")
    @PreAuthorize("isAuthenticated()")
    public void updateAvatar(@ModelAttribute @Valid UpdateAvatarValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateAvatar(body.getAvatar(), user.getUser());
    }
}
