package com.dreamfitbackend.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.security.CredentialsInput;
import com.dreamfitbackend.configs.security.CredentialsOutput;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.services.UserGeneralServices;

@RestController
@RequestMapping("/users")
public class UsuarioController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserGeneralServices userGeneralServices;
	
	private final List<Integer> ADM = Arrays.asList(Role.ADMIN.getCod());
	
	private final List<Integer> ADM_PROF = Arrays.asList(Role.ADMIN.getCod(), Role.PROFESSOR.getCod());
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserInputRegister userInputRegister, HttpServletRequest req) {
		if (!jwtUtil.verifyToken(userRepo, req, ADM)) {
			throw new MessageException("Sem autorização", HttpStatus.UNAUTHORIZED);
		}
		userGeneralServices.register(userInputRegister);
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput login(@Valid @RequestBody CredentialsInput credentialsInput) {
		return userGeneralServices.login(credentialsInput);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> list(HttpServletRequest req) {
		if (!jwtUtil.verifyToken(userRepo, req, ADM_PROF)) {
			throw new MessageException("Sem autorização", HttpStatus.UNAUTHORIZED);
		}
		return userRepo.findAll();
	}
}
