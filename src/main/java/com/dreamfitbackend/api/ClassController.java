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

import com.dreamfitbackend.configs.exceptions.Problem;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.gymclass.models.ClassInputRegisterMany;
import com.dreamfitbackend.domain.gymclass.services.ClassServices;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/classes")
public class ClassController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private ClassServices classServices;
	
	@Autowired
	private Auth authorization;
	
//	// ** Criar aulas **
//	@PostMapping("/schedule")
//	@ResponseStatus(HttpStatus.CREATED)
//	public StatusMessage registerClasses(HttpServletRequest req, @Valid @RequestBody ClassInputRegisterMany classesInput) {
//		authorization.auth(userRepo, req, Permissions.ADM_PROF, "Sem permissão", HttpStatus.UNAUTHORIZED);
//		return classServices.registerClasses(classesInput);
//	}
	
	// ** Agendar aula **
	@ApiOperation(value = "Agendar aula", notes = "Esta operação permite que um aluno ou professor agende sua aula", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "Aula agendada com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso haja algum impedimento, será retornada a mensagem de erro descrevendo-o"),
			@ApiResponse(code = 401, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por Professor ou Aluno"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping("/schedule/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage makeAppointment(HttpServletRequest req, @PathVariable Long id) {
		User user = authorization.auth(userRepo, req, Permissions.PROF_STUDENT, "Somente usuários da academia podem marcar aulas", HttpStatus.FORBIDDEN);		
		return classServices.makeAppointment(id, user);
	}

}
