package com.smartjob.challange.util;

import com.smartjob.challange.repository.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 300000))
        .signWith(key)
        .compact();
  }

}

