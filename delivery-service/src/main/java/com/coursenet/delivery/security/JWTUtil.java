package com.coursenet.delivery.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public DecodedJWT verifyJWTToken(String token){
        JWTVerifier verifier =JWT.require(algorithm).build();
        return verifier.verify(token.replace("Bearer ", ""));
    }

    public String generateJWTToken(String userName, String type){
        LocalDateTime issuedLocalTime = LocalDateTime.now();
        int valid = type.equals(TokenConstants.TOKEN_ACCESS) ? accessTokenValid : refreshTokenValid;

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userName)
                .withIssuedAt(Date.from(issuedLocalTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(issuedLocalTime.plusSeconds(valid).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("type", type)
                .sign(algorithm);
    }
}
