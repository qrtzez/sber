package com.sber.sber.security;

import com.sber.sber.entity.Human;
import com.sber.sber.exception.ErrorMessages;
import com.sber.sber.exception.JwtAuthenticationException;
import com.sber.sber.service.security.UserService;
import io.jsonwebtoken.*;

import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

//@Component
public class JwtTokenProvider {

    @Autowired
    private UserService userService;


    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;
    @Value("${jwt.expiration}")
    private long expirationDate;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validate = new Date(now.getTime() + expirationDate * 60);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {

            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !jwsClaims.getBody().getExpiration().before(new Date());
        }
        catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(ErrorMessages.JWT_EXPIRED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getHumanEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getHumanEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
