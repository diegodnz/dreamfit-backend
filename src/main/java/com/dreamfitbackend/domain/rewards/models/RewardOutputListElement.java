package com.dreamfitbackend.domain.rewards.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RewardOutputListElement {

	@ApiModelProperty(position = 1)
	private Long id;
	
	@ApiModelProperty(position = 2)
	private String picture;
	
	@ApiModelProperty(position = 3)
	private String title;
	
	@ApiModelProperty(position = 5)
	private String description;
	
	@ApiModelProperty(position = 6)
	private Integer quantity;
	
	@ApiModelProperty(position = 7)
	private Integer price;
	
	public RewardOutputListElement() {}

	public RewardOutputListElement(Long id, String picture, String title, String description, Integer quantity, Integer price) {
		this.id = id;
		this.picture = picture;
		this.title = title;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

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
