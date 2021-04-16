package com.dreamfitbackend.configs.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.usuario.UserRepository;

public class Auth {
		
	public static String auth(UserRepository userRepo, HttpServletRequest req, List<Integer> permissions, String message, HttpStatus error) {
		JWTUtil jwtUtil = new JWTUtil();
		String userCpf = jwtUtil.verifyToken(userRepo, req, permissions);
		if (userCpf == null) {
			throw new MessageException(message, error);
		}
		return userCpf;
	}

}
