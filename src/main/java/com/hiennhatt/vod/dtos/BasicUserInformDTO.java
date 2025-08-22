package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.BasicUserInformProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicUserInformDTO {
    private String username;
    private User.Status status;
    private User.Role role;

    private String firstName;
    private String lastName;
    private String avatar;

    public BasicUserInformDTO(User user, BasicUserInformProjection userInform) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.status = user.getStatus();
        if (userInform != null) {
            this.firstName = userInform.getFirstName();
            this.lastName = userInform.getLastName();
            this.avatar = userInform.getAvatar();
        }
    }
}
