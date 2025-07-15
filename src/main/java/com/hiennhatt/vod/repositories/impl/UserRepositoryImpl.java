package com.hiennhatt.vod.repositories.impl;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Query("select u from User u where u.username = ?1")
    abstract public User findUsersByUsername(String username);
}
