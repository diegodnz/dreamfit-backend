package com.dreamfitbackend.domain.usuario.services;

import java.time.LocalDateTime;
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
import com.dreamfitbackend.domain.gymclass.Class;
import com.dreamfitbackend.domain.gymclass.ClassRepository;
import com.dreamfitbackend.domain.gymclass.models.ClassOutputResume;
import com.dreamfitbackend.domain.user_measures.UserMeasures;
import com.dreamfitbackend.domain.user_measures.UserMeasuresRepository;
import com.dreamfitbackend.domain.user_measures.models.UserMeasuresInputUpdate;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.MeasureChange;
import com.dreamfitbackend.domain.usuario.enums.Role;
import com.dreamfitbackend.domain.usuario.models.UserInputRegister;
import com.dreamfitbackend.domain.usuario.models.UserInputUpdatePassword;
import com.dreamfitbackend.domain.usuario.models.UserOutputComplete;
import com.dreamfitbackend.domain.usuario.models.UserOutputList;
import com.dreamfitbackend.domain.usuario.models.UserOutputPublic;

import io.jsonwebtoken.Claims;

@Service
public class UserGeneralServices {
	
    @Autowired 
    private JavaMailSender mailSender;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserMeasuresRepository userMeasuresRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public List<UserOutputList> listByRole(Role role, String type, String search) {
		List<UserOutputList> listOutput = new ArrayList<>();
		List<User> listUser = new ArrayList<>();		
		if (type == null || search == null) {
			listUser = userRepo.findAllByRole(role.getCod());
		} else {		
			if (type.equals("Nome")) {
				listUser = userRepo.findAllByNameAndRole(search, role.getCod());
			} else if (type.equals("Cpf")) {
				listUser = userRepo.findAllByCpfAndRole(search, role.getCod());
			} else if (type.equals("Email")) {
				listUser = userRepo.findAllByEmailAndRole(search, role.getCod());
			} else {
				throw new MessageException("Tipo de busca inválida (Só são permitidas buscas por Nome, Cpf ou Email)", HttpStatus.BAD_REQUEST);
			}
		}
		for (User user : listUser) {
			listOutput.add(new UserOutputList(user.getUuid(), user.getName(), user.getEmail(), user.getCpf()));
		}
		return listOutput;
	}
	
