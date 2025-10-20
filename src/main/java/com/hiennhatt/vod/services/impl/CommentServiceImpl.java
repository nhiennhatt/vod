package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.CommentRepository;
import com.hiennhatt.vod.repositories.VideoRepository;
import com.hiennhatt.vod.services.CommentService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import com.hiennhatt.vod.validations.SaveCommentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public UUID saveComment(SaveCommentValidation saveComment, User user) {
        Video video = videoRepository.findVideoByUid(UUID.fromString(saveComment.getVideoId()));
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        if (video.getPrivacy() == Video.Privacy.PRIVATE && !user.getId().equals(video.getUser().getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        Comment comment = new Comment();
        comment.setUid(UUID.randomUUID());
        comment.setContent(saveComment.getComment());
        comment.setVideo(video);
        comment.setStatus(Comment.Status.ACTIVE);
        comment.setUser(user);

        commentRepository.save(comment);
        return comment.getUid();
    }

    @Override
    public List<CommentDTO> getComments(UUID videoId, UUID previousComment, User user) {
        Video video = videoRepository.findVideoByUid(videoId);

        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        if (video.getPrivacy() == Video.Privacy.PRIVATE && !user.getId().equals(video.getUser().getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        if (previousComment == null)
            return commentRepository.findCommentsInPublic(video, user).stream().map(CommentDTO::new).toList();

        return commentRepository.findNextCommentsInPublic(video, user, previousComment).stream().map(CommentDTO::new).toList();
    }

    @Override
    public void deleteComment(String uid, User user) {
        Comment comment = commentRepository.findCommentByUid(UUID.fromString(uid));
        if (comment == null)
            throw new HTTPResponseStatusException("Comment not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        if (!comment.getUser().getId().equals(user.getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);

        commentRepository.delete(comment);
    }

    @Override
    public long countComments(UUID videoId, User user) {
        Video video = videoRepository.findVideoByUid(videoId);
        if (video == null || video.getStatus() == Video.Status.INACTIVE)
            throw new HTTPResponseStatusException("Video not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);
        if (video.getPrivacy() == Video.Privacy.PRIVATE  && !user.getId().equals(video.getUser().getId()))
            throw new HTTPResponseStatusException("You don't have permission to access resource", "NOT_PERMITTED", HttpStatus.FORBIDDEN, null);
        return commentRepository.countCommentByVideoAndStatus(video, Comment.Status.ACTIVE);
    }
}
