package com.dreamfitbackend.domain.post.models;

import java.time.LocalDateTime;

import com.dreamfitbackend.domain.post.interactions.Interaction;
import com.dreamfitbackend.domain.usuario.models.UserOutputName;

import io.swagger.annotations.ApiModelProperty;

public class PostOutputListElement {
	
	@ApiModelProperty(position = 1)
	private Long id;
	
	@ApiModelProperty(position = 2)
	private UserOutputName user;
	
	@ApiModelProperty(position = 3)
	private Interaction userAction;
	
	@ApiModelProperty(position = 4)
	private String description;
	
	@ApiModelProperty(position = 5)
	private String picture;
	
	@ApiModelProperty(position = 6)
	private Integer likes;
	
	@ApiModelProperty(position = 7)
	private Integer emotes;
	
	@ApiModelProperty(position = 8)
	private Integer arms;
	
	@ApiModelProperty(position = 9)
	private LocalDateTime time;

	public PostOutputListElement(Long id, UserOutputName user, Interaction userAction, String description, String picture, Integer likes,
			Integer emotes, Integer arms, LocalDateTime time) {
		this.id = id;
		this.user = user;
		this.userAction = userAction;
		this.description = description;
		this.picture = picture;
		this.likes = likes;
		this.emotes = emotes;
		this.arms = arms;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserOutputName getUser() {
		return user;
	}

	public void setUser(UserOutputName user) {
		this.user = user;
	}

	public String getUserAction() {
		if (userAction != null) {
			return userAction.getDescricao();
		}
		return null;
	}

	public void setUserAction(Interaction userAction) {
		this.userAction = userAction;
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

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getEmotes() {
		return emotes;
	}

	public void setEmotes(Integer emotes) {
		this.emotes = emotes;
	}

	public Integer getArms() {
		return arms;
	}

	public void setArms(Integer arms) {
		this.arms = arms;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
