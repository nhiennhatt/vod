package com.hiennhatt.vod.utils;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

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
            return NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
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
