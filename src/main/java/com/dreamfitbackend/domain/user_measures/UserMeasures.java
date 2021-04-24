package com.dreamfitbackend.domain.user_measures;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dreamfitbackend.domain.usuario.User;

@Entity
@Table(name = "user_measures")
public class UserMeasures implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@NotNull
	private LocalDateTime date;
	
	private Float weight;
	
	private Float arm_measurement;
	
	private Float leg_measurement;
	
	private Float hip_measurement;
	
	private Float belly_measurement;
	
	public UserMeasures() {}

	public UserMeasures(User user, LocalDateTime date, Float weight, Float arm_measurement,
			Float leg_measurement, Float hip_measurement, Float belly_measurement) {
		this.user = user;
		this.date = date;
		this.weight = weight;
		this.arm_measurement = arm_measurement;
		this.leg_measurement = leg_measurement;
		this.hip_measurement = hip_measurement;
		this.belly_measurement = belly_measurement;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getArm_measurement() {
		return arm_measurement;
	}

	public void setArm_measurement(Float arm_measurement) {
		this.arm_measurement = arm_measurement;
	}

	public Float getLeg_measurement() {
		return leg_measurement;
	}

	public void setLeg_measurement(Float leg_measurement) {
		this.leg_measurement = leg_measurement;
	}

	public Float getHip_measurement() {
		return hip_measurement;
	}

	public void setHip_measurement(Float hip_measurement) {
		this.hip_measurement = hip_measurement;
	}

	public Float getBelly_measurement() {
		return belly_measurement;
	}

	public void setBelly_measurement(Float belly_measurement) {
		this.belly_measurement = belly_measurement;
	}
	
}
