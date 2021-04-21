package com.dreamfitbackend.configs.generalDtos;

import io.swagger.annotations.ApiModelProperty;

public class StatusMessage {
	
	@ApiModelProperty(position = 1)
	private Integer status;
	
	@ApiModelProperty(position = 2)
	private String message;

	public StatusMessage(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

}
