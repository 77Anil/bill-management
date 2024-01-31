package com.billnex.billManage.service.Impl;

import com.billnex.billManage.service.JWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Override
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Date date = extractClaims(token, Claims::getExpiration);
        return (date.before(new Date()));
    }

    private Key getKey(){
        byte[] key = Decoders.BASE64.decode("SKJFSDFJSDSDLFSDLKFJLSDJFKLJ32432KLDFLKJSLKDFJS3432KSDJFL3R432849DSJKFKJ");
        return Keys.hmacShaKeyFor(key);
    }

   private  <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    @Override
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
}
