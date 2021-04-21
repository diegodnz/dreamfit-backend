package com.dreamfitbackend.domain.usuario.enums;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;

public enum MeasureChange {
	
	AUMENTOU(1, "Aumentou"),
	DIMINUIU(2, "Diminuiu"),
	MANTEVE(3, "Manteve");
	
	private Integer cod;	
	private String descricao;
	
	private MeasureChange(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static MeasureChange toEnum(Integer cod) {
		if (cod == null) {
			throw new MessageException("Id inválido", HttpStatus.BAD_REQUEST);
		}
		
		for (MeasureChange m : MeasureChange.values()) {
			if (cod.equals(m.getCod())) {
				return m;
			}
		}
		throw new MessageException("Id inválido", HttpStatus.BAD_REQUEST);
	}
	
	public static MeasureChange toEnum(String change) {
		if (change == null) {
			throw new MessageException("Mudança de medida inválida", HttpStatus.BAD_REQUEST);
		}
		for (MeasureChange m : MeasureChange.values()) {
			if (change.equals(m.getDescricao())) {
				return m;
			}
		}
		
		throw new MessageException("Mudança de medida inválida", HttpStatus.BAD_REQUEST);
	}

}
