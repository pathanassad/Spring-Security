package com.asad.SpringSecurity.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {


private String secretKey;

public JWTService(){
    try {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGen.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}

private SecretKey getKey(){
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
}

public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 10))
            .and()
            .signWith(getKey())
            .compact();
}

private Claims extractAllClaims(String token){
    return Jwts.parser()
            .verifyWith(getKey())
            .build().parseSignedClaims(token).getPayload();
}

private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
}

public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
}

public boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

}

private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);

}

private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}


}
