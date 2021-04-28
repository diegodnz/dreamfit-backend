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
	private long user_id;
	
	@Id
	private long post_id;
	
	private Integer interaction;
	
	public PostInteractions() {}

	public PostInteractions(long user_id, long post_id, Integer interaction) {
		this.user_id = user_id;
		this.post_id = post_id;
		this.interaction = interaction;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getPost_id() {
		return post_id;
	}

	public void setPost_id(long post_id) {
		this.post_id = post_id;
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
		result = prime * result + (int) (post_id ^ (post_id >>> 32));
		result = prime * result + (int) (user_id ^ (user_id >>> 32));
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
		if (post_id != other.post_id)
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}

}
