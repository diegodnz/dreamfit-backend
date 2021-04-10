package com.dreamfitbackend.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.services.UserGeneralServices;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UserGeneralServices userGeneralServices;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserInputRegister userInputRegister) {
		userGeneralServices.register(userInputRegister);
	}
}
