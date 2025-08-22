package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.models.UserInform;
import com.hiennhatt.vod.repositories.projections.BasicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.PublicUserInformProjection;
import com.hiennhatt.vod.repositories.projections.SelfUserInformProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformRepository extends JpaRepository<UserInform, Integer> {
    UserInform findUserInformById(Integer id);
    UserInform findUserInformByUser(User user);
    PublicUserInformProjection findPublicUserInformProjectionByUserUsername(String username);
    SelfUserInformProjection findSelfUserInformProjectionByUser(User user);
    BasicUserInformProjection findUserInformProjectionByUser(User user);
}
