package com.dreamfitbackend.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dreamfitbackend.configs.exceptions.Problem;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.rewards.models.RewardInputRegister;
import com.dreamfitbackend.domain.rewards.models.RewardOutputList;
import com.dreamfitbackend.domain.rewards.services.RewardServices;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/rewards")
public class RewardController {
	
	@Autowired
	private RewardServices rewardServices;
	
	@Autowired
	private Auth authorization;
	
	@Autowired
	private UserRepository userRepo;
	
	
	// ** Cadastrar um produto de recompensa **
	@ApiOperation(value = "Cadastrar um produto de recompensa", notes = "Esta operação permite que a academia cadastre um produto para ser comprado com fitcoins", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Produto adicionado com sucesso. Sem retorno"),
			@ApiResponse(code = 400, response = Problem.class, message = "Retornará caso um ou mais campos estejam nulos ou incorretos"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody RewardInputRegister rewardInputRegister) {
		rewardServices.register(rewardInputRegister);
	}
	
	
	// ** Retorna as recompensas cadastradas **
	@ApiOperation(value = "Retorna as recompensas cadastradas", notes = "Esta operação permite que um usuário veja os produtos da loja", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = RewardOutputList.class, message = "Retorna os produtos da loja"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um usuário logado"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",  
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public RewardOutputList listAll(HttpServletRequest req) {
		User loggedUser = authorization.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		return rewardServices.listAll(loggedUser);
	}

}
