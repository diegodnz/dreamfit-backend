package com.dreamfitbackend.domain.rewards.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dreamfitbackend.configs.Mapper;
import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.generalDtos.StatusMessage;
import com.dreamfitbackend.domain.rewards.Reward;
import com.dreamfitbackend.domain.rewards.RewardRepository;
import com.dreamfitbackend.domain.rewards.models.RewardInputRegister;
import com.dreamfitbackend.domain.rewards.models.RewardOutputList;
import com.dreamfitbackend.domain.rewards.models.RewardOutputListElement;
import com.dreamfitbackend.domain.user_rewards.UserRewards;
import com.dreamfitbackend.domain.user_rewards.UserRewardsRepository;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;

@Service
public class RewardServices {
	
	@Autowired
	private RewardRepository rewardRepo;
	
	@Autowired
	private UserRewardsRepository userRewardsRepo;
	
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
	
	public StatusMessage redeemReward(User user, Long id) {
		Reward reward = rewardRepo.getById(id);
		if (reward == null) {
			throw new MessageException("Produto não encontrado", HttpStatus.BAD_REQUEST);
		}
		
		if (user.getFitcoins() < reward.getPrice()) {
			throw new MessageException("Saldo de fitcoins insuficiente :(", HttpStatus.BAD_REQUEST);
		}
		
		if (reward.getQuantity() < 1) {
			throw new MessageException("Produto fora de estoque", HttpStatus.BAD_REQUEST);
		}		
		
		user.setFitcoins(user.getFitcoins() - reward.getPrice());
		reward.setQuantity(reward.getQuantity() - 1);
		
		UserRewards userReward = new UserRewards(user, reward, false);
		userRewardsRepo.save(userReward);
		
		return new StatusMessage(HttpStatus.OK.value(), "Produto comprado com sucesso :). Informe seu cpf na academia para recebê-lo");
	}
}
