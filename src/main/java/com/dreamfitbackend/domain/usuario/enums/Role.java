package com.dreamfitbackend.domain.usuario.enums;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.MessageException;

public enum Role {
	ADMIN(20, "Academia"),
	TEACHER(40, "Professor"),
	STUDENT(60, "Aluno");

	private int cod;
	private String descricao;

	private Role(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao () {
		return descricao;
	}

	public static Role toEnum(Integer cod) {

		if (cod == null) {
			throw new MessageException("Id do perfil inválido: " + cod, HttpStatus.BAD_REQUEST);
		}
		for (Role x : Role.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new MessageException("Id do perfil inválido: " + cod, HttpStatus.BAD_REQUEST);
	}
	
	public static Role toEnum(String role) {
		if (role == null) {
			throw new MessageException("Perfil de usuário inválido: " + role, HttpStatus.BAD_REQUEST);
		}
		for (Role a : Role.values()) {
			if (role.equals(a.getDescricao())) {
				return a;
			}
		}
		
		throw new MessageException("Perfil de usuário inválido: " + role, HttpStatus.BAD_REQUEST);
	}

}
