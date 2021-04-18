package com.dreamfitbackend.configs.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.usuario.UserRepository;

@Configuration
public class Auth {
		
	@Autowired
	private JWTUtil jwtUtil;
	
	public String auth(UserRepository userRepo, HttpServletRequest req, List<Integer> permissions, String message, HttpStatus error) {
		String userCpf = jwtUtil.verifyToken(userRepo, req, permissions);
		if (userCpf == null) {
			throw new MessageException(message, error);
		}
		return userCpf;
	}

}
