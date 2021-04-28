package com.dreamfitbackend.domain.usuario.models;

public class UserOutputName {
	
	private String name;
	
	private String profile_picture;
	
	public UserOutputName(String name, String profile_picture) {
		this.name = name;
		this.profile_picture = profile_picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}	

}
