package com.dreamfitbackend.domain.rewards.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.domain.rewards.Reward;
import com.dreamfitbackend.domain.rewards.RewardRepository;
import com.dreamfitbackend.domain.rewards.models.RewardInputRegister;
import com.dreamfitbackend.domain.rewards.models.RewardOutputList;
import com.dreamfitbackend.domain.rewards.models.RewardOutputListElement;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

@Service
public class RewardServices {
	
	@Autowired
	private RewardRepository rewardRepo;
	
	public void register(RewardInputRegister rewardInputRegister) {
		Reward reward = new Reward();
		Mapper.copyPropertiesAllowNull(rewardInputRegister, reward);
		rewardRepo.save(reward);
	}
	
	public RewardOutputList listAll(User user) {
		RewardOutputList rewardsList = new RewardOutputList(user.getFitcoins());
		List<Reward> rewards = rewardRepo.getRewards();
		for (Reward reward : rewards) {
			rewardsList.addItem(new RewardOutputListElement(reward.getId(), reward.getPicture(), reward.getTitle(), reward.getDescription(), reward.getQuantity(), reward.getPrice()));
		}
		return rewardsList;		
	}
}
