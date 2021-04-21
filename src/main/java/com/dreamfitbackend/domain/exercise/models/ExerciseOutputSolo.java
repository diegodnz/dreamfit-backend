package com.dreamfitbackend.domain.exercise.models;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ExerciseOutputSolo {

	@ApiModelProperty(position = 1)
	private String type;
	
	@ApiModelProperty(position = 2)
	private String name;
	
	@ApiModelProperty(position = 3)
	private String reps;	

	public ExerciseOutputSolo() {}
	
	public ExerciseOutputSolo(String type, String name, String reps) {
		this.type = type;
		this.name = name;
		this.reps = reps;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReps() {
		return reps;
	}

	public void setReps(String reps) {
		this.reps = reps;
	}	

}
