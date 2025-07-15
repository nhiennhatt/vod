package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUsersByUsername(String username);
}
