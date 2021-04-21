package com.dreamfitbackend.domain.exercise.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.dreamfitbackend.domain.exercise.enums.TrainingType;

import io.swagger.annotations.ApiModelProperty;

public class ExerciseInputRegister {
	
	@NotBlank
	@ApiModelProperty(position = 1, example = "Peito", allowableValues = "Peito, Costas, Perna")
	private String type;
	
	@Valid
	@NotEmpty
	@ApiModelProperty(position = 2)
	List<ExerciseInputRegisterSolo> exercises;
	
	public TrainingType getType() {
		return TrainingType.toEnum(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ExerciseInputRegisterSolo> getExercises() {
		return exercises;
	}

	public void setExercises(List<ExerciseInputRegisterSolo> exercises) {
		this.exercises = exercises;
	}
	
}
