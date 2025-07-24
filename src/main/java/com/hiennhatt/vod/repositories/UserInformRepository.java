package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.UserInform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformRepository extends JpaRepository<UserInform, Integer> {
    UserInform findUserInformById(Integer id);
}
