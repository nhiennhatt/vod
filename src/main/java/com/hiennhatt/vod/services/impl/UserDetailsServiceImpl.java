package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.repositories.UserRepository;
import com.hiennhatt.vod.repositories.projections.AuthorizationUserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthorizationUserProjection user = userRepository.findAuthorizationUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new CustomUserDetails(user);
    }
}
