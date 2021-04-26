package com.dreamfitbackend.domain.user_rewards.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

public class UserRewardsInputDeliver {
	
	@NotNull
	private Long reward_id;
	
	@NotBlank
	private String cpf;

	public Long getReward_id() {
		return reward_id;
	}

	public void setReward_id(Long reward_id) {
		this.reward_id = reward_id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
