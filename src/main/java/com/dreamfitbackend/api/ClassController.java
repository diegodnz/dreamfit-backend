package com.dreamfitbackend.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.JWTUtil;
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
	
	@PostMapping("/schedule/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage schedule(HttpServletRequest req, @PathVariable Long id) {
		String userCpf = jwtUtil.verifyToken(userRepo, req, Permissions.PROF_STUDENT);
		if (userCpf == null) {
			throw new MessageException("Somente usuários da academia podem marcar aulas", HttpStatus.FORBIDDEN);
		}
		return classServices.schedule(id, userCpf);
	}

}
