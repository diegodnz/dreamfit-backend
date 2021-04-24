package com.dreamfitbackend.api;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.exceptions.Problem;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.CredentialsInput;
import com.dreamfitbackend.configs.security.CredentialsOutput;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.exercise.models.ExerciseInputRegister;
import com.dreamfitbackend.domain.exercise.models.ExerciseOutputComplete;
import com.dreamfitbackend.domain.exercise.services.ExerciseServices;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.EmailRecovery;
import com.dreamfitbackend.domain.usuario.models.PasswordModify;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.models.UserOutputComplete;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;
import com.dreamfitbackend.domain.usuario.models.UserOutputPublic;
import com.dreamfitbackend.domain.usuario.services.UserGeneralServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

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
	private ExerciseServices exerciseServices;
	
	@Autowired
	private Auth authorization;
	
	
	// ** Cadastrar Usuário **
	@ApiOperation(value = "Cadastrar usuário", notes = "Esta operação permite que a academia cadastre um novo aluno ou professor.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "O usuário foi cadastrado com sucesso. Sem retorno"),
			@ApiResponse(code = 400, response = Problem.class, message = "Caso haja campos preechidos incorretamente, serão retornadas mensagens de erro para cada campo incorreto com o nome e descrição do mesmo"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por ADM"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserInputRegister userInputRegister, HttpServletRequest req) {
		authorization.auth(userRepo, req, Permissions.ADM);		
		userGeneralServices.register(userInputRegister);
	}
	
	
	// ** Alterar Senha **
	@ApiOperation(value = "Alterar senha", notes = "Esta operação permite que o usuário que esqueceu sua senha mude para uma senha nova")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "O token passado na url é inválido ou a confirmação de senha não coincide com a nova senha"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@PostMapping("/recovery-token/{token}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage tokenRecovery(@PathVariable String token, @Valid @RequestBody PasswordModify newPassword) {
		return userGeneralServices.resetPassword(token, newPassword.getNewPassword(), newPassword.getConfirmNewPassword());
	}
	
	
	// ** E-mail para recurepação de senha **
	@ApiOperation(value = "Solicitar e-mail para recurepação de senha", notes = "Esta operação permite que o usuário insira seu e-mail para receber o token de recuperação de senha")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "E-mail enviado com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Será retornado caso o e-mail não seja encontrado no sistema"),		
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })
	@PostMapping("/recovery-password")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage sendMail(@Valid @RequestBody EmailRecovery emailRecovery) {
		return userGeneralServices.sendPasswordToken(emailRecovery.getEmail());
	}
	
	
	// ** Login (Receber Bearer Token) **
	@ApiOperation(value = "Login no sistema (Recebe bearer token no corpo de resposta)", notes = "Esta operação permite que o usuário insira seu e-mail e senha para receber seu token de acesso ao sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = CredentialsOutput.class, message = "Logado com sucesso. Retorna o token que deverá ser passado no header nas próximas requisições"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Será retornado este código caso o e-mail não seja encontrado no sistema"),		
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput login(@Valid @RequestBody CredentialsInput credentialsInput) {
		return userGeneralServices.login(credentialsInput);
	}
	
	
	// ** Listar os alunos da academia **
	@ApiOperation(value = "Listar alunos", notes = "Esta operação permite que a academia e professores possam listar os alunos. Neste mesmo endpoint também é possível buscar alunos por nome, cpf ou e-mail", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputList.class, responseContainer = "List", message = "Retorna os alunos da academia"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso o parâmetro de busca recebido pela api não corresponda à Nome, Cpf ou Email"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por ADM ou professor"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/students")
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputList> listStudents(HttpServletRequest req, 
			@RequestParam(name = "type", required = false) @ApiParam(value = "Tipo da busca (Ex: Buscar por nome, buscar por cpf...)", allowableValues = "Nome, Cpf, Email") String type,
			@RequestParam(name = "search", required = false) @ApiParam(value = "O texto da busca (Ex: Diego, 13958473844...)") String search) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF);	
		return userGeneralServices.listByRole(Role.STUDENT, type, search);
	}
	
	
	// ** Listar os professores da academia **
	@ApiOperation(value = "Listar professores", notes = "Esta operação permite que a academia possa listar todos os professores. Neste mesmo endpoint também é possível buscar professores por nome, cpf ou e-mail", authorizations = {
			@Authorization(value = "JWT") })
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputList.class, responseContainer = "List", message = "Retorna os professores da academia"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso o parâmetro de busca recebido pela api não corresponda à Nome, Cpf ou Email"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por ADM"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/teachers")
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputList> listTeachers(HttpServletRequest req, 
			@RequestParam(name = "type", required = false) @ApiParam(value = "Tipo da busca (Ex: Buscar por nome, buscar por cpf...)", allowableValues = "Nome, Cpf, Email") String type,	 														 
			@RequestParam(name = "search", required = false) @ApiParam(value = "O texto da busca (Ex: Diego, 13958473844...)") String search) {
		authorization.auth(userRepo, req, Permissions.ADM);		
		return userGeneralServices.listByRole(Role.TEACHER, type, search);
	}
	
	
	// ** Perfil público do usuário **
	@ApiOperation(value = "Perfil público do usuário", notes = "Esta operação permite que um usuário logado possa obter o perfil público de alguém.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputPublic.class, message = "Retorna o perfil público do usuário"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um usuário logado"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este código caso o usuário não seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/public_profile/{uuid}")
	@ResponseStatus(HttpStatus.OK)
	public UserOutputPublic publicProfile(HttpServletRequest req, @PathVariable String uuid) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		return userGeneralServices.publicProfile(uuid);
	}
	
	
	// ** Perfil completo de usuário **
	@ApiOperation(value = "Perfil completo do usuário", notes = "Esta operação permite que a academia ou professor possa obter o perfil completo de alguém. Um aluno só poderá obter seu próprio perfil completo", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputComplete.class, message = "Retorna o perfil completo do usuário"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia, um professor ou o aluno dono do perfil solicitado"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este código caso o usuário não seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/private_profile/{uuid}")
	@ResponseStatus(HttpStatus.OK)
	public UserOutputComplete profile(HttpServletRequest req, @PathVariable String uuid) {
		User loggedUser = authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		return userGeneralServices.profile(uuid, loggedUser);
	}
	
	
	// ** Adicionar lista de treino para um aluno **
	@ApiOperation(value = "Adicionar lista de treino para um aluno", notes = "Esta operação permite que a academia ou professor adicionem uma lista de treino para um aluno", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "O Treino foi atribuído ao aluno. Sem retorno"),
			@ApiResponse(code = 400, response = Problem.class, message = "Caso haja campos preechidos incorretamente, serão retornadas mensagens de erro para cada campo incorreto com o nome e descrição do mesmo"),		
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia ou professor"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este código caso o usuário não seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping("/exercises/{uuid}")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerExercises(HttpServletRequest req, @Valid @RequestBody ExerciseInputRegister exerciseInputRegister,@PathVariable String uuid) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF);
		exerciseServices.register(exerciseInputRegister, uuid);
	}
	
	
	// ** Obter lista de treino **
	@ApiOperation(value = "Obter lista de treino", notes = "Esta operação permite que a academia ou professor veja a lista de treinos de um aluno. O aluno só pode acessar sua própria lista de treino", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = ExerciseOutputComplete.class, message = "Retorna a lista de treinos"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia, professor. Um aluno só pode aceessar sua própria lista de treino"),
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este código caso o usuário não seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/exercises/{uuid}")
	@ResponseStatus(HttpStatus.OK)
	public ExerciseOutputComplete exercises(HttpServletRequest req, @PathVariable String uuid) {
		User loggedUser = authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		return exerciseServices.getExercises(loggedUser, uuid);
	}
	
	
	// ** Atualizar/Verificar token **
	@ApiOperation(value = "Atualizar/Verificar token", notes = "Esta operação permite o usuário verifique a validade do seu token. Caso o token esteja perto da expiração, o mesmo é atualizado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = CredentialsOutput.class, message = "Caso o token esteja perto da expiração, será retornado um novo token"),
			@ApiResponse(code = 202, response = StatusMessage.class, message = "Token válido"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Token expirado ou inválido") })	
	@GetMapping("/token")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput refreshToken(HttpServletRequest req) {
		CredentialsOutput newToken = jwtUtil.refreshToken(userRepo, req);
		return newToken;
	}
	
}
