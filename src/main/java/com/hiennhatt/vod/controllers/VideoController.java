package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.CreationDTO;
import com.hiennhatt.vod.dtos.PreSaveFileDTO;
import com.hiennhatt.vod.dtos.VideoOverviewDTO;
import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.repositories.projections.VideoDetailProjection;
import com.hiennhatt.vod.repositories.projections.VideoOverviewProjection;
import com.hiennhatt.vod.services.VideoCategoryService;
import com.hiennhatt.vod.services.VideoService;
import com.hiennhatt.vod.validations.AddCategoriesToVideoValidation;
import com.hiennhatt.vod.validations.UpdateVideoThumbnailValidation;
import com.hiennhatt.vod.validations.UpdateVideoValidation;
import com.hiennhatt.vod.validations.UploadVideoValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoCategoryService videoCategoryService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreationDTO> uploadVideo(@ModelAttribute @Valid UploadVideoValidation body, @AuthenticationPrincipal CustomUserDetails user) {
        PreSaveFileDTO saveFile = videoService.preUploadVideo(body, body.getThumbnail(), body.getVideo(), user.getUser());
        this.videoService.uploadVideo(saveFile);
        return new ResponseEntity<>(new CreationDTO(UUID.fromString(saveFile.getUid())), null, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uid}/overview")
    @PostAuthorize("returnObject.getPrivacy() != returnObject.privacy.PRIVATE || (isAuthenticated() && principal.username == returnObject.getUser().username)")
    public VideoOverviewProjection getVideoOverview(@PathVariable String uid) {
        return this.videoService.getVideoOverview(uid);
    }

    @GetMapping("/{uid}")
    @PostAuthorize("returnObject.privacy != returnObject.privacy.PRIVATE || (isAuthenticated() && principal.username == returnObject.user.username)")
    public VideoDetailProjection getVideo(@PathVariable String uid) {
        return this.videoService.getVideo(uid);
    }

    @PutMapping("/{uid}")
    @PreAuthorize("isAuthenticated()")
    public void updateVideo(@PathVariable String uid, @RequestBody @Validated UpdateVideoValidation updateVideoValidation, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        this.videoService.updateVideo(UUID.fromString(uid), updateVideoValidation, customUserDetails.getUser());
    }

    @PutMapping("/{uid}/thumbnail")
    @PreAuthorize("isAuthenticated()")
    public void updateVideoThumbnail(@PathVariable String uid, @ModelAttribute @Validated UpdateVideoThumbnailValidation updateVideoThumbnailValidation, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        this.videoService.updateVideoThumbnail(UUID.fromString(uid), updateVideoThumbnailValidation, customUserDetails.getUser());
    }

    @DeleteMapping("/{uid}")
    @PreAuthorize("isAuthenticated()")
    public void deleteVideo(@PathVariable String uid, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        this.videoService.deleteVideo(uid, customUserDetails.getUser());
    }

    @PostMapping("/{videoUid}/categories")
    @PreAuthorize("isAuthenticated()")
    public void addCategoriesToVideo(@RequestBody @Valid AddCategoriesToVideoValidation body, @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable @Valid @org.hibernate.validator.constraints.UUID String videoUid) {
        videoCategoryService.addCategoriesToVideo(body.getCategories(), videoUid, customUserDetails.getUser());
    }

    @GetMapping("/{videoUid}/categories")
    @PreAuthorize("isAuthenticated()")
    public List<Category> getCategories(@PathVariable UUID videoUid) {
        return videoCategoryService.getVideoCategories(videoUid);
    }

    @GetMapping("")
    public List<VideoOverviewDTO> findVideos(@RequestParam String q, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 9);
        return videoService.findVideoByKeyword('%' + q + '%', pageable).stream().map(VideoOverviewDTO::new).toList();
    }

    @GetMapping("latest")
    public List<VideoOverviewDTO> lastestVideos(@RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 9, Sort.by(Sort.Direction.DESC, "createdOn"));
        return videoService.getLatestVideos(pageable).stream().map(VideoOverviewDTO::new).toList();
    }

    @GetMapping("subscribed")
    public List<VideoOverviewDTO> getVideosSubscribedByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 9);
        return videoService.getVideosSubscribedByUser(customUserDetails.getUser(), pageable).stream().map(VideoOverviewDTO::new).toList();
    }
}
