package com.hiennhatt.vod.services;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.validations.SaveCommentValidation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    UUID saveComment(SaveCommentValidation saveComment, User user);

    List<CommentDTO> getComments(UUID videoId, Pageable pageable, User user);

    void deleteComment(String uid, User user);
    long countComments(UUID videoId, User user);
}
