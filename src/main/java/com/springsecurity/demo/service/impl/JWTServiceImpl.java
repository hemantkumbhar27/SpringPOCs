package com.springsecurity.demo.service.impl;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import com.springsecurity.demo.service.JWTService;

import javax.crypto.spec.SecretKeySpec;

@Service
public class JWTServiceImpl implements JWTService {

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*60))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();

//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() *1000*60*60))
//                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
//                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*60400000))  //7 days
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSiginKey() {
        byte[] key= Decoders.BASE64.decode("mySecretKey123amsbfbfabfabfbafbsafbaskfbaskfbfbbfkbfjbsfbksfkafvkvjshfdvfv");
        return Keys.hmacShaKeyFor(key);

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        return key;
//      return  Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//        MacAlgorithm SECRET_KEY = Jwts.SIG.HS256;
//        byte[] bytes = Base64.getDecoder()
//                .decode(SECRET_KEY.key().build().toString().getBytes(StandardCharsets.UTF_8));
//        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValidateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
