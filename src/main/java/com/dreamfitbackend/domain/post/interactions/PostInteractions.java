package com.dreamfitbackend.domain.post.interactions;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.dreamfitbackend.domain.post.Post;
import com.dreamfitbackend.domain.usuario.User;

@Entity
@Table(name = "post_interactions")
@IdClass(PostInteractionsPK.class)
public class PostInteractions implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private User user;
	
	@Id
	private Post post;
	
	@Id
	private Integer interaction;
	
	public PostInteractions() {}

	public PostInteractions(User user, Post post, Integer interaction) {
		this.user = user;
		this.post = post;
		this.interaction = interaction;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Integer getInteraction() {
		return interaction;
	}

	public void setInteraction(Integer interaction) {
		this.interaction = interaction;
	}

}
