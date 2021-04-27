package com.dreamfitbackend.domain.post.interactions;

import java.io.Serializable;

import javax.persistence.Entity;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interaction == null) ? 0 : interaction.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		PostInteractions other = (PostInteractions) obj;
		if (interaction == null) {
			if (other.interaction != null)
				return false;
		} else if (!interaction.equals(other.interaction))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
