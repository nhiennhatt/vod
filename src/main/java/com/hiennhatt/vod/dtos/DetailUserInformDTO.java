package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.repositories.projections.DetailUserProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DetailUserInformDTO {
    private String username;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String description;
    private String avatar;
    private String coverImg;

    public DetailUserInformDTO(DetailUserProjection user) {
        username = user.getUsername();
        email = user.getEmail();
        SelfUserInformProjection userInform = user.getUserInform();
        if (userInform != null) {
            this.firstName = userInform.getFirstName();
            this.middleName = userInform.getMiddleName();
            this.lastName = userInform.getLastName();
            this.dateOfBirth = userInform.getDateOfBirth();
            this.description = userInform.getDescription();
            this.avatar = userInform.getAvatar();
            this.coverImg = userInform.getCoverImg();
        }
    }
}
