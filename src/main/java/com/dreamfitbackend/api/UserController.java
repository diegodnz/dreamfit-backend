package com.dreamfitbackend.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.CredentialsInput;
import com.dreamfitbackend.configs.security.CredentialsOutput;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.EmailRecovery;
import com.dreamfitbackend.domain.usuario.models.PasswordModify;
import com.dreamfitbackend.domain.usuario.models.UserInputModify;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.models.UserOutputComplete;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;
import com.dreamfitbackend.domain.usuario.services.UserGeneralServices;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserGeneralServices userGeneralServices;
	
	@Autowired
	private Auth authorization;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserInputRegister userInputRegister, HttpServletRequest req) {
		authorization.auth(userRepo, req, Permissions.ADM,  "Sem permissão", HttpStatus.UNAUTHORIZED);		
		userGeneralServices.register(userInputRegister);
	}
	
	@PostMapping("/recovery-token/{token}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage tokenRecovery(@PathVariable String token, @Valid @RequestBody PasswordModify newPassword) {
		return userGeneralServices.resetPassword(token, newPassword.getNewPassword(), newPassword.getConfirmNewPassword());
	}
	
	@PostMapping("/recovery-password")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage sendMail(@Valid @RequestBody EmailRecovery emailRecovery) {
		return userGeneralServices.sendPasswordToken(emailRecovery.getEmail());
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput login(@Valid @RequestBody CredentialsInput credentialsInput) {
		return userGeneralServices.login(credentialsInput);
	}
	
	@GetMapping("/students")
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputList> listStudents(HttpServletRequest req) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF, "Sem permissão", HttpStatus.UNAUTHORIZED);		
		return userGeneralServices.listByRole(Role.STUDENT);
	}
	
	@GetMapping("/teachers")
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputList> listTeachers(HttpServletRequest req) {
		authorization.auth(userRepo, req, Permissions.ADM, "Sem permissão", HttpStatus.UNAUTHORIZED);		
		return userGeneralServices.listByRole(Role.TEACHER);
	}
	
}
