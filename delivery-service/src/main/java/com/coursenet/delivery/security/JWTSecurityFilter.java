package com.coursenet.delivery.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.coursenet.delivery.security.TokenConstants.TOKEN_ACCESS;

@Slf4j
public class JWTSecurityFilter extends BasicAuthenticationFilter {
    private JWTUtil jwtUtil;

    public JWTSecurityFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //Harus baca Request Header: Authorization
        String token = request.getHeader("Authorization");
        if (token ==null || !token.startsWith("Bearer")){
            log.error("Token cant be null");
            return null;
        }

        //Verify JWTToken using JWTUtil
        DecodedJWT decodedJWT = jwtUtil.verifyJWTToken(token);
        String userName = decodedJWT.getSubject();
        String tokenType = decodedJWT.getClaim("type").asString();
        if (!tokenType.equalsIgnoreCase(TOKEN_ACCESS)){
            log.error("Token is not Access Token");
            return null;
        }

        //Set Security Context
        return new UsernamePasswordAuthenticationToken(userName,null,null);
    }

}
