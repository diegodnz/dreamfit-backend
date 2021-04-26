package com.dreamfitbackend.domain.rewards.models;

import java.util.ArrayList;
import java.util.List;

public class RewardOutputList {
	
	private Integer fitcoins;
	
	private List<RewardOutputListElement> store;
	
	public RewardOutputList(Integer fitcoins) {
		this.fitcoins = fitcoins;
		this.store = new ArrayList<>();
	}
	
	public void addItem(RewardOutputListElement item) {
		this.store.add(item);
	}

	public Integer getFitcoins() {
		return fitcoins;
	}

	public void setFitcoins(Integer fitcoins) {
		this.fitcoins = fitcoins;
	}

	public List<RewardOutputListElement> getStore() {
		return store;
	}

	public void setStore(List<RewardOutputListElement> store) {
		this.store = store;
	}
	
	

}
