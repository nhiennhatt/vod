package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import com.hiennhatt.vod.repositories.projections.DetailUserProjection;
import com.hiennhatt.vod.repositories.projections.IdentifiableUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    IdentifiableUserProjection findIdentifiableUserByUsername(String username);

    AuthorizationUserProjection findAuthorizationUserByUsername(String username);

    DetailUserProjection findDetailUserProjectionByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.username = ?1 WHERE u.id = ?2")
    void updateUserUsername(String username, Integer id);

    @Modifying
    @Query("UPDATE User u SET u.email = ?1 WHERE u.id = ?2")
    void updateUserEmail(String email, Integer id);
}
