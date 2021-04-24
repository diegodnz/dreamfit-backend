package com.dreamfitbackend.domain.user_measures.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserMeasuresInputUpdate {
	
	@NotBlank
	private String uuid;
	
	@NotNull
	private Float weight;
	
	@NotNull
	private Float arm_measurement;
	
	@NotNull
	private Float leg_measurement;
	
	@NotNull
	private Float hip_measurement;
	
	@NotNull
	private Float belly_measurement;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getArm_measurement() {
		return arm_measurement;
	}

	public void setArm_measurement(Float arm_measurement) {
		this.arm_measurement = arm_measurement;
	}

	public Float getLeg_measurement() {
		return leg_measurement;
	}

	public void setLeg_measurement(Float leg_measurement) {
		this.leg_measurement = leg_measurement;
	}

	public Float getHip_measurement() {
		return hip_measurement;
	}

	public void setHip_measurement(Float hip_measurement) {
		this.hip_measurement = hip_measurement;
	}

	public Float getBelly_measurement() {
		return belly_measurement;
	}

	public void setBelly_measurement(Float belly_measurement) {
		this.belly_measurement = belly_measurement;
	}

}
