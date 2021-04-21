package com.dreamfitbackend.domain.exercise.enums;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;

public enum TrainingType {
	CHEST(1, "Peito"),
	BACK(2, "Costas"),
	LEG(3, "Perna");

	private int cod;
	private String descricao;

	private TrainingType(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}

	public static TrainingType toEnum(Integer cod) {

		if (cod == null) {
			throw new MessageException("Id do tipo de treino inválido: " + cod, HttpStatus.BAD_REQUEST);
		}
		for (TrainingType x : TrainingType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new MessageException("Id do tipo de treino inválido: " + cod, HttpStatus.BAD_REQUEST);
	}
	
	public static TrainingType toEnum(String trainingType) {
		if (trainingType == null) {
			throw new MessageException("Tipo de treino inválido: " + trainingType, HttpStatus.BAD_REQUEST);
		}
		for (TrainingType t : TrainingType.values()) {
			if (trainingType.equals(t.getDescricao())) {
				return t;
			}
		}
		
		throw new MessageException("Tipo de treino inválido: " + trainingType, HttpStatus.BAD_REQUEST);
	}

}