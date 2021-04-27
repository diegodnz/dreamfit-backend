package com.dreamfitbackend.domain.usuario.models;

import com.dreamfitbackend.configs.validations.ValidPassword;

public class UserInputUpdatePassword {
	
	private String currentPassword;
	
	@ValidPassword
	private String newPassword;
	
	@ValidPassword
	private String confirmNewPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
