package com.dreamfitbackend.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.gymclass.models.ClassInputRegisterMany;
import com.dreamfitbackend.domain.gymclass.services.ClassServices;
import com.dreamfitbackend.domain.usuario.UserRepository;

@RestController
@RequestMapping("/classes")
public class ClassController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private ClassServices classServices;
	
	// ** Criar aulas **
	@PostMapping("/schedule")
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage registerClasses(HttpServletRequest req, @Valid @RequestBody ClassInputRegisterMany classesInput) {
		Auth.auth(userRepo, req, Permissions.ADM_PROF, "Sem permissão", HttpStatus.UNAUTHORIZED);
		return classServices.registerClasses(classesInput);
	}
	
	// ** Agendar aula **
	@PostMapping("/schedule/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage makeAppointment(HttpServletRequest req, @PathVariable Long id) {
		String userCpf = Auth.auth(userRepo, req, Permissions.PROF_STUDENT, "Somente usuários da academia podem marcar aulas", HttpStatus.FORBIDDEN);		
		return classServices.makeAppointment(id, userCpf);
	}

}
