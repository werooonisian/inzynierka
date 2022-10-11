package com.example.inzynierka.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.validity}")
    private long validity;

    public String generateToken(Map<String, Object> claims, String subject){ //subject to login
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+validity))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public Map<String, Object> buildClaims(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("ROLE", userDetails.getAuthorities().stream()
                .map(i -> i.getAuthority()).collect(Collectors.toList())); //ewentualnie do poprawienia lambda
        return claims;
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    public String getLoginFromToken(String token){
        return getSubjectFromToken(token, Claims::getSubject);
    }

    public <T> T getSubjectFromToken(String token, Function<Claims, T> claimsFunction){
        Claims claims = getClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }
}
