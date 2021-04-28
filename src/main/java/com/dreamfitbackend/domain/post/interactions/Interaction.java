package com.dreamfitbackend.domain.post.interactions;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;

public enum Interaction {
	
	LIKE(1, "Like"),
	EMOTE(2, "Emote"),
	ARM(3, "Arm");
	
	private String descricao;
	private int cod;
	
	private Interaction(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;		
	}
	
	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}

	public static Interaction toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}
		for (Interaction i : Interaction.values()) {
			if (cod.equals(i.getCod())) {
				return i;
			}
		}

		return null;
	}
	
	public static Interaction toEnum(String interaction) {
		if (interaction == null) {
			throw new MessageException("Interação inválida: " + interaction, HttpStatus.BAD_REQUEST);
		}
		for (Interaction i : Interaction.values()) {
			if (interaction.equals(i.getDescricao())) {
				return i;
			}
		}
		
		throw new MessageException("Interação inválida: " + interaction, HttpStatus.BAD_REQUEST);
	}
	
	
	

}
