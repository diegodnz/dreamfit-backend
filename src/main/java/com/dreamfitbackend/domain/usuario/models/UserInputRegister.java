package com.dreamfitbackend.domain.usuario.models;

import java.sql.Date;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.dreamfitbackend.configs.validations.ValidPassword;
import com.dreamfitbackend.domain.usuario.enums.Role;

import io.swagger.annotations.ApiModelProperty;

public class UserInputRegister {
	
	@NotBlank
	@CPF
	@ApiModelProperty(position = 1)
	private String cpf;
	
	@NotBlank
	@Email
	@Size(max = 255)
	@ApiModelProperty(position = 2)
	private String email;
	
	@NotBlank
	@Size(max = 255)
	@ApiModelProperty(position = 3)
	private String name;
	
	@NotNull
	@ApiModelProperty(position = 4, example = "1999-10-14")
	private LocalDate birthDate;
	
	@NotBlank
	@Size(max = 255)
	@ApiModelProperty(position = 5)
	private String gender;
	
	@ValidPassword
	@ApiModelProperty(position = 6)
	private String password;
	
	@NotNull
	@ApiModelProperty(position = 7, example = "Aluno", allowableValues = "Professor, Aluno")
    private String role_user;
	
	@NotBlank
	@Size(max = 255)
	@ApiModelProperty(position = 8)
	private String phone;
	
	@Size(max = 255)
	@ApiModelProperty(position = 9)
	private String profilePicture;
	
	@ApiModelProperty(position = 10)
	private Float weight;
	
	@ApiModelProperty(position = 11)
	private Float armMeasurement;
	
	@ApiModelProperty(position = 12)
	private Float legMeasurement;
	
	@ApiModelProperty(position = 13)
	private Float hipMeasurement;
	
	@ApiModelProperty(position = 14)
	private Float bellyMeasurement;

	public UserInputRegister() {}

	public String getCpf() {		
		return cpf.trim();
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email.trim();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate bithDate) {
		this.birthDate = bithDate;
	}

	public String getGender() {
		return gender.trim();
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password.trim();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole_user() {
		return Role.toEnum(role_user);
		
	}

	public void setRole_user(String role_user) {
		this.role_user = role_user;
	}

	public String getPhone() {
		return phone.trim();
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePicture() {
		return profilePicture.trim();
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

}
