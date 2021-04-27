package com.dreamfitbackend.domain.post.interactions;

import java.io.Serializable;

import com.dreamfitbackend.domain.post.Post;
import com.dreamfitbackend.domain.usuario.User;

public class PostInteractionsPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long user;
	
	private long post;
	
	private int interaction;

	public PostInteractionsPK() {
	}

	public PostInteractionsPK(long user, long post, int interaction) {
		this.user = user;
		this.post = post;
		this.interaction = interaction;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getPost() {
		return post;
	}

	public void setPost(long post) {
		this.post = post;
	}

	public int getInteraction() {
		return interaction;
	}

	public void setInteraction(int interaction) {
		this.interaction = interaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + interaction;
		result = prime * result + (int) (post ^ (post >>> 32));
		result = prime * result + (int) (user ^ (user >>> 32));
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
		if (interaction != other.interaction)
			return false;
		if (post != other.post)
			return false;
		if (user != other.user)
			return false;
		return true;
	}

}
