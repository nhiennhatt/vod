package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.UploadVideoValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PreAuthorize("isAuthenticated() and @activeUserAccessManager.isActiveUser(authentication)")
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    public void uploadVideo(@ModelAttribute @Valid UploadVideoValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        this.videoService.uploadVideo(body, user.getUser());
    }
}
