package com.gopizza.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret:mySecretKeyForJWTTokenGenerationThatShouldBeAtLeast256BitsLong}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}") // 24 horas em milissegundos
	private long jwtExpiration;

	private SecretKey getSigningKey() {
		// Garantir que a chave tenha pelo menos 256 bits (32 bytes) para HMAC SHA-256
		if (jwtSecret == null || jwtSecret.isEmpty()) {
			throw new IllegalStateException("JWT_SECRET não pode estar vazio. Configure a variável de ambiente JWT_SECRET.");
		}
		
		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
		
		// Se a chave for menor que 32 bytes, repetir até atingir o tamanho mínimo
		if (keyBytes.length < 32) {
			byte[] expandedKey = new byte[32];
			for (int i = 0; i < 32; i++) {
				expandedKey[i] = keyBytes[i % keyBytes.length];
			}
			keyBytes = expandedKey;
		}
		
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(UUID userId, String email) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpiration);

		return Jwts.builder()
				.subject(userId.toString())
				.claim("email", email)
				.issuedAt(now)
				.expiration(expiryDate)
				.signWith(getSigningKey())
				.compact();
	}

	public UUID getUserIdFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return UUID.fromString(claims.getSubject());
	}

	public String getEmailFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.get("email", String.class);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(getSigningKey())
					.build()
					.parseSignedClaims(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
}
