package com.hiennhatt.vod.utils;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JWTUtils {
    private final String privateKey;
    private final String publicKey;
    
    public JWTUtils(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public JwtDecoder jwtDecoder() {
        try {
            NimbusJwtDecoder delegate = NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
            
            return new JwtDecoder() {
                @Override
                public Jwt decode(String token) throws JwtException {
                    try {
                        return delegate.decode(token);
                    } catch (JwtValidationException e) {
                        boolean expired = e.getErrors().stream()
                            .anyMatch(err -> {
                                String desc = err.getDescription();
                                return desc != null && desc.toLowerCase().contains("expired");
                            });
                        
                        if (expired) {
                            throw new HTTPResponseStatusException("JWT token has expired", "EXPIRED_TOKEN", HttpStatus.UNAUTHORIZED, null);
                        }
                        
                        String errorMessage = e.getErrors().stream()
                            .map(err -> err.getDescription() != null ? err.getDescription() : err.getErrorCode())
                            .findFirst()
                            .orElse("JWT validation failed");
                        
                        throw new JwtException(errorMessage);
                    } catch (JwtException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new HTTPResponseStatusException("JWT token has expired", "EXPIRED_TOKEN", HttpStatus.UNAUTHORIZED, null);
                    }
                }
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JwtEncoder jwtEncoder() {
        try {
            RSAKey rsaKey = (new RSAKey.Builder(getPublicKey())).privateKey(getPrivateKey()).build();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
