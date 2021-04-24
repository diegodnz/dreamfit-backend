package com.dreamfitbackend.api;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.exceptions.Problem;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.configs.storage.AmazonClient;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.UserInputUuid;
import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/files")
public class FilesController {

	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private Auth auth;
	
	@Autowired
	private UserRepository userRepo;

	// ** Atualizar foto do usuário **
	@ApiOperation(value = "Atualizar foto do usuário", notes = "Esta operação permite que a academia atualize a foto do perfil de um usuário. O próprio usuário também tem autorização para atualizar sua foto de perfil", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Foto atualizada com sucesso!"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "O uuid passado no body é inválido ou houve algum erro no upload da imagem"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia, trocando a foto do perfil de qualquer usuário, ou o próprio usuário trocando sua foto do perfil"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setProfilePicture(@RequestPart(value = "image") MultipartFile image, @RequestPart(value = "uuid") String uuid, HttpServletRequest req) {
    	User loggedUser = auth.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
    	User updateUser = amazonClient.checkUser(loggedUser, uuid);
    	String fileName = updateUser.getUuid() + System.currentTimeMillis();
    	String fileUrl = amazonClient.uploadFile(image, fileName);
    	amazonClient.deleteFileFromS3Bucket(updateUser.getProfilePicture());
    	amazonClient.saveNewUrl(updateUser, fileUrl);  	
    }

	
	// ** Remover foto do usuário **
	@ApiOperation(value = "Remover foto do usuário", notes = "Esta operação permite que a academia remova a foto do perfil de um usuário. O próprio usuário também tem autorização para remover sua foto de perfil", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Foto removida com sucesso!"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "O uuid passado no body é inválido ou houve algum erro no upload da imagem"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado pela academia, removendo a foto do perfil de qualquer usuário, ou o próprio usuário removendo sua foto do perfil"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'", 
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@RequestPart(value = "url") String fileUrl, @RequestPart(value = "uuid") String uuid, HttpServletRequest req) {
		User loggedUser = auth.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		User updateUser = amazonClient.checkUser(loggedUser, uuid);
        amazonClient.deleteFileFromS3Bucket(fileUrl);
        amazonClient.saveNewUrl(updateUser, null);
    }
    
}