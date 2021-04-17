package com.dreamfitbackend.domain.usuario.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailRecovery {
	
	@NotBlank
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
