package com.dreamfitbackend.configs.security;

public class CredentialsOutput {
	
	private Integer status;
	
	private String token;
	
	public CredentialsOutput(String token) {
		this.status = 200;
		this.token = "Bearer " + token;
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
	
}
