package com.hiennhatt.vod.repositories.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserOverview {
    private String username;
    private String firstName;
    private String lastName;
    private String avatar;
}
