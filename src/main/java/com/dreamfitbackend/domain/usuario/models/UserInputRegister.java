package com.dreamfitbackend.domain.usuario.models;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.dreamfitbackend.configs.validations.ValidPassword;
import com.dreamfitbackend.domain.usuario.enums.Role;

public class UserInputRegister {
	
	@NotBlank
	@CPF
	private String cpf;
	
	@NotBlank
	@Email
	@Size(max = 255)
	private String email;
	
	@NotBlank
	@Size(max = 255)	
	private String name;
	
	@NotNull
	private Date birthDate;
	
	@NotBlank
	@Size(max = 255)
	private String gender;
	
	@ValidPassword
	private String password;
	
	@NotNull
    private String role_user;
	
	@NotBlank
	@Size(max = 255)
	private String phone;
	
	@NotBlank
	@Size(max = 255)
	private String profilePicture;
	
	private Float weight;
	
	private Float armMeasurement;
	
	private Float legMeasurement;
	
	private Float hipMeasurement;
	
	private Float bellyMeasurement;

	public UserInputRegister() {}

	public String getCpf() {		
		return cpf.strip();
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email.strip();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name.strip();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date bithDate) {
		this.birthDate = bithDate;
	}

	public String getGender() {
		return gender.strip();
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password.strip();
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
		return phone.strip();
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePicture() {
		return profilePicture.strip();
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
