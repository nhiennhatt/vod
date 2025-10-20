package com.hiennhatt.vod.dtos;

import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import lombok.Getter;

@Getter
public class PublicUserInformDTO {
    private String username;
    private String firstName;
    private String middlename;
    private String lastName;
    private String avatar;
    private String coverImg;
    private String description;
    public PublicUserInformDTO(PublicUserInformProjection userInform, String username) {
        this.username = username;
        firstName = userInform.getFirstName();
        lastName = userInform.getLastName();
        avatar = userInform.getAvatar();
        coverImg = userInform.getCoverImg();
        description = userInform.getDescription();
        middlename = userInform.getMiddleName();
    }
}
