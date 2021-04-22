package com.dreamfitbackend.configs.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

@Configuration
public class Auth {
		
	@Autowired
	private JWTUtil jwtUtil;
	
	public User auth(UserRepository userRepo, HttpServletRequest req, List<Integer> permissions) {
		User user = jwtUtil.verifyToken(userRepo, req, permissions);
		if (user == null) {
			throw new MessageException("Faça login", HttpStatus.FORBIDDEN);
		}
		return user;
	}

}
