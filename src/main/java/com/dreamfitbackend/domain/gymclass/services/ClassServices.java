package com.dreamfitbackend.domain.gymclass.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.domain.gymclass.Class;
import com.dreamfitbackend.domain.gymclass.ClassRepository;
import com.dreamfitbackend.domain.gymclass.models.ClassInputRegisterMany;
import com.dreamfitbackend.domain.gymclass.models.ClassOutputList;
import com.dreamfitbackend.domain.gymclass.models.ClassOutputListElement;
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
	
	public ClassOutputList getClasses(User user) {
		ZonedDateTime nowBr = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
		LocalDateTime startDay = nowBr.toLocalDateTime().withHour(0).withMinute(0);
		LocalDateTime endDay = nowBr.toLocalDateTime().withHour(0).withMinute(0).plusDays(1);
		List<Class> todayClasses = classRepo.getByDate(startDay, endDay);
		ClassOutputList classesOutput = new ClassOutputList(new ArrayList<ClassOutputListElement>());
		for (Class gymClass : todayClasses) {
			ClassOutputListElement classElement = new ClassOutputListElement(gymClass.getId() ,gymClass.getClassName(), gymClass.getType() ,gymClass.getStartDate(), gymClass.getEndDate(), gymClass.getFilledVacancies(), gymClass.getTotalVacancies());
			boolean scheduled = classRepo.verifyRelation(user.getId(), gymClass.getId()) != null;
			classElement.setScheduled(scheduled);
			classesOutput.addClass(classElement);
		}
		return classesOutput;
	}
	
	public StatusMessage makeAppointment(Long id, User user) {
		Class gymClass = classRepo.getById(id);
		
		if (gymClass == null) {
			throw new MessageException("Aula não encontrada", HttpStatus.BAD_REQUEST);
		}
		
		if (classRepo.verifyRelation(user.getId(), gymClass.getId()) != null) {
			throw new MessageException("Você já fez o agendamento para esta aula", HttpStatus.BAD_REQUEST);
		}
		
		LocalDateTime startDay = gymClass.getStartDate().withHour(0).withMinute(0);
		LocalDateTime endDay = gymClass.getStartDate().withHour(0).withMinute(0).plusDays(1);
		Integer schedule = classRepo.getUserClasses(user.getId(), startDay, endDay);
		if (schedule > 1) {
			throw new MessageException("Você só pode agendar 2 aulas no mesmo dia", HttpStatus.BAD_REQUEST);
		}
		
		if (gymClass.getTotalVacancies() <= gymClass.getFilledVacancies()) {
			throw new MessageException("Não há vagas para esta aula", HttpStatus.BAD_REQUEST);
		}
		
		ZonedDateTime nowBr = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
		LocalDateTime nowMinusTenMin = nowBr.toLocalDateTime().minus(10, ChronoUnit.MINUTES);
		if (gymClass.getStartDate().isBefore(nowMinusTenMin)) {
			throw new MessageException("Só é possível agendar aulas com até 10 minutos a partir do horário de início", HttpStatus.BAD_REQUEST);
		}		
		
		gymClass.addStudent(user);
		user.addClassStudent(gymClass);
		classRepo.save(gymClass);
		userRepo.save(user);
		String hour = String.valueOf(gymClass.getStartDate().getHour());
		if (hour.length() == 1) {
			hour = "0" + hour;
		}		
		String minutes = String.valueOf(gymClass.getStartDate().getMinute());
		if (minutes.length() == 1) {
			minutes = "0" + minutes;
		}
		return new StatusMessage(200, "Agendamento feito com sucesso para " + gymClass.getClassName() + " às " + hour + ":" + minutes);
	}
	
	public StatusMessage deleteAppointment(Long id, User user) {
		Class gymClass = classRepo.getById(id);
		
		if (gymClass == null) {
			throw new MessageException("Aula não encontrada", HttpStatus.BAD_REQUEST);
		}
		
		if (classRepo.verifyRelation(user.getId(), gymClass.getId()) == null) {
			throw new MessageException("Você não está agendado para esta aula", HttpStatus.BAD_REQUEST);
		}
		
		ZonedDateTime nowBr = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
		LocalDateTime now = nowBr.toLocalDateTime();
		if (gymClass.getStartDate().isBefore(now)) {
			throw new MessageException("Só é possível cancelar o agendamento antes da aula começar", HttpStatus.BAD_REQUEST);
		}
		
		gymClass.removeStudent(user);
		user.removeClassStudent(gymClass);
		classRepo.save(gymClass);
		userRepo.save(user);
		return new StatusMessage(200, "Agendamento cancelado");		
	}

}
