package com.dreamfitbackend.domain.rewards.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RewardInputRegister {
	
	@NotBlank
	@ApiModelProperty(position = 1, example = "Garrafa")
	private String title;
	
	@ApiModelProperty(position = 2, example = "Linda garrafa azul de 500ml")
	private String description;
	
	@NotNull
	@ApiModelProperty(position = 3, example = "10")
	private Integer quantity;
	
	@NotNull
	@ApiModelProperty(position = 4, example = "100")
	private Integer price;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
