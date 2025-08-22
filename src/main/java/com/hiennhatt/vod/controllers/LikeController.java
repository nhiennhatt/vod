package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.CountLikeVideoDTO;
import com.hiennhatt.vod.dtos.IsLikedVideoDTO;
import com.hiennhatt.vod.dtos.LikeDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.LikeService;
import com.hiennhatt.vod.validations.LikeVideoValidation;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public void likeVideo(@RequestBody @Validated LikeVideoValidation body, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        likeService.likeVideo(body.getVideoId(), customUserDetails.getUser());
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public IsLikedVideoDTO getLikes(@RequestParam @Validated @UUID String videoId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boolean result = likeService.isVideoLiked(java.util.UUID.fromString(videoId), customUserDetails.getUser());
        return new IsLikedVideoDTO(result);
    }

    @DeleteMapping("")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void unlikeVideo(@RequestBody @Validated LikeVideoValidation body, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        likeService.unlikeVideo(body.getVideoId(), customUserDetails.getUser());
    }

    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public CountLikeVideoDTO getCountLikeInVideo(@RequestParam @Validated @UUID String videoId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        long result = likeService.getLikesCount(java.util.UUID.fromString(videoId));
        return new CountLikeVideoDTO(result);
    }

    @GetMapping("/liked")
    @PreAuthorize("isAuthenticated()")
    public List<LikeDTO> getLikedVideos(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return likeService.getVideosLikedByUser(customUserDetails.getUser());
    }
}
