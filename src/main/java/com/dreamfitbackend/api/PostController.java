package com.dreamfitbackend.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.Auth;
import com.dreamfitbackend.configs.security.Permissions;
import com.dreamfitbackend.domain.post.PostRepository;
import com.dreamfitbackend.domain.post.models.PostOutputListElement;
import com.dreamfitbackend.domain.post.services.PostServices;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private Auth auth;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	// ** Listar os posts do feed **
	@ApiOperation(value = "Listar os posts do feed", notes = "Esta operação permite que um usuário logado receba os posts do feed.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = PostOutputListElement.class, responseContainer = "List", message = "Retorna os posts com base na página passada como parâmetro (São retornador no máximo 5 posts por página)"),
			@ApiResponse(code = 400, message = "Caso o parâmetro 'page' esteja incorreto (Deve ser inteiro)"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um usuário logado"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PostOutputListElement> listAll(@RequestParam(name = "page") int page) {
		return postServices.list(page);
	}
	
	
	// ** Postar no feed **
	@ApiOperation(value = "Postar no feed", notes = "Esta operação permite que um usuário logado faça um post no feed.", authorizations = {
			@Authorization(value = "JWT") })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Postagem criada"),
			@ApiResponse(code = 400, response = StatusMessage.class, message = "Caso a descrição e a imagem passadas sejam nulas (É necessário que, pelo menos, um destes campos não sejam nulos). Também pode ocorrer um erro no upload da imagem caso o tamanho dela exceda o limite"),
			@ApiResponse(code = 403, response = StatusMessage.class, message = "O token passado é inválido ou não possui a permissão para acessar este recurso. Este recurso só pode ser acessado por um usuário logado"),			
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da requisição") })	
	@ApiImplicitParam(name = "Authorization", 
	value = "Um Bearer Token deve ser passado no header 'Authorization'. \nEx: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.7g5IV9YbjporuxChCooCAgHxIibCz-Yh3Yq3qIn0dsY'",
	required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createPost(HttpServletRequest req, @RequestPart(value = "description", required = false) String description, @RequestPart(value = "image", required = false) MultipartFile image) {
		User loggedUser = auth.auth(userRepo, req, Permissions.ADM_PROF_STUDENT);
		postServices.create(loggedUser, description, image);
	}

}
