package com.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	private final String SECRET_STRING="aVeryLongAndMuchMoreSecureSecretKeyForMyApplication8883837";
	
	private final Key SECRET_KEY;
	
	public JwtUtil() {
		this.SECRET_KEY=Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(String email,String role) {
		
		Long  tokenExpirationTime=System.currentTimeMillis() + 300000;
		
		return Jwts.builder()
				.setSubject(email)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(tokenExpirationTime))
				.signWith(SECRET_KEY,SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	public String extractEmail(String token) {
		return getClaim(token).getSubject(); 
	}
	
	
	@SuppressWarnings("deprecation")
	public Claims getClaim(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	            .setSigningKey(SECRET_KEY)
	            .build()
	            .parseClaimsJws(token);

	        return true;

	    } catch (Exception e) {
	        return false;
	    }
	}

	

}
