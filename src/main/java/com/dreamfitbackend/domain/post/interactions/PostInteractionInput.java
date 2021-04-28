package com.dreamfitbackend.domain.post.interactions;

import javax.validation.constraints.NotNull;

public class PostInteractionInput {
	
	private Boolean like;
	
	private Boolean emote;
	
	private Boolean arm;
	
	@NotNull
	private Boolean put;

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public Boolean getEmote() {
		return emote;
	}

	public void setEmote(Boolean emote) {
		this.emote = emote;
	}

	public Boolean getArm() {
		return arm;
	}

	public void setArm(Boolean arm) {
		this.arm = arm;
	}

	public Boolean getPut() {
		return put;
	}

	public void setPut(Boolean put) {
		this.put = put;
	}

}
