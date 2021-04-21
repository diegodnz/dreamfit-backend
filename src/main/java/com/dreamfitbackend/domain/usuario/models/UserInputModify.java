package com.dreamfitbackend.domain.usuario.models;

import java.sql.Date;

public class UserInputModify {
	
	private String email;

	private String name;
	
	private Date birthDate;
	
	private String gender;
	
    private Integer role_user;
	
	private String phone;
	
	private String profilePicture;
	
	private Integer fitcoins;
	
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getRole_user() {
		return role_user;
	}

	public void setRole_user(Integer role_user) {
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

	public Integer getFitcoins() {
		return fitcoins;
	}

	public void setFitcoins(Integer fitcoins) {
		this.fitcoins = fitcoins;
	}
	
}
