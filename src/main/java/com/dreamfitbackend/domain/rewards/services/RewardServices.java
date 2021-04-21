package com.dreamfitbackend.domain.rewards.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.domain.rewards.Reward;
import com.dreamfitbackend.domain.rewards.RewardRepository;
import com.dreamfitbackend.domain.rewards.models.RewardInputRegister;

@Service
public class RewardServices {
	
	@Autowired
	private RewardRepository rewardRepo;
	
	public void register(RewardInputRegister rewardInputRegister) {
		Reward reward = new Reward();
		Mapper.copyPropertiesAllowNull(rewardInputRegister, reward);
		rewardRepo.save(reward);
	}
}
