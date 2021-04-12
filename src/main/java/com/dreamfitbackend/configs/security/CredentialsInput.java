package com.dreamfitbackend.configs.security;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.dreamfitbackend.configs.validations.ValidPassword;

public class CredentialsInput {
	
	@NotBlank
	private String cpf;
	
	private String password;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
