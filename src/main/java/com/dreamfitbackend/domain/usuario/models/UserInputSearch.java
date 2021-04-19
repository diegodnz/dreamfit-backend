package com.dreamfitbackend.domain.usuario.models;

import io.swagger.annotations.ApiModelProperty;

public class UserInputSearch {
	@ApiModelProperty(allowableValues = "Nome, Cpf, Email")
	private String type;
	
	@ApiModelProperty(example = ("Diego"))
	private String search;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
}
