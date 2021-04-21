package com.dreamfitbackend.domain.exercise.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.dreamfitbackend.domain.exercise.enums.TrainingType;

import io.swagger.annotations.ApiModelProperty;

public class ExerciseInputRegisterSolo {
	
	@NotBlank
	@ApiModelProperty(position = 2, example = "Supino reto com barra")
	@Size(min = 1, max = 64)
	private String name;
	
	@NotBlank
	@ApiModelProperty(position = 3, example = "3x10 40'")
	@Size(min = 1, max = 64)
	private String reps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getReps() {
		return reps;
	}

	public void setReps(String reps) {
		this.reps = reps.trim();
	}

}
