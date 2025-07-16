package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username = ?1")
    User findUsersByUsername(String username);

    User save(User user);
}
