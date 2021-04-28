package com.dreamfitbackend.domain.post.models;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Feed {
	
	@ApiModelProperty(position = 1)
	private String profile_picture;
	
	@ApiModelProperty(position = 2)
	private List<PostOutputListElement> posts;

	public Feed(String profile_picture) {
		this.profile_picture = profile_picture;
		this.posts = new ArrayList<>();
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public List<PostOutputListElement> getPosts() {
		return posts;
	}

	public void setPosts(List<PostOutputListElement> posts) {
		this.posts = posts;
	}

}
