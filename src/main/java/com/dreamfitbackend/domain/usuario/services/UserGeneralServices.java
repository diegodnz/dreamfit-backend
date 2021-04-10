package com.dreamfitbackend.domain.usuario.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.configs.exceptions.FieldsException;
import com.dreamfitbackend.configs.exceptions.Problem.Field;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;

@Service
public class UserGeneralServices {
	
	@Autowired
	private UserRepository userRepo;
	
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

}
