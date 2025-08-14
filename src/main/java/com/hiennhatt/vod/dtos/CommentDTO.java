package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.Comment;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.UserOverview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Setter
public class CommentDTO {
    public CommentDTO (Comment comment) {
        uid = comment.getUid();
        user = comment.getUser();
        content = comment.getContent();
        createdOn = comment.getCreatedOn();
        status = comment.getStatus();
    }

    @Getter
    private UUID uid;
    private User user;
    @Getter
    private String content;
    @Getter
    private Instant createdOn;
    @Getter
    private Comment.Status status;

    public UserOverview getUser() {
        return new UserOverview(user.getUsername(), user.getUserInform().getFirstName(), user.getUserInform().getLastName(), user.getUserInform().getAvatar());
    }
}
