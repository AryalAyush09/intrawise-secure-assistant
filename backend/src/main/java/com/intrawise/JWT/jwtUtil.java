package com.intrawise.JWT;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.intrawise.Enum.Role;

import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Component
public class jwtUtil {
     private Key key;
     
     @Value("${JWT_SECRET:mysupersecuresecretkeyforjwtgeneration123!}")
     private String secret;
     
     @Value("${jwt.token.expiration-days:20}")
     private long tokenExpirationDays;
     
     @Value("${jwt.reset.expiration-minutes:15}")
     private long resetTokenExpirationMinutes;
 
     @PostConstruct
     private void setup() {
    	this.key = Keys.hmacShaKeyFor(secret.getBytes()); 
     }   
     
     public String generateToken(Long userId, Role role) {
    	 return Jwts.builder()
    			 .setSubject(String.valueOf(userId))
    			 .claim("role", role.name())
    			 .setIssuedAt(new Date())
    			 .setExpiration(new Date(System.currentTimeMillis() +tokenExpirationDays *24*60*60)) //1day
    			 .signWith(key, SignatureAlgorithm.HS256)
    			 .compact();
     }
     
     public String generateResetToken(String email, Role role) {
    	 return Jwts.builder()
    			 .setSubject(email)
    			 .claim("role", role.name())
    			 .setIssuedAt(new Date())
    			 .setExpiration(new Date(System.currentTimeMillis() + resetTokenExpirationMinutes*60))  //15min
    			 .signWith(key, SignatureAlgorithm.HS256)
    			 .compact();
     }
     
     // extract user id or email from jwt token
     public String extractEmail(String token) {
    	 return extractAllClaims(token).getSubject();
     }
     
     // extract role from jwt token 
     public String extractRole(String token) {
    	 return extractAllClaims(token).get("role", String.class);
     }
     
     //check whether token is expired or not 
     public boolean isTokenExpried(String token) {
    	 return extractAllClaims(token).getExpiration().before(new Date());
     }
     
     //validate the token
     public boolean validateToken(String email, String token) {
    	 return extractEmail(token).equals(email) && isTokenExpried(token);
     }
     
     
     //method to extract all subejct,rokle from token
     public Claims extractAllClaims(String token) {
    	 return Jwts.parserBuilder()
    			 .setSigningKey(key)
    			 .build()
    			 .parseClaimsJws(token)
    			 .getBody();
     }
}
