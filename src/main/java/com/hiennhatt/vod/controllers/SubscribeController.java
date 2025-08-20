package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.CountSubscribersDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.repositories.projections.SubscribeProjection;
import com.hiennhatt.vod.services.SubscribeService;
import com.hiennhatt.vod.validations.SubscribeUserValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public void subscribeUser(@RequestBody @Valid SubscribeUserValidation validation, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        subscribeService.subscribe(validation, customUserDetails.getUser());
    }

    @DeleteMapping("")
    @PreAuthorize("isAuthenticated()")
    public void unsubscribeUser(@RequestBody @Valid SubscribeUserValidation validation, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        subscribeService.unsubscribe(validation, customUserDetails.getUser());
    }

    @GetMapping("/subscribed")
    @PreAuthorize("isAuthenticated()")
    public List<SubscribeProjection> getSubscribedByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return subscribeService.getSubscribesByUser(customUserDetails.getUser());
    }

    @GetMapping("/subscribers")
    @PreAuthorize("isAuthenticated()")
    public List<SubscribeProjection> getSubscribersOfUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return  subscribeService.getSubscribers(customUserDetails.getUser());
    }

    @GetMapping("/count/{username}")
    public CountSubscribersDTO countSubscribersOfUser(@PathVariable("username") String username) {
        return new CountSubscribersDTO(subscribeService.countSubscribersOfUser(username));
    }
}