	public String register(UserInputRegister userInputRegister) {
		List<Field> errorFields = new ArrayList<>();
		
		if (userInputRegister.getWeight() > 999) {
			errorFields.add(new Field("weight", "Peso não pode exceder 999"));
		}
		
		if (userInputRegister.getArmMeasurement() > 999) {
			errorFields.add(new Field("arm_measurement", "Medida não pode exceder 999"));
		}
		
		if (userInputRegister.getLegMeasurement() > 999) {
			errorFields.add(new Field("leg_measurement", "Medida não pode exceder 999"));
		}
		
		if (userInputRegister.getHipMeasurement() > 999) {
			errorFields.add(new Field("hip_measurement", "Medida não pode exceder 999"));
		}
		
		if (userInputRegister.getBellyMeasurement() > 999) {
			errorFields.add(new Field("belly_measurement", "Medida não pode exceder 999"));
		}
		
		User userCpf = userRepo.findByCpf(userInputRegister.getCpf());
		if (userCpf != null) {
			errorFields.add(new Field("cpf", "Cpf já existente"));
		}
		
		User userEmail = userRepo.findByEmail(userInputRegister.getEmail());
		if (userEmail != null) {
			errorFields.add(new Field("email", "E-mail já existente"));
		}
		
		if (userInputRegister.getRole_user() != Role.STUDENT && userInputRegister.getRole_user() != Role.TEACHER) {
			errorFields.add(new Field("role_user", "Só é permitido o cadastro de usuários com perfil 'Aluno' ou 'Professor'"));
		}
		
		if (!errorFields.isEmpty()) {
			throw new FieldsException(HttpStatus.BAD_REQUEST, errorFields);
		}
		
		User newUser = new User();
		Mapper.copyPropertiesIgnoreNull(userInputRegister, newUser);		
		
		newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
		newUser.setFitcoins(0);
		
		UserMeasures userMeasures = new UserMeasures();	
		userMeasures.setUser(newUser);
		userMeasures.setDate(LocalDateTime.now());
		userMeasures.setWeight(userInputRegister.getWeight());
		userMeasures.setArm_measurement(userInputRegister.getArmMeasurement());
		userMeasures.setLeg_measurement(userInputRegister.getLegMeasurement());
		userMeasures.setHip_measurement(userInputRegister.getHipMeasurement());
		userMeasures.setBelly_measurement(userInputRegister.getBellyMeasurement());				
		
		userRepo.save(newUser);
		userMeasuresRepo.save(userMeasures);
		return newUser.getUuid();
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
			return new CredentialsOutput(token, user.getUuid());			
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
	            helper.setText(String.format("<h>Use este link para recuperar sua senha: <a href=%s>Clique aqui</a></h>", "https://dream-fit.vercel.app/senha/?token=" + token), true);
	            mailSender.send(mail);	
	            userRepo.setResetToken(email, token);
				return new StatusMessage(HttpStatus.OK.value(), "E-mail enviado com sucesso");
            } catch (Exception e) {            	       
            	throw new MessageException("Erro ao enviar e-mail, tente novamente", HttpStatus.BAD_REQUEST);
            }
			
		} else {			
			throw new MessageException("Email não cadastrado no sistema", HttpStatus.BAD_REQUEST);
		}
            

	}
	
	public StatusMessage resetPassword(String token, String newPassword, String confirmNewPassword) {
		Claims claims = jwtUtil.getClaims(token);
		if (claims == null) {
			throw new MessageException("Token expirado ou inválido", HttpStatus.BAD_REQUEST);
		}

		String cpf = claims.getSubject();		
		User user = userRepo.findByCpf(cpf);		
		if (user == null) {
			throw new MessageException("Token expirado ou inválido", HttpStatus.BAD_REQUEST);
		}
		
		String tokenUsuario = user.getTokenReset();
		Date expiration = claims.getExpiration();		
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
			throw new MessageException("Senhas não coincidem", HttpStatus.BAD_REQUEST);
		}

	}
	
	private MeasureChange measureChange(Long userId, String measure) {
		List<Float> userMeasures = new ArrayList<>();
		if (measure.matches("Weight")) {
			userMeasures = userMeasuresRepo.getWeightMeasures(userId);
		} else if (measure.matches("Arm")) {
			userMeasures = userMeasuresRepo.getArmMeasures(userId);
		} else if (measure.matches("Leg")) {
			userMeasures = userMeasuresRepo.getLegMeasures(userId);
		} else if (measure.matches("Hip")) {
			userMeasures = userMeasuresRepo.getHipMeasures(userId);
		} else if (measure.matches("Belly")) {
			userMeasures = userMeasuresRepo.getBellyMeasures(userId);
		}
		if (userMeasures.size() < 2 || userMeasures.get(0) == userMeasures.get(1)) {
			return MeasureChange.MANTEVE;
		} else {
			Float newMeasure = userMeasures.get(0);
			Float oldMeasure = userMeasures.get(1);			
			if (newMeasure > oldMeasure) {
				return MeasureChange.AUMENTOU;
			} else if (newMeasure < oldMeasure){
				return MeasureChange.DIMINUIU;
			} else {
				return MeasureChange.MANTEVE;
			}
		}
	}
	
	private Float lastMeasure(Long userId, String measure) {
		if (measure.matches("Weight")) {
			return userMeasuresRepo.getWeight(userId);
		} else if (measure.matches("Arm")) {
			return userMeasuresRepo.getArmMeasurement(userId);
		} else if (measure.matches("Leg")) {
			return userMeasuresRepo.getLegMeasurement(userId);
		} else if (measure.matches("Hip")) {
			return userMeasuresRepo.getHipMeasurement(userId);
		} else if (measure.matches("Belly")) {
			return userMeasuresRepo.getBellyMeasurement(userId);
		} else {
			return null;
		}
	}
	
	public UserOutputPublic publicProfile(String uuid) {
		User user = userRepo.findByUuid(uuid);
		if (user == null) {
			throw new MessageException("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		// TODO Academia tem perfil?
		UserOutputPublic userPublicProfile = new UserOutputPublic();
		Mapper.copyPropertiesIgnoreNull(user, userPublicProfile);
		
		Long userId = user.getId();
		userPublicProfile.setWeightChange(measureChange(userId, "Weight"));
		userPublicProfile.setArmMeasurementChange(measureChange(userId, "Arm"));
		userPublicProfile.setLegMeasurementChange(measureChange(userId, "Leg"));
		userPublicProfile.setHipMeasurementChange(measureChange(userId, "Hip"));
		userPublicProfile.setBellyMeasurementChange(measureChange(userId, "Belly"));
		userPublicProfile.setWeight(lastMeasure(userId, "Weight"));
		userPublicProfile.setArmMeasurement(lastMeasure(userId, "Arm"));
		userPublicProfile.setLegMeasurement(lastMeasure(userId, "Leg"));
		userPublicProfile.setHipMeasurement(lastMeasure(userId, "Hip"));
		userPublicProfile.setBellyMeasurement(lastMeasure(userId, "Belly"));
		
		return userPublicProfile;
	}
	
	public UserOutputComplete profile(String uuid, User loggedUser) {
		User user = userRepo.findByUuid(uuid);
		if (user == null) {
			throw new MessageException("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		if (loggedUser.getRole_user() == Role.STUDENT && loggedUser != user) {			
			throw new MessageException("Não é possivel acessar o perfil privado de outro aluno", HttpStatus.UNAUTHORIZED);			
		}
		
		UserOutputComplete userProfile = new UserOutputComplete();
		Mapper.copyPropertiesIgnoreNull(user, userProfile);
		
		Long userId = user.getId();
		userProfile.setWeightChange(measureChange(userId, "Weight"));
		userProfile.setArmMeasurementChange(measureChange(userId, "Arm"));
		userProfile.setLegMeasurementChange(measureChange(userId, "Leg"));
		userProfile.setHipMeasurementChange(measureChange(userId, "Hip"));
		userProfile.setBellyMeasurementChange(measureChange(userId, "Belly"));
		userProfile.setWeight(lastMeasure(userId, "Weight"));
		userProfile.setArmMeasurement(lastMeasure(userId, "Arm"));
		userProfile.setLegMeasurement(lastMeasure(userId, "Leg"));
		userProfile.setHipMeasurement(lastMeasure(userId, "Hip"));
		userProfile.setBellyMeasurement(lastMeasure(userId, "Belly"));
		
		Class nextClass = classRepo.getNextClass(userId, LocalDateTime.now(), LocalDateTime.now().withHour(0).withMinute(0), LocalDateTime.now().withHour(0).withMinute(0).plusDays(1));
		if (nextClass != null) {
			ClassOutputResume nextClassOutput = new ClassOutputResume(nextClass.getType(), nextClass.getStartDate().toLocalTime(), nextClass.getEndDate().toLocalTime());
			userProfile.setNextClass(nextClassOutput);
		}
		
		return userProfile;
		
	}
	
	public StatusMessage updateMeasures(UserMeasuresInputUpdate userMeasuresInput) {
		User updateUser = userRepo.findByCpf(userMeasuresInput.getCpf());
		if (updateUser == null) {
			throw new MessageException("Cpf inválido", HttpStatus.BAD_REQUEST);
		}
		UserMeasures userMeasures = new UserMeasures(updateUser, LocalDateTime.now(), userMeasuresInput.getWeight(), userMeasuresInput.getArm_measurement(), 
				userMeasuresInput.getLeg_measurement(), userMeasuresInput.getHip_measurement(), userMeasuresInput.getBelly_measurement());
		userMeasuresRepo.save(userMeasures);
		return new StatusMessage(HttpStatus.OK.value(), "Medidas atualizadas com sucesso!");
	}
	
	public StatusMessage updatePassword(User loggedUser, UserInputUpdatePassword passwordInput) {
		boolean matchCurrentPassword = BCrypt.checkpw(passwordInput.getCurrentPassword(), loggedUser.getPassword());
		if (!matchCurrentPassword) {
			throw new MessageException("Senha atual incorreta", HttpStatus.BAD_REQUEST);
		}
		
		if (!passwordInput.getNewPassword().matches(passwordInput.getConfirmNewPassword())) {
			throw new MessageException("Senhas não coincidem", HttpStatus.BAD_REQUEST);
		}
		
		String encodedPassword = BCrypt.hashpw(passwordInput.getNewPassword(), BCrypt.gensalt());
		loggedUser.setPassword(encodedPassword);
		userRepo.save(loggedUser);
		return new StatusMessage(HttpStatus.OK.value(), "Senha alterada!");
	}

}
