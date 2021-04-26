package com.dreamfitbackend.domain.rewards.models;

import java.io.Serializable;

import javax.persistence.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class RewardOutputRedeem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Long id;
	
	@ApiModelProperty(position = 1)
	private Integer quantity;
	
	@ApiModelProperty(position = 2)
	private RewardOutputListElement reward;
	
	public RewardOutputRedeem() {}

	public RewardOutputRedeem(RewardOutputListElement reward, Integer quantity) {
		this.reward = reward;
		this.quantity = quantity;
	}

	public RewardOutputRedeem(Long id, Integer quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public RewardOutputListElement getReward() {
		return reward;
	}

	public void setReward(RewardOutputListElement reward) {
		this.reward = reward;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
