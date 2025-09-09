package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.BasicUserInformDTO;
import com.hiennhatt.vod.dtos.VideoOverviewDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.UpdateAvatarValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import com.hiennhatt.vod.validations.UploadCoverValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("videos")
    @PreAuthorize("isAuthenticated()")
    public List<VideoOverviewDTO> getOwnVideos(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false) Integer page) {
        if (page == null) page = 0;
        return videoService.getOwnVideos(customUserDetails.getUser(), PageRequest.of(page, 10)).stream().map(VideoOverviewDTO::new).toList();
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public BasicUserInformDTO getBasicUserInform(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getBasicUserInformDTO(user.getUser());
    }

    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public void updateProfile(@RequestBody @Valid UpdateProfileValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateProfile(body, user.getUser());
    }

    @PostMapping("/avatar")
    @PreAuthorize("isAuthenticated()")
    public void updateAvatar(@ModelAttribute @Valid UpdateAvatarValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateAvatar(body.getAvatar(), user.getUser());
    }

    @PostMapping("/cover")
    @PreAuthorize("isAuthenticated()")
    public void uploadCover(@ModelAttribute @Valid UploadCoverValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateCoverImage(body.getFile(), user.getUser());
    }
}
