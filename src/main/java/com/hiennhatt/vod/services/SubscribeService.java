package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.repositories.projections.SubscriptionProjection;
import com.hiennhatt.vod.validations.SubscribeUserValidation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscribeService {
    void subscribe(SubscribeUserValidation validation, User sourceUser);
    void unsubscribe(SubscribeUserValidation validation, User sourceUser);
    List<SubscribeProjection> getSubscribers(User destUser);
    List<SubscriptionProjection> getSubscribesByUser(User sourceUser, Pageable pageable);
    long countSubscribersOfUser(String username);
    boolean isSubscribed(User sourceUser, String destUsername);
}
