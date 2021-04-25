package com.dreamfitbackend.domain.gymclass.models;

import java.time.LocalTime;

public class ClassOutputResume {
	
	private String type;
	
	private LocalTime startDate;
	
	private LocalTime endDate;

	public ClassOutputResume(String type, LocalTime startDate, LocalTime endDate) {
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalTime startDate) {
		this.startDate = startDate;
	}

	public LocalTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalTime endDate) {
		this.endDate = endDate;
	}
	
}
