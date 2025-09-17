package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.*;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.TokenService;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/me")
@RestController
public class MeController {
    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("videos")
    @PreAuthorize("isAuthenticated()")
    public List<VideoOverviewDTO> getOwnVideos(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false) Integer page) {
        if (page == null) page = 0;
        return videoService.getOwnVideos(customUserDetails.getUser(), PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdOn"))).stream().map(VideoOverviewDTO::new).toList();
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public BasicUserInformDTO getBasicUserInform(@AuthenticationPrincipal CustomUserDetails user) {
        return new BasicUserInformDTO(user.getUser(), userService.getBasicUserInformDTO(user.getUser()));
    }

    @GetMapping("/detail")
    @PreAuthorize("isAuthenticated()")
    public DetailUserInformDTO getDetailUserInform(@AuthenticationPrincipal CustomUserDetails user) {
        return new DetailUserInformDTO(userService.getUserInformByUser(user.getUser()));
    }

    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public void updateProfile(@RequestBody @Valid UpdateProfileValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateProfile(body, user.getUser());
    }

    @PostMapping("/avatar")
    @PreAuthorize("isAuthenticated()")
    public UploadedDTO updateAvatar(@ModelAttribute @Valid UpdateAvatarValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        return new UploadedDTO(userService.updateAvatar(body.getAvatar(), user.getUser()));
    }

    @PostMapping("/cover")
    @PreAuthorize("isAuthenticated()")
    public UploadedDTO uploadCover(@ModelAttribute @Valid UploadCoverValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        return new UploadedDTO(userService.updateCoverImage(body.getFile(), user.getUser()));
    }

    @PutMapping("/username")
    @PreAuthorize("isAuthenticated()")
    public GainTokenDTO updateUsername(@RequestBody @Valid UpdateUsernameValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        return tokenService.generateToken(userService.updateUsername(body.getUsername(), user.getUser()));
    }

    @PutMapping("/email")
    @PreAuthorize("isAuthenticated()")
    public void updateEmail(@RequestBody @Valid UpdateEmailValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateEmail(body.getEmail(), user.getUser());
    }
}
