package com.dreamfitbackend.domain.gymclass.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;

public class ClassOutputListElement {
	
	@ApiModelProperty(position = 1)
	private Long id;
	
	@ApiModelProperty(position = 2, example = "Treino de Musculação")
	private String className;
	
	@ApiModelProperty(position = 3, example = "Musculação")
	private String type;;

	@ApiModelProperty(position = 4)
	private LocalDateTime startDate;
	
	@ApiModelProperty(position = 5)
	private LocalDateTime endDate;
	
	@ApiModelProperty(position = 6)
	private Integer filled_vacancies;
	
	@ApiModelProperty(position = 7)
	private Integer total_vacancies;
	
	@ApiModelProperty(position = 8)
	private boolean scheduled;

	public ClassOutputListElement() {}

	public ClassOutputListElement(Long id, String className, String type,LocalDateTime startDate, LocalDateTime endDate,
			Integer filled_vacancies, Integer total_vacancies) {
		this.id = id;
		this.className = className;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.filled_vacancies = filled_vacancies;
		this.total_vacancies = total_vacancies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
