package com.dreamfitbackend.domain.usuario.models;

import java.sql.Date;
import java.time.LocalDate;

import com.dreamfitbackend.domain.usuario.enums.MeasureChange;
import com.dreamfitbackend.domain.usuario.enums.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class UserOutputPublic {
	
	@ApiModelProperty(position = 1)
	private String name;
	
	@ApiModelProperty(position = 2, example = "1999-10-14")
	private LocalDate birthDate;
	
	@ApiModelProperty(position = 3)
	private String gender;
	
	@ApiModelProperty(position = 4, example = "Aluno", allowableValues = "Academia, Professor, Aluno")
    private Role role_user;
	
	@ApiModelProperty(position = 5)
	private String profilePicture;
	
	@ApiModelProperty(position = 6)
	private Float weight;
	
	@ApiModelProperty(position = 7, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange weightChange;
	
	@ApiModelProperty(position = 8)
	private Float armMeasurement;
	
	@ApiModelProperty(position = 9, example = "Diminuiu", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange armMeasurementChange;
	
	@ApiModelProperty(position = 10)
	private Float legMeasurement;

	@ApiModelProperty(position = 11, example = "Manteve", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange legMeasurementChange;
	
	@ApiModelProperty(position = 12)
	private Float hipMeasurement;

	@ApiModelProperty(position = 13, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange hipMeasurementChange;
	
	@ApiModelProperty(position = 14)
	private Float bellyMeasurement;

	@ApiModelProperty(position = 15, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange bellyMeasurementChange;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole_user() {
		return role_user.getDescricao();
	}

	public void setRole_user(Role role_user) {
		this.role_user = role_user;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getArmMeasurement() {
		return armMeasurement;
	}

	public void setArmMeasurement(Float armMeasurement) {
		this.armMeasurement = armMeasurement;
	}

	public Float getLegMeasurement() {
		return legMeasurement;
	}

	public void setLegMeasurement(Float legMeasurement) {
		this.legMeasurement = legMeasurement;
	}

	public Float getHipMeasurement() {
		return hipMeasurement;
	}

	public void setHipMeasurement(Float hipMeasurement) {
		this.hipMeasurement = hipMeasurement;
	}

	public Float getBellyMeasurement() {
		return bellyMeasurement;
	}

	public void setBellyMeasurement(Float bellyMeasurement) {
		this.bellyMeasurement = bellyMeasurement;
	}

	public String getWeightChange() {
		return weightChange.getDescricao();
	}

	public void setWeightChange(MeasureChange weightChange) {
		this.weightChange = weightChange;
	}

	public String getArmMeasurementChange() {
		return armMeasurementChange.getDescricao();
	}

	public void setArmMeasurementChange(MeasureChange armMeasurementChange) {
		this.armMeasurementChange = armMeasurementChange;
	}

	public String getLegMeasurementChange() {
		return legMeasurementChange.getDescricao();
	}

	public void setLegMeasurementChange(MeasureChange legMeasurementChange) {
		this.legMeasurementChange = legMeasurementChange;
	}

	public String getHipMeasurementChange() {
		return hipMeasurementChange.getDescricao();
	}

	public void setHipMeasurementChange(MeasureChange hipMeasurementChange) {
		this.hipMeasurementChange = hipMeasurementChange;
	}

	public String getBellyMeasurementChange() {
		return bellyMeasurementChange.getDescricao();
	}

	public void setBellyMeasurementChange(MeasureChange bellyMeasurementChange) {
		this.bellyMeasurementChange = bellyMeasurementChange;
	}

}
