package com.dreamfitbackend.domain.post.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.configs.storage.AmazonClient;
import com.dreamfitbackend.domain.post.Post;
import com.dreamfitbackend.domain.post.PostRepository;
import com.dreamfitbackend.domain.post.models.PostOutputListElement;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.models.UserOutputName;

@Service
public class PostServices {
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private PostRepository postRepo;
	
	public List<PostOutputListElement> list(int page) {
		if (page < 1) {
			return new ArrayList<>();
		}
		int start = (page-1) * 5;
		int limit = page * 5;		
		List<Post> posts = postRepo.getWithLimit(limit);
		if (start + 1 > posts.size()) {
			return new ArrayList<>();
		}
		posts = posts.subList(start, posts.size());
		List<PostOutputListElement> postsOutput = new ArrayList<>();
		for (Post p : posts) {
			postsOutput.add(new PostOutputListElement(p.getId(), new UserOutputName(p.getUser().getName()), p.getDescription(), p.getPicture(), p.getLikes(), p.getEmotes(), p.getArms(), p.getTime()));
		}
		return postsOutput;		
	}
	
	public void create(User user, String description, MultipartFile image) {		
		if (description == null && image == null) {
			throw new MessageException("Postagem vazia", HttpStatus.BAD_REQUEST);
		}
		if (description != null) {
			description = description.trim();
			if (description.length() > 255) {
				throw new MessageException("A descrição da postagem excede o limite de caracteres: 255", HttpStatus.BAD_REQUEST);
			}
		}
		
		Post post = new Post(user, description);
		if (image != null) {			
			String fileUrl = amazonClient.uploadFile(image, UUID.randomUUID().toString() + System.currentTimeMillis());
			post.setPicture(fileUrl);
		}
		postRepo.save(post);	
		
	}
}
