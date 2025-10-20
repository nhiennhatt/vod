package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import com.hiennhatt.vod.repositories.projections.DetailUserProjection;
import com.hiennhatt.vod.repositories.projections.IdentifiableUserProjection;
import com.hiennhatt.vod.repositories.projections.UserOverviewProjection;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    IdentifiableUserProjection findIdentifiableUserByUsername(String username);

    AuthorizationUserProjection findAuthorizationUserByUsername(String username);

    DetailUserProjection findDetailUserProjectionByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    @Query("SELECT u FROM User u  WHERE (u.username like :keyword or u.userInform.firstName like :keyword or u.userInform.middleName like :keyword or u.userInform.lastName like :keyword or CONCAT(u.userInform.firstName, ' ', u.userInform.middleName, ' ', u.userInform.lastName) like :keyword) and u.status = com.hiennhatt.vod.models.User.Status.ACTIVE")
    List<UserOverviewProjection> searchUsers (@Param("username") String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE User u SET u.username = ?1 WHERE u.id = ?2")
    void updateUserUsername(String username, Integer id);

    @Modifying
    @Query("UPDATE User u SET u.email = ?1 WHERE u.id = ?2")
    void updateUserEmail(String email, Integer id);
}
