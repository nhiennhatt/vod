package com.hiennhatt.vod.repositories.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserOverview {
    private String username;
    private String firstName;
    private String lastName;
    private String avatar;
}
