package com.dreamfitbackend.domain.exercise.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.exercise.Exercise;
import com.dreamfitbackend.domain.exercise.ExerciseRepository;
import com.dreamfitbackend.domain.exercise.enums.TrainingType;
import com.dreamfitbackend.domain.exercise.models.ExerciseInputRegister;
import com.dreamfitbackend.domain.exercise.models.ExerciseInputRegisterSolo;
import com.dreamfitbackend.domain.exercise.models.ExerciseOutputComplete;
import com.dreamfitbackend.domain.exercise.models.ExerciseOutputSolo;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;

@Service
public class ExerciseServices {
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public void register(ExerciseInputRegister exerciseInputRegister, String uuid) {
		User user = userRepo.findByUuid(uuid);
		
		if (user == null) {
			throw new MessageException("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
//		if (user.getRole_user() != Role.STUDENT) {
//			throw new MessageException("Apenas alunos podem possuir lista de treinos", HttpStatus.BAD_REQUEST);
//		}
		
		TrainingType exercisesType = exerciseInputRegister.getType();
		
		List<Exercise> exercises = new ArrayList<>();
		for (ExerciseInputRegisterSolo exerciseInput : exerciseInputRegister.getExercises()) {
			exercises.add(new Exercise(user, exercisesType, exerciseInput.getName(), exerciseInput.getReps()));
		}

		exerciseRepo.deleteByUserAndType(user.getId(), exercisesType.getCod());
		exerciseRepo.saveAll(exercises);
	}
	
	public ExerciseOutputComplete getExercises(User loggedUser, String uuid) {
		User user = userRepo.findByUuid(uuid);
		
		if (user == null) {
			throw new MessageException("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		if (loggedUser.getRole_user() == Role.STUDENT && user != loggedUser) {
			throw new MessageException("Sem permissão", HttpStatus.UNAUTHORIZED);
		}
		
		ExerciseOutputComplete exercisesOutput = new ExerciseOutputComplete();
		// Chest
		List<Exercise> chestExercises = exerciseRepo.getByUserAndType(user.getId(), TrainingType.CHEST.getCod());
		List<ExerciseOutputSolo> chestExercisesOutput = new ArrayList<>();
		for (Exercise e : chestExercises) {
			chestExercisesOutput.add(new ExerciseOutputSolo(e.getType(), e.getName(), e.getReps()));
		}
		exercisesOutput.addAllChest(chestExercisesOutput);
		
		// Back
		List<Exercise> backExercises = exerciseRepo.getByUserAndType(user.getId(), TrainingType.BACK.getCod());
		List<ExerciseOutputSolo> backExercisesOutput = new ArrayList<>();
		for (Exercise e : backExercises) {
			backExercisesOutput.add(new ExerciseOutputSolo(e.getType(), e.getName(), e.getReps()));
		}
		exercisesOutput.addAllBack(backExercisesOutput);
		
		// Leg		
		List<Exercise> legExercises = exerciseRepo.getByUserAndType(user.getId(), TrainingType.LEG.getCod());
		List<ExerciseOutputSolo> legExercisesOutput = new ArrayList<>();
		for (Exercise e : legExercises) {
			legExercisesOutput.add(new ExerciseOutputSolo(e.getType(), e.getName(), e.getReps()));
		}
		exercisesOutput.addAllLeg(legExercisesOutput);
		
		return exercisesOutput;
	}

}
