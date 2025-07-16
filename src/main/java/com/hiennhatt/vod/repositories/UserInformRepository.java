package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.UserInform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformRepository extends JpaRepository<UserInform, Integer> {
    UserInform save(UserInform userInform);
}
