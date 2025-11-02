package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.dtos.CommentDTO;
import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.Video;
import com.hiennhatt.vod.repositories.projections.CommentProjection;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findCommentByUid(@NotNull UUID uid);

    @Query("SELECT c FROM Comment c WHERE c.video = :video and (c.status = 'ACTIVE' or c.user = :user)")
    List<CommentProjection> findCommentsInPublic(@NotNull Video video, @NotNull User user, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.video = :video and (c.status = 'ACTIVE' or c.user = :user) AND c.uid > :latestCommentId ORDER BY c.uid")
    List<CommentProjection> findNextCommentsInPublic(@NotNull Video video, @NotNull User user, @NotNull UUID latestCommentId);

    long countCommentByVideoAndStatus(@NotNull Video video, @NotNull Comment.Status status);
}
