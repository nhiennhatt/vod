package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    AuthorizationUserProjection findAuthorizationUserByUsername(String username);
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);
}
