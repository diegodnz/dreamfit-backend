package com.dreamfitbackend.domain.post.interactions;

import java.io.Serializable;

import com.dreamfitbackend.domain.post.Post;
import com.dreamfitbackend.domain.usuario.User;

public class PostInteractionsPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	
	private Post post;
	
	private Integer interaction;

	public PostInteractionsPK() {
	}

	public PostInteractionsPK(User user, Post post, Integer interaction) {
		this.user = user;
		this.post = post;
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
		PostInteractionsPK other = (PostInteractionsPK) obj;
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
