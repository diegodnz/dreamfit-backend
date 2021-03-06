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
import org.springframework.web.bind.annotation.PutMapping;
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
import com.dreamfitbackend.domain.user_measures.models.UserMeasuresInputUpdate;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.EmailRecovery;
import com.dreamfitbackend.domain.usuario.models.PasswordModify;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.models.UserInputUpdatePassword;
import com.dreamfitbackend.domain.usuario.models.UserOutputComplete;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;
import com.dreamfitbackend.domain.usuario.models.UserOutputPublic;
import com.dreamfitbackend.domain.usuario.models.UserOutputUuid;
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
	
	
	// ** Cadastrar Usu??rio **
	@ApiOperation(value = "Cadastrar usu??rio", notes = "Esta opera????o permite que a academia cadastre um novo aluno ou professor.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, response = UserOutputUuid.class, message = "O usu??rio foi cadastrado com sucesso. Retorna o uuid do novo usu??rio"),
			@ApiResponse(code = 400, response = Problem.class, message = "Caso haja campos preechidos incorretamente, ser??o retornadas mensagens de erro para cada campo incorreto com o nome e descri????o do mesmo"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por ADM"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserOutputUuid register(@Valid @RequestBody UserInputRegister userInputRegister, HttpServletRequest req) {
		authorization.auth(userRepo, req, Permissions.ADM);		
		return new UserOutputUuid(userGeneralServices.register(userInputRegister));
	}
	
	
	// ** Alterar Senha **
	@ApiOperation(value = "Alterar senha", notes = "Esta opera????o permite que o usu??rio que esqueceu sua senha mude para uma senha nova")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "O token passado na url ?? inv??lido ou a confirma????o de senha n??o coincide com a nova senha"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
	@PostMapping("/recovery-token/{token}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage tokenRecovery(@PathVariable String token, @Valid @RequestBody PasswordModify newPassword) {
		return userGeneralServices.resetPassword(token, newPassword.getNewPassword(), newPassword.getConfirmNewPassword());
	}
	
	
	// ** E-mail para recurepa????o de senha **
	@ApiOperation(value = "Solicitar e-mail para recurepa????o de senha", notes = "Esta opera????o permite que o usu??rio insira seu e-mail para receber o token de recupera????o de senha")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "E-mail enviado com sucesso"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Ser?? retornado caso o e-mail n??o seja encontrado no sistema"),		
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })
	@PostMapping("/recovery-password")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage sendMail(@Valid @RequestBody EmailRecovery emailRecovery) {
		return userGeneralServices.sendPasswordToken(emailRecovery.getEmail());
	}
	
	
	// ** Login (Receber Bearer Token) **
	@ApiOperation(value = "Login no sistema (Recebe bearer token no corpo de resposta)", notes = "Esta opera????o permite que o usu??rio insira seu e-mail e senha para receber seu token de acesso ao sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = CredentialsOutput.class, message = "Logado com sucesso. Retorna o token que dever?? ser passado no header nas pr??ximas requisi????es"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Ser?? retornado este c??digo caso o e-mail n??o seja encontrado no sistema"),		
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput login(@Valid @RequestBody CredentialsInput credentialsInput) {
		return userGeneralServices.login(credentialsInput);
	}
	
	
	// ** Listar os alunos da academia **
	@ApiOperation(value = "Listar alunos", notes = "Esta opera????o permite que a academia e professores possam listar os alunos. Neste mesmo endpoint tamb??m ?? poss??vel buscar alunos por nome, cpf ou e-mail", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputList.class, responseContainer = "List", message = "Retorna os alunos da academia"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso o par??metro de busca recebido pela api n??o corresponda ?? Nome, Cpf ou Email"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por ADM ou professor"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
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
	@ApiOperation(value = "Listar professores", notes = "Esta opera????o permite que a academia possa listar todos os professores. Neste mesmo endpoint tamb??m ?? poss??vel buscar professores por nome, cpf ou e-mail", authorizations = {
			@Authorization(value = "JWT") })
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputList.class, responseContainer = "List", message = "Retorna os professores da academia"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso o par??metro de busca recebido pela api n??o corresponda ?? Nome, Cpf ou Email"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por ADM"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
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
	
	
	// ** Perfil p??blico do usu??rio **
	@ApiOperation(value = "Perfil p??blico do usu??rio", notes = "Esta opera????o permite que um usu??rio logado possa obter o perfil p??blico de algu??m.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputPublic.class, message = "Retorna o perfil p??blico do usu??rio"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por um usu??rio logado"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este c??digo caso o usu??rio n??o seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/public_profile/{uuid}")
	@ResponseStatus(HttpStatus.OK)
	public UserOutputPublic publicProfile(HttpServletRequest req, @PathVariable String uuid) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		return userGeneralServices.publicProfile(uuid);
	}
	
	
	// ** Perfil completo de usu??rio **
	@ApiOperation(value = "Perfil completo do usu??rio", notes = "Esta opera????o permite que a academia ou professor possa obter o perfil completo de algu??m. Um aluno s?? poder?? obter seu pr??prio perfil completo", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = UserOutputComplete.class, message = "Retorna o perfil completo do usu??rio"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado pela academia, um professor ou o aluno dono do perfil solicitado"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este c??digo caso o usu??rio n??o seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
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
	@ApiOperation(value = "Adicionar lista de treino para um aluno", notes = "Esta opera????o permite que a academia ou professor adicionem uma lista de treino para um aluno", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "O Treino foi atribu??do ao aluno. Sem retorno"),
			@ApiResponse(code = 400, response = Problem.class, message = "Caso haja campos preechidos incorretamente, ser??o retornadas mensagens de erro para cada campo incorreto com o nome e descri????o do mesmo"),		
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado pela academia ou professor"),			
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este c??digo caso o usu??rio n??o seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
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
	@ApiOperation(value = "Obter lista de treino", notes = "Esta opera????o permite que a academia ou professor veja a lista de treinos de um aluno. O aluno s?? pode acessar sua pr??pria lista de treino", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = ExerciseOutputComplete.class, message = "Retorna a lista de treinos"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado pela academia, professor. Um aluno s?? pode aceessar sua pr??pria lista de treino"),
			@ApiResponse(code = 404, response = StatusMessage.class, message = "Retorna este c??digo caso o usu??rio n??o seja encontrado no sistema"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
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
	@ApiOperation(value = "Atualizar/Verificar token", notes = "Esta opera????o permite o usu??rio verifique a validade do seu token. Caso o token esteja perto da expira????o, o mesmo ?? atualizado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = CredentialsOutput.class, message = "Caso o token esteja perto da expira????o, ser?? retornado um novo token"),
			@ApiResponse(code = 202, response = StatusMessage.class, message = "Token v??lido"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Token expirado ou inv??lido") })	
	@GetMapping("/token")
	@ResponseStatus(HttpStatus.OK)
	public CredentialsOutput refreshToken(HttpServletRequest req) {
		CredentialsOutput newToken = jwtUtil.refreshToken(userRepo, req);
		return newToken;
	}
	
	
	// ** Atualizar medidas do aluno **
	@ApiOperation(value = "Atualizar medidas do aluno", notes = "Esta opera????o permite que um professor ou a academia atualize as medidas de um aluno", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 204, response = StatusMessage.class, message = "As medidas foram atualizadas"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "O uuid do usu??rio informado ?? inv??lido"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por um professor ou ADM."),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
	@ApiImplicitParam(name = "Authorization",
		value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
		required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/measures")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage updateMeasures(HttpServletRequest req, @Valid @RequestBody UserMeasuresInputUpdate userMeasures) {
		authorization.auth(userRepo, req, Permissions.ADM_PROF);
		return userGeneralServices.updateMeasures(userMeasures);
	}
	
	// ** Atualizar senha logado **
	@ApiOperation(value = "Atualizar senha logado", notes = "Esta opera????o permite que um professor ou aluno atualize a sua senha enquanto est?? logado", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = StatusMessage.class, message = "A senha foi alterada"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "A senha atual passada n??o est?? correta ou as senhas novas n??o coincidem (newPassword e confirmNewPassword)"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado ?? inv??lido ou n??o possui a permiss??o para acessar este recurso. Este recurso s?? pode ser acessado por um usu??rio logado."),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisi????o") })	
	@ApiImplicitParam(name = "Authorization",
		value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
		required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/password")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage updatePassword(HttpServletRequest req, @Valid @RequestBody UserInputUpdatePassword updatePasswordInput) {
		User loggedUser = authorization.auth(userRepo, req, Permissions.PROF_STUDENT);
		return userGeneralServices.updatePassword(loggedUser, updatePasswordInput);
	}
	
}
