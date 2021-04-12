package com.dreamfitbackend.domain.usuario;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dreamfitbackend.domain.usuario.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "usuarios")
public class User implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String uuid;
	
	@Column(unique = true)
	private String cpf;
	
	@Column(unique = true)	
	private String email;
	
	private String name;
	
	private Date birthDate;
	
	private String gender;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
    private Integer role_user;
	
	private String phone;
	
	private String profilePicture;
	
	private Float weight;
	
	private Float armMeasurement;
	
	private Float legMeasurement;
	
	private Float hipMeasurement;
	
	private Float bellyMeasurement;

	public User(String cpf, String email, String name, Date birthDate, String gender, String password,
			Integer role_user, String phone, String profilePicture, Float weight, Float armMeasurement,
			Float legMeasurement, Float hipMeasurement, Float bellyMeasurement) {
		this.uuid = UUID.randomUUID().toString();
		this.cpf = cpf;
		this.email = email;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.password = password;
		this.role_user = role_user;
		this.phone = phone;
		this.profilePicture = profilePicture;
		this.weight = weight;
		this.armMeasurement = armMeasurement;
		this.legMeasurement = legMeasurement;
		this.hipMeasurement = hipMeasurement;
		this.bellyMeasurement = bellyMeasurement;
	}

	public User() {
		this.uuid = UUID.randomUUID().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole_user() {
		return Role.toEnum(role_user);
	}

	public void setRole_user(Role role_user) {
		this.role_user = role_user.getCod();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getArmMeasurement() {
		return armMeasurement;
	}

	public void setArmMeasurement(Float armMeasurement) {
		this.armMeasurement = armMeasurement;
	}

	public Float getLegMeasurement() {
		return legMeasurement;
	}

	public void setLegMeasurement(Float legMeasurement) {
		this.legMeasurement = legMeasurement;
	}

	public Float getHipMeasurement() {
		return hipMeasurement;
	}

	public void setHipMeasurement(Float hipMeasurement) {
		this.hipMeasurement = hipMeasurement;
	}

	public Float getBellyMeasurement() {
		return bellyMeasurement;
	}

	public void setBellyMeasurement(Float bellyMeasurement) {
		this.bellyMeasurement = bellyMeasurement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		User other = (User) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
