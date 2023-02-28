package com.coursenet.order.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.accessValid}")
    private int accessTokenValid;

    @Value("${jwt.token.refreshValid}")
    private int refreshTokenValid;

    @Autowired
    private Algorithm algorithm;

    public DecodedJWT verifyJWTToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token.replace("Bearer ", ""));
    }

    public String getCurrentToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DecodedJWT decodedJWT = principal instanceof DecodedJWT ? (DecodedJWT) principal :null;
        return decodedJWT.getToken();
    }
}
