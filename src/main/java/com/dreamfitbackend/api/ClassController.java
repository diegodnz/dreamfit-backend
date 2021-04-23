package com.dreamfitbackend.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.dreamfitbackend.domain.gymclass.models.ClassOutputList;
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
		
	// ** Agendar aula **
	@ApiOperation(value = "Agendar aula", notes = "Esta operação permite que um aluno agende sua aula", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "Aula agendada com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso haja algum impedimento, será retornada a mensagem de erro descrevendo-o"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um Aluno"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping("/schedule/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage makeAppointment(HttpServletRequest req, @PathVariable Long id) {
		User user = authorization.auth(userRepo, req, Permissions.STUDENT);		
		return classServices.makeAppointment(id, user);
	}
	
	
	// ** Desfazer agendamento de aula **
	@ApiOperation(value = "Desfazer agendamento de aula", notes = "Esta operação permite que um aluno desfaça seu agendamento", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "O agendamento foi cancelado"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso haja algum impedimento, será retornada a mensagem de erro descrevendo-o"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um Aluno"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/schedule/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage deleteAppointment(HttpServletRequest req, @PathVariable Long id) {
		User user = authorization.auth(userRepo, req, Permissions.STUDENT);		
		return classServices.deleteAppointment(id, user);
	}
	
	
	// ** Listar aulas **
	@ApiOperation(value = "Listar aula", notes = "Esta operação permite que um usuário receba uma lista de aulas do dia atual", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = ClassOutputList.class, message = "Recebe a lista de aulas do dia"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um usuário logado"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/schedule")
	@ResponseStatus(HttpStatus.OK)
	public ClassOutputList getClasses(HttpServletRequest req) {
		User user = authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);		
		return classServices.getClasses(user);
	}

}
