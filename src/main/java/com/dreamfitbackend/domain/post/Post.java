package com.dreamfitbackend.domain.post;

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
@Table(name = "posts")
public class Post implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private User user;
	
	private String description;
	
	private String picture;
	
	private Integer likes;
	
	private Integer emotes;
	
	private Integer arms;
	
	private LocalDateTime time;
	
	public Post() {}
	
	public Post(User user, String description) {
		this.user = user;
		this.description = description;
		this.likes = 0;
		this.emotes = 0;
		this.arms = 0;
		this.time = LocalDateTime.now();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getLikes() {
		return likes;
	}

	public void addLike() {
		this.likes += 1;
	}
	
	public void subLike() {
		this.likes -= 1;
	}

	public Integer getEmotes() {
		return emotes;
	}

	public void addEmote() {
		this.emotes += 1;
	}
	
	public void subEmote() {
		this.emotes -= 1;
	}
	
	public Integer getArms() {
		return arms;
	}

	public void addArm() {
		this.arms += 1;
	}
	
	public void subArm() {
		this.arms -= 1;
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Post other = (Post) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
