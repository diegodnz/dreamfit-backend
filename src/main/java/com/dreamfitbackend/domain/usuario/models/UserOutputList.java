package com.dreamfitbackend.domain.usuario.models;

import io.swagger.annotations.ApiModelProperty;

public class UserOutputList {
	
	@ApiModelProperty(position = 0)
	private String uuid;
	
	@ApiModelProperty(position = 1)
	private String name;
	
	@ApiModelProperty(position = 2)
	private String email;
	
	@ApiModelProperty(position = 3)
	private String cpf;

	public UserOutputList(String uuid, String name, String email, String cpf) {
		this.uuid = uuid;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
