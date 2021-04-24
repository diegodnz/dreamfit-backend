package com.dreamfitbackend.domain.gymclass;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.usuario.User;

@Entity
@Table(name = "classes")
public class Class implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(
	  name = "teachers_classes", 
	  joinColumns = @JoinColumn(name = "class_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> teachers;
	
	@ManyToMany
	@JoinTable(
	  name = "students_classes", 
	  joinColumns = @JoinColumn(name = "class_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> students;
	
	private String className;
	
	private String type;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private Integer filledVacancies;
	
	private Integer totalVacancies;
	
	public Class() {}

	public Class(String className, String type, LocalDateTime startDate, LocalDateTime endDate, Integer filledVacancies, Integer totalVacancies) {
		this.className = className;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.filledVacancies = filledVacancies;
		this.totalVacancies = totalVacancies;
	}
	
	public void addStudent(User user) {			
		this.filledVacancies += 1;			
	}
	
	public void removeStudent(User user) {		
		this.filledVacancies -= 1;		
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

	public Integer getFilledVacancies() {
		return filledVacancies;
	}

	public void setFilledVacancies(Integer filledVacancies) {
		this.filledVacancies = filledVacancies;
	}

	public Integer getTotalVacancies() {
		return totalVacancies;
	}

	public void setTotalVacancies(Integer totalVacancies) {
		this.totalVacancies = totalVacancies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
