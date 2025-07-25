package com.hiennhatt.vod.configs;

import com.hiennhatt.vod.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootConfiguration
@EnableMethodSecurity()
public class SecurityConfig {
    @Autowired
    private Environment env;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean(name = "jwtAccessTokenUtil")
    public JWTUtils jwtAccessTokenUtil() {
        return new JWTUtils(env.getProperty("jwt.keypair.access.privateKey"), env.getProperty("jwt.keypair.access.publicKey"));
    }

    @Bean(name = "jwtRefreshTokenUtil")
    public JWTUtils jwtRefreshTokenUtil() {
        return new JWTUtils(env.getProperty("jwt.keypair.refresh.privateKey"), env.getProperty("jwt.keypair.refresh.publicKey"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter(userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(customizer -> customizer
                .jwt(jwt -> jwt
                    .decoder(jwtAccessTokenUtil().jwtDecoder())
                    .jwtAuthenticationConverter(customJwtAuthenticationConverter())
                )
            )
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
