package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.BasicUserInformDTO;
import com.hiennhatt.vod.dtos.VideoOverviewDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import com.hiennhatt.vod.validations.UpdateAvatarValidation;
import com.hiennhatt.vod.validations.UpdateProfileValidation;
import com.hiennhatt.vod.validations.UploadCoverValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterUserValidation body) {
        this.userService.registerUser(body);
    }

    @GetMapping("/{username}")
    public PublicUserInformProjection getUserInform(@PathVariable String username) {
        return userService.getUserInformByUsername(username);
    }

    @GetMapping("/{username}/videos")
    public List<VideoOverviewDTO> getVideosByUsername(@PathVariable String username, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);
        return videoService.getVideoOverviewProjectionsByUser(username, pageable).stream().map(VideoOverviewDTO::new).toList();
    }
}
