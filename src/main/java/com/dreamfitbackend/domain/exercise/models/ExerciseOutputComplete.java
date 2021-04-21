package com.dreamfitbackend.domain.exercise.models;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ExerciseOutputComplete {

	@ApiModelProperty(position = 1)
	private List<ExerciseOutputSolo> chest;
	
	@ApiModelProperty(position = 2)
	private List<ExerciseOutputSolo> back;
	
	@ApiModelProperty(position = 3)
	private List<ExerciseOutputSolo> leg;

	
	
	public ExerciseOutputComplete() {
		this.chest = new ArrayList<>();
		this.back = new ArrayList<>();
		this.leg = new ArrayList<>();
	}

	public List<ExerciseOutputSolo> getChest() {
		return chest;
	}

	public void addChest(ExerciseOutputSolo chestExercise) {
		this.chest.add(chestExercise);
	}
	
	public void addAllChest(List<ExerciseOutputSolo> chestExercises) {
		this.chest.addAll(chestExercises);
	}

	public List<ExerciseOutputSolo> getBack() {
		return back;
	}

	public void addBack(ExerciseOutputSolo backExercise) {
		this.back.add(backExercise);
	}
	
	public void addAllBack(List<ExerciseOutputSolo> backExercises) {
		this.back.addAll(backExercises);		
	}

	public List<ExerciseOutputSolo> getLeg() {
		return leg;
	}

	public void addLeg(ExerciseOutputSolo legExercise) {
		this.leg.add(legExercise);
	}
	
	public void addAllLeg(List<ExerciseOutputSolo> legExercises) {
		this.leg.addAll(legExercises);
	}
	
}
