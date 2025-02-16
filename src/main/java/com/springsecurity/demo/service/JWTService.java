package com.springsecurity.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {

    String extractUsername(String jwtToken);

    String generateToken(UserDetails userDetails);

    boolean isValidateToken(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
}
