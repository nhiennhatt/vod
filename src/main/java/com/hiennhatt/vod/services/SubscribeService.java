package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.User;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.validations.SubscribeUserValidation;

import java.util.List;

public interface SubscribeService {
    void subscribe(SubscribeUserValidation validation, User sourceUser);
    void unsubscribe(SubscribeUserValidation validation, User sourceUser);
    List<SubscribeProjection> getSubscribers(User destUser);
    List<SubscribeProjection> getSubscribesByUser(User sourceUser);
    long countSubscribersOfUser(String username);
}
