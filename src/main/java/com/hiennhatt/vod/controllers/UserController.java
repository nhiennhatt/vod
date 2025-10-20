package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.CountVideoDTO;
import com.hiennhatt.vod.dtos.PublicUserInformDTO;
import com.hiennhatt.vod.dtos.UserOverviewDTO;
import com.hiennhatt.vod.dtos.VideoOverviewDTO;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PublicUserInformDTO getUserInform(@PathVariable String username) {
        return new PublicUserInformDTO(userService.getUserInformByUsername(username), username);
    }

    @GetMapping("/{username}/videos")
    public List<VideoOverviewDTO> getVideosByUsername(@PathVariable String username, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 12, Sort.by(Sort.Direction.DESC, "createdOn"));
        return videoService.getVideoOverviewProjectionsByUser(username, pageable).stream().map(VideoOverviewDTO::new).toList();
    }

    @GetMapping("/{username}/videos/count")
    public CountVideoDTO countVideosByUsername(@PathVariable String username) {
        return new CountVideoDTO(videoService.countUserPublicVideos(username));
    }

    @GetMapping("")
    public List<UserOverviewDTO> searchUsers(@RequestParam String query, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10, Sort.by(Sort.Direction.ASC, "username"));
        return userService.searchUser(query, pageable).stream().map(UserOverviewDTO::new).toList();
    }
}
