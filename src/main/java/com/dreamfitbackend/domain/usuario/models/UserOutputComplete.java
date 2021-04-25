package com.dreamfitbackend.domain.usuario.models;

import java.sql.Date;
import java.time.LocalDate;

import com.dreamfitbackend.domain.gymclass.models.ClassOutputResume;
import com.dreamfitbackend.domain.usuario.enums.MeasureChange;
import com.dreamfitbackend.domain.usuario.enums.Role;

import io.swagger.annotations.ApiModelProperty;

public class UserOutputComplete {
	
	@ApiModelProperty(position = 1)
	private String cpf;
	
	@ApiModelProperty(position = 2)
	private String email;
	
	@ApiModelProperty(position = 3)
	private String name;
	
	@ApiModelProperty(position = 4, example = "1999-10-14")
	private LocalDate birthDate;
	
	@ApiModelProperty(position = 5)
	private String gender;
	
	@ApiModelProperty(position = 6, example = "Aluno", allowableValues = "Academia, Professor, Aluno")
    private Role role_user;
	
	@ApiModelProperty(position = 7)
	private String phone;
	
	@ApiModelProperty(position = 8)
	private String profilePicture;
	
	@ApiModelProperty(position = 9)
	private Float weight;
	
	@ApiModelProperty(position = 10, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange weightChange;
	
	@ApiModelProperty(position = 11)
	private Float armMeasurement;
	
	@ApiModelProperty(position = 12, example = "Diminuiu", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange armMeasurementChange;
	
	@ApiModelProperty(position = 13)
	private Float legMeasurement;

	@ApiModelProperty(position = 14, example = "Manteve", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange legMeasurementChange;
	
	@ApiModelProperty(position = 15)
	private Float hipMeasurement;

	@ApiModelProperty(position = 16, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange hipMeasurementChange;
	
	@ApiModelProperty(position = 17)
	private Float bellyMeasurement;

	@ApiModelProperty(position = 18, example = "Aumentou", allowableValues = "Aumentou, Diminuiu, Manteve")
	private MeasureChange bellyMeasurementChange;
	
	@ApiModelProperty(position = 19)
	private Integer fitcoins;
	
	@ApiModelProperty(position = 20)
	private String plan;
	
	@ApiModelProperty(position = 21)
	private ClassOutputResume nextClass;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Integer getFitcoins() {
		return fitcoins;
	}

	public void setFitcoins(Integer fitcoins) {
		this.fitcoins = fitcoins;
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

	public ClassOutputResume getNextClass() {
		return nextClass;
	}

	public void setNextClass(ClassOutputResume nextClass) {
		this.nextClass = nextClass;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

}
