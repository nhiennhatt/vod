package com.hiennhatt.vod.configs;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private final UserDetailsService userDetailsService;

    public CustomJwtAuthenticationConverter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        this.jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        String username = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "N/A", authorities);
    }
}