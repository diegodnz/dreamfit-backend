package com.dreamfitbackend.domain.usuario.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.configs.exceptions.FieldsException;
import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.exceptions.Problem.Field;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.configs.security.CredentialsInput;
import com.dreamfitbackend.configs.security.CredentialsOutput;
import com.dreamfitbackend.configs.security.JWTUtil;
import com.dreamfitbackend.domain.gymclass.ClassRepository;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.models.UserInputSearch;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;

import io.jsonwebtoken.Claims;

@Service
public class UserGeneralServices {
	
    @Autowired 
    private JavaMailSender mailSender;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public List<UserOutputList> listByRole(Role role, UserInputSearch search) {
		List<UserOutputList> listOutput = new ArrayList<>();
		List<User> listUser = new ArrayList<>();		
		if (search == null || search.getType() == null || search.getSearch() == null) {
			listUser = userRepo.findAllByRole(role.getCod());
		} else {		
			if (search.getType().equals("Nome")) {
				listUser = userRepo.findAllByNameAndRole(search.getSearch(), role.getCod());
			} else if (search.getType().equals("Cpf")) {
				listUser = userRepo.findAllByCpfAndRole(search.getSearch(), role.getCod());
			} else if (search.getType().equals("Email")) {
				listUser = userRepo.findAllByEmailAndRole(search.getSearch(), role.getCod());
			} else {
				throw new MessageException("Tipo de busca inválida (Só são permitidas buscas por Nome, Cpf ou Email)", HttpStatus.BAD_REQUEST);
			}
		}
		for (User user : listUser) {
			listOutput.add(new UserOutputList(user.getName(), user.getEmail(), user.getCpf()));
		}
		return listOutput;
	}
	
	public void register(UserInputRegister userInputRegister) {
		List<Field> errorFields = new ArrayList<>();
		
		User userCpf = userRepo.findByCpf(userInputRegister.getCpf());
		if (userCpf != null) {
			errorFields.add(new Field("cpf", "Cpf já existente"));
		}
		
		User userEmail = userRepo.findByEmail(userInputRegister.getEmail());
		if (userEmail != null) {
			errorFields.add(new Field("email", "E-mail já existente"));
		}
		
		if (!errorFields.isEmpty()) {
			throw new FieldsException(HttpStatus.BAD_REQUEST, errorFields);
		}
		
		User newUser = new User();
		Mapper.copyProperties(userInputRegister, newUser);		
		
		newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
		userRepo.save(newUser);
	}
	
	public CredentialsOutput login(CredentialsInput credentialsInput) {
		User user = userRepo.findByCpf(credentialsInput.getCpf());
		if (user == null) {
			throw new MessageException("Cpf inválido", HttpStatus.BAD_REQUEST);
		}
		
		boolean matchPassword = BCrypt.checkpw(credentialsInput.getPassword(), user.getPassword());
		if (!matchPassword) {
			throw new MessageException("Senha incorreta", HttpStatus.BAD_REQUEST);
		} else {
			String token = jwtUtil.generateToken(user.getCpf(), user.getRole_user().getCod());
			return new CredentialsOutput(token);			
		}
	}
	
	public StatusMessage sendPasswordToken(String email) {       	
      	User user = userRepo.findByEmail(email);
		if (user != null) {					
			try {
    	        String token = jwtUtil.generatePasswordToken(user.getCpf(), 1800000L); // 30 minutos
				
				//E-mail
				MimeMessage mail = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper( mail );
	            helper.setTo(email);
	            helper.setSubject( "DreamFit - Recuperação de senha" );
	            helper.setText(String.format("<h>Use este link para recuperar sua senha: <a href=%s>Clique aqui</a></h>", "http://localhost:8080/users/recovery-token/" + token), true);
	            mailSender.send(mail);	
	            userRepo.setResetToken(email, token);
				return new StatusMessage(HttpStatus.ACCEPTED.value(), "E-mail enviado com sucesso");
            } catch (Exception e) {
            	e.printStackTrace();        
                throw new MessageException("Erro ao enviar e-mail, tente novamente", HttpStatus.BAD_REQUEST);
            }
			
		} else {			
			throw new MessageException("Email não cadastrado no sistema", HttpStatus.BAD_REQUEST);
		}
            

	}
	
	public StatusMessage resetPassword(String token, String newPassword, String confirmNewPassword) {
		Claims claims = jwtUtil.getClaims(token);
		String cpf = claims.getSubject();		
		User user = userRepo.findByCpf(cpf);
		
		if (user == null) {
			throw new MessageException("Token expirado ou inválido", HttpStatus.BAD_REQUEST);
		}
		
		String tokenUsuario = user.getTokenReset();
		Date expiration = claims.getExpiration();
		
		System.out.println(tokenUsuario);
		System.out.println(token);
		
		if (tokenUsuario == null || !tokenUsuario.equals(token) || expiration == null || expiration.before(new Date(System.currentTimeMillis()))) {
			throw new MessageException("Token expirado ou inválido", HttpStatus.BAD_REQUEST);
		}
		
		if (newPassword.equals(confirmNewPassword)) {
			String encodedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());				
			user.setPassword(encodedPassword);
			user.setTokenReset(null);
			userRepo.save(user);				
			return new StatusMessage(HttpStatus.OK.value(), "Senha alterada!");
		} else {
			return new StatusMessage(HttpStatus.BAD_REQUEST.value(), "Senhas não coincidem");
		}

	}

}
