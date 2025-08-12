package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.CommentService;
import com.hiennhatt.vod.validations.SaveCommentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public void saveComment(@RequestBody @Validated SaveCommentValidation body, @AuthenticationPrincipal CustomUserDetails userDetails) {
        this.commentService.saveComment(body, userDetails.getUser());
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<CommentDTO> getCommentsByVideoId(@RequestParam(required = true) String videoId, @RequestParam(required = false) UUID previousComment, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return this.commentService.getComments(UUID.fromString(videoId), previousComment, userDetails.getUser());
    }

    @DeleteMapping("/{uid}")
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(@PathVariable String uid, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.deleteComment(uid, userDetails.getUser());
    }
}
