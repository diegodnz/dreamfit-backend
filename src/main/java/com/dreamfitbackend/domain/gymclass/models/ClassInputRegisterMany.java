package com.dreamfitbackend.domain.gymclass.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClassInputRegisterMany {

	@NotBlank
	private String start;
	
	@NotBlank
	private String end;
	
	@NotBlank
	private String className;
	
	@NotNull
	private LocalTime initTime;
	
	@NotNull
	private LocalTime endTime;
	
	@NotNull
	private LocalTime classTime;
	
	private LocalTime intervalBetween;
	
	@NotNull
	private Integer totalVacancies;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public LocalTime getInitTime() {
		return initTime;
	}

	public void setInitTime(LocalTime initTime) {
		this.initTime = initTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalTime getClassTime() {
		return classTime;
	}

	public void setClassTime(LocalTime classTime) {
		this.classTime = classTime;
	}

	public LocalTime getIntervalBetween() {
		return intervalBetween;
	}

	public void setIntervalBetween(LocalTime intervalBetween) {
		this.intervalBetween = intervalBetween;
	}

	public Integer getTotalVacancies() {
		return totalVacancies;
	}

	public void setTotalVacancies(Integer totalVacancies) {
		this.totalVacancies = totalVacancies;
	}
	
}
