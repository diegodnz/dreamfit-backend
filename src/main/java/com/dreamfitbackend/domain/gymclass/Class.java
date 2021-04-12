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
	  joinColumns = @JoinColumn(name = "teacher_id"), 
	  inverseJoinColumns = @JoinColumn(name = "class_id"))
	private Set<User> teachers;
	
	@ManyToMany
	@JoinTable(
	  name = "students_classes", 
	  joinColumns = @JoinColumn(name = "student_id"), 
	  inverseJoinColumns = @JoinColumn(name = "class_id"))
	private Set<User> students;
	
	private String className;
	
	private LocalDateTime date;
	
	private Integer filledVacancies;
	
	private Integer totalVacancies;

	public Class(String className, LocalDateTime date, Integer filledVacancies, Integer totalVacancies) {
		this.className = className;
		this.date = date;
		this.filledVacancies = filledVacancies;
		this.totalVacancies = totalVacancies;
	}
	
	public void addStudent(User user) {
		this.students.add(user);
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
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
