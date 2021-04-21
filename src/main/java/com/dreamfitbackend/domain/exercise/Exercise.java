package com.dreamfitbackend.domain.exercise;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dreamfitbackend.domain.exercise.enums.TrainingType;
import com.dreamfitbackend.domain.usuario.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "exercises")
public class Exercise implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	private User user;
	
	@NotNull
	private Integer type;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String reps;
	
	public Exercise() {}
	
	public Exercise(User user, TrainingType type, String name, String reps) {
		this.user = user;
		this.type = type.getCod();
		this.name = name;
		this.reps = reps;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return TrainingType.toEnum(type).getDescricao();
	}

	public void setType(TrainingType type) {
		this.type = type.getCod();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReps() {
		return reps;
	}

	public void setReps(String reps) {
		this.reps = reps;
	}

}
