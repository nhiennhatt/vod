package com.hiennhatt.vod.repositories.impl;

import com.hiennhatt.vod.models.UserInform;
import com.hiennhatt.vod.repositories.UserInformRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserInformRepositoryImpl implements UserInformRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public UserInform save(UserInform userInform) {
        entityManager.persist(userInform);
        return userInform;
    }
}
