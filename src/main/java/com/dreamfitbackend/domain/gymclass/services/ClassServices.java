package com.dreamfitbackend.domain.gymclass.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.domain.gymclass.Class;
import com.dreamfitbackend.domain.gymclass.ClassRepository;
import com.dreamfitbackend.domain.gymclass.models.ClassInputRegisterMany;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

@Service
public class ClassServices {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	public StatusMessage registerClasses(ClassInputRegisterMany classesInput) {
		 return null; // TODO implementar
	}
	
	public StatusMessage makeAppointment(Long id, User user) {
		Class gymClass = classRepo.getById(id);
		
		if (gymClass == null) {
			throw new MessageException("Aula não encontrada", HttpStatus.BAD_REQUEST);
		}
		
		LocalDateTime nowPlusTenMin = LocalDateTime.of(LocalDate.now(), LocalTime.now()).plus(10, ChronoUnit.MINUTES);
		if (gymClass.getDate().isAfter(nowPlusTenMin)) {
			throw new MessageException("Só é possível agendar aulas com até 10 minutos a partir do horário de início", HttpStatus.BAD_REQUEST);
		}
		
		if (gymClass.getTotalVacancies() >= gymClass.getFilledVacancies()) {
			throw new MessageException("Não há vagas para esta aula", HttpStatus.BAD_REQUEST);
		}
		
		Integer schedule = classRepo.getUserClasses(user.getId(), new Date(System.currentTimeMillis())); // TODO Tá errado, tem q checar o horário da aula
		if (schedule > 1) {
			throw new MessageException("Você só pode agendar 2 aulas no mesmo dia", HttpStatus.BAD_REQUEST);
		}
		
		if (classRepo.verifyRelation(user.getId(), gymClass.getId()) != null) {
			throw new MessageException("Você já fez o agendamento para esta aula", HttpStatus.BAD_REQUEST);
		}
		
		gymClass.addStudent(user);
		user.addClassStudent(gymClass);
		classRepo.save(gymClass);	
		userRepo.save(user);
		return new StatusMessage(200, "Agendamento feito com sucesso para " + gymClass.getClassName() + " às " + gymClass.getDate().getHour() + ":" + gymClass.getDate().getMinute());
	}

}
