package com.itau.itau.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

  @Value("${api.security.token.secret:my-secret-key}")
  private String secret;

  public String generateToken(Authentication authentication) {
    var username = authentication.getName();
    var algorithm = Algorithm.HMAC256(secret);

    return JWT.create()
        .withIssuer("itau-api")
        .withSubject(username)
        .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
        .sign(algorithm);
  }

  public String validateToken(String token) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("itau-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException | IllegalArgumentException e) {
      return null;
    }
  }

}
