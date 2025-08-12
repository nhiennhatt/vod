package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.CommentRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.CommentService;
import com.hiennhatt.vod.validations.SaveCommentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void saveComment(SaveCommentValidation saveComment, User user) {
        Video video = videoRepository.findVideoByUid(UUID.fromString(saveComment.getVideoId()));
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

        if (video.getPrivacy() == Video.Privacy.PRIVATE && !user.getId().equals(video.getUser().getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to comment on private video");

        Comment comment = new Comment();
        comment.setUid(UUID.randomUUID());
        comment.setContent(saveComment.getComment());
        comment.setVideo(video);
        comment.setStatus(Comment.Status.ACTIVE);
        comment.setUser(user);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> getComments(UUID videoId, UUID previousComment, User user) {
        Video video = videoRepository.findVideoByUid(videoId);

        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");

        if (video.getPrivacy() == Video.Privacy.PRIVATE && !user.getId().equals(video.getUser().getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to view comments on private video");

        if (previousComment == null)
            return commentRepository.findCommentsInPublic(video, user);

        return commentRepository.findNextCommentsInPublic(video, user, previousComment);
    }

    @Override
    public void deleteComment(String uid, User user) {
        Comment comment = commentRepository.findCommentByUid(UUID.fromString(uid));
        if (comment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");

        if (!comment.getUser().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to delete comment");

        commentRepository.delete(comment);
    }
}
