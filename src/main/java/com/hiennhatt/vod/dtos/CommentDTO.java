package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.CommentProjection;
import com.hiennhatt.vod.repositories.projections.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Setter
public class CommentDTO {
    public CommentDTO (CommentProjection comment) {
        uid = comment.getUid();
        user = new UserOverviewDTO(comment.getUser());
        content = comment.getContent();
        createdOn = comment.getCreatedOn();
        status = comment.getStatus();
    }

    @Getter
    private UUID uid;
    @Getter
    private UserOverviewDTO user;
    @Getter
    private String content;
    @Getter
    private Instant createdOn;
    @Getter
    private Comment.Status status;
}
