package com.dreamfitbackend.domain.gymclass.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ClassOutputListElement {
	
	private String className;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private Integer filled_vacancies;
	
	private Integer total_vacancies;
	
	private boolean scheduled;

	public ClassOutputListElement() {}

	public ClassOutputListElement(String className, LocalDateTime startDate, LocalDateTime endDate,
			Integer filled_vacancies, Integer total_vacancies) {
		this.className = className;
		this.startDate = startDate;
		this.endDate = endDate;
		this.filled_vacancies = filled_vacancies;
		this.total_vacancies = total_vacancies;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Integer getFilled_vacancies() {
		return filled_vacancies;
	}

	public void setFilled_vacancies(Integer filled_vacancies) {
		this.filled_vacancies = filled_vacancies;
	}

	public Integer getTotal_vacancies() {
		return total_vacancies;
	}

	public void setTotal_vacancies(Integer total_vacancies) {
		this.total_vacancies = total_vacancies;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}		
	
}
