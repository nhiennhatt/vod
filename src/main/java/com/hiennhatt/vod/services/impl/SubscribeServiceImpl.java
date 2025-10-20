package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Subscribe;
import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.SubscribeRepository;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import com.hiennhatt.vod.repositories.projections.IdentifiableUserProjection;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.repositories.projections.SubscriptionProjection;
import com.hiennhatt.vod.services.SubscribeService;
import com.hiennhatt.vod.utils.HTTPResponseStatusException;
import com.hiennhatt.vod.validations.SubscribeUserValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
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
        IdentifiableUserProjection destUser = userRepository.findIdentifiableUserByUsername(validation.getTargetUsername());
        if (destUser == null)
            throw new HTTPResponseStatusException("User not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        Subscribe subscribe = new Subscribe();
        subscribe.setSourceUser(sourceUser);
        subscribe.setDestUser(destUser.toUser());
        subscribe.setUid(UUID.randomUUID());
        try {
            subscribeRepository.save(subscribe);
        } catch (DuplicateKeyException e) {
            throw new HTTPResponseStatusException("Already subscribed", "ALREADY_SUBSCRIBED", HttpStatus.CONFLICT, null);
        }
    }

    @Override
    @Transactional
    public void unsubscribe(SubscribeUserValidation validation, User sourceUser) {
        IdentifiableUserProjection destUser = userRepository.findIdentifiableUserByUsername(validation.getTargetUsername());
        if (destUser == null)
            throw new HTTPResponseStatusException("User not found", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        Subscribe subscribe = subscribeRepository.findSubscribeBySourceUserAndDestUser(sourceUser, destUser.toUser());
        if (subscribe == null)
            throw new HTTPResponseStatusException("User doesn't already subscribe that user", "NOT_FOUND", HttpStatus.NOT_FOUND, null);

        subscribeRepository.delete(subscribe);
    }

    @Override
    public List<SubscribeProjection> getSubscribers(User destUser) {
        return subscribeRepository.findSubscribeProjectionsByDestUser(destUser);
    }

    @Override
    public List<SubscriptionProjection> getSubscribesByUser(User sourceUser, Pageable pageable) {
        return subscribeRepository.findSubscription(sourceUser, pageable);
    }

    @Override
    public long countSubscribersOfUser(String username) {
        IdentifiableUserProjection destUser = userRepository.findIdentifiableUserByUsername(username);
        if (destUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user");

        return subscribeRepository.countSubscribesByDestUser(destUser.toUser());
    }

    @Override
    public boolean isSubscribed(User sourceUser, String destUsername) {
        IdentifiableUserProjection destUser = userRepository.findIdentifiableUserByUsername(destUsername);
        return subscribeRepository.existsBySourceUserAndDestUser(sourceUser, destUser.toUser());
    }
}
