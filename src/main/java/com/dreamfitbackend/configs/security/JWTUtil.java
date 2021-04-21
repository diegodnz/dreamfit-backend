package com.dreamfitbackend.configs.security;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.passwordSecret}")
	private String passwordSecret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String cpf, Integer role) {
		return Jwts.builder()
				.setSubject(cpf)		
				.claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public String generatePasswordToken(String cpf, Long expirationTime) {
		return Jwts.builder()
				.setSubject(cpf)		
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, passwordSecret.getBytes())
				.compact();
	}
	
	public User verifyToken(UserRepository userRepo ,HttpServletRequest req, List<Integer> roles) {
		try {
			String token = (String)req.getHeader("Authorization").substring(7);	
			return validToken(userRepo, token, roles);
		} catch (Exception e) {			
			return null;
		}
	}
	
	public User validToken(UserRepository userRepo, String token, List<Integer> roles) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String cpf = claims.getSubject();
			User user = userRepo.findByCpf(cpf);
			Integer roleToken = claims.get("role", Integer.class);
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());			
			if (roles.contains(roleToken) && user != null && expirationDate != null && now.before(expirationDate)) {
				return user;
			}
		}	
		return null;
	}
	
	public OffsetDateTime expirationToken(String token) {
		Claims claims = getClaims(token);
		Date expirationDate = claims.getExpiration();
		OffsetDateTime offsetDateTime = expirationDate.toInstant().atOffset(ZoneOffset.UTC);
		return offsetDateTime;
	}

	public String getCpf(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	public Claims getClaims(String token) {
		try {			
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
