package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findCommentByUid(@NotNull UUID uid);

    @Query("SELECT new com.hiennhatt.vod.dtos.CommentDTO(c) FROM Comment c WHERE c.video = :video and (c.status = 'ACTIVE' or c.user = :user) ORDER BY c.uid")
    List<CommentDTO> findCommentsInPublic(@NotNull Video video, @NotNull User user);

    @Query("SELECT c FROM Comment c WHERE c.video = :video and (c.status = 'ACTIVE' or c.user = :user) AND c.uid > :latestCommentId ORDER BY c.uid")
    List<CommentDTO> findNextCommentsInPublic(@NotNull Video video, @NotNull User user, @NotNull UUID latestCommentId);

    long countCommentByVideoAndStatus(@NotNull Video video, @NotNull Comment.Status status);
}
