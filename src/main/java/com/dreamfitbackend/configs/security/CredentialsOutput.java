package com.dreamfitbackend.configs.security;

import io.swagger.annotations.ApiModelProperty;

public class CredentialsOutput {
	
	@ApiModelProperty(position = 1)
	private Integer status;
	
	@ApiModelProperty(position = 2)
	private String token;
	
	@ApiModelProperty(position = 3)
	private String uuid;
	
	public CredentialsOutput(String token, String uuid) {
		this.status = 200;
		this.token = "Bearer " + token;
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
