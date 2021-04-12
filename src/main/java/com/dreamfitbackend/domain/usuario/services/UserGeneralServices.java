package com.dreamfitbackend.domain.usuario.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.configs.exceptions.FieldsException;
import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.exceptions.Problem.Field;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.CredentialsInput;
import com.dreamfitbackend.configs.security.CredentialsOutput;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.domain.gymclass.Class;
import com.dreamfitbackend.domain.gymclass.ClassRepository;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;

@Service
public class UserGeneralServices {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public void register(UserInputRegister userInputRegister) {
		List<Field> errorFields = new ArrayList<>();
		
		User userCpf = userRepo.findByCpf(userInputRegister.getCpf());
		if (userCpf != null) {
			errorFields.add(new Field("cpf", "Cpf já existente"));
		}
		
		User userEmail = userRepo.findByEmail(userInputRegister.getEmail());
		if (userEmail != null) {
			errorFields.add(new Field("email", "E-mail já existente"));
		}
		
		if (!errorFields.isEmpty()) {
			throw new FieldsException(HttpStatus.BAD_REQUEST, errorFields);
		}
		
		User newUser = new User();
		Mapper.copyProperties(userInputRegister, newUser);		
		
		newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
		userRepo.save(newUser);
	}
	
	public CredentialsOutput login(CredentialsInput credentialsInput) {
		User user = userRepo.findByCpf(credentialsInput.getCpf());
		if (user == null) {
			throw new MessageException("Cpf inválido", HttpStatus.BAD_REQUEST);
		}
		
		boolean matchPassword = BCrypt.checkpw(credentialsInput.getPassword(), user.getPassword());
		if (!matchPassword) {
			throw new MessageException("Senha errada", HttpStatus.BAD_REQUEST);
		} else {
			String token = jwtUtil.generateToken(user.getCpf(), user.getRole_user().getCod());
			return new CredentialsOutput(token);			
		}
	}

}
