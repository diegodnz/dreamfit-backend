package com.dreamfitbackend.domain.post.interactions;

import java.io.Serializable;

import com.dreamfitbackend.domain.post.Post;
import com.dreamfitbackend.domain.usuario.User;

public class PostInteractionsPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long user_id;
	
	private long post_id;

	public PostInteractionsPK() {
	}

	public PostInteractionsPK(long user_id, int post_id) {
		this.user_id = user_id;
		this.post_id = post_id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PostInteractionsPK other = (PostInteractionsPK) obj;
		if (post_id != other.post_id)
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}

}
