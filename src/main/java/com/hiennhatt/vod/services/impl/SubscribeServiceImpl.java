package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Subscribe;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.SubscribeRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.services.SubscribeService;
import com.hiennhatt.vod.validations.SubscribeUserValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    private SubscribeRepository subscribeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void subscribe(SubscribeUserValidation validation, User sourceUser) {
        User destUser = userRepository.findByUsername(validation.getTargetUsername());
        if (destUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user");

        Subscribe subscribe = new Subscribe();
        subscribe.setSourceUser(sourceUser);
        subscribe.setDestUser(destUser);
        subscribe.setUid(UUID.randomUUID());
        subscribeRepository.save(subscribe);
    }

    @Override
    @Transactional
    public void unsubscribe(SubscribeUserValidation validation, User sourceUser) {
        User destUser = userRepository.findByUsername(validation.getTargetUsername());
        if (destUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user");

        Subscribe subscribe = subscribeRepository.findSubscribeBySourceUserAndDestUser(sourceUser, destUser);
        if (subscribe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not already subscribed");

        subscribeRepository.delete(subscribe);
    }

    @Override
    public List<SubscribeProjection> getSubscribers(User destUser) {
        return subscribeRepository.findSubscribeProjectionsByDestUser(destUser);
    }

    @Override
    public List<SubscribeProjection> getSubscribesByUser(User sourceUser) {
        return subscribeRepository.findSubscribeProjectionsBySourceUser(sourceUser);
    }

    @Override
    public long countSubscribersOfUser(String username) {
        User destUser = userRepository.findByUsername(username);
        if (destUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user");

        return subscribeRepository.countSubscribesByDestUser(destUser);
    }
}
