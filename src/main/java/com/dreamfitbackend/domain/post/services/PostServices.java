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
import com.dreamfitbackend.domain.post.interactions.Interaction;
import com.dreamfitbackend.domain.post.interactions.PostInteractionInput;
import com.dreamfitbackend.domain.post.interactions.PostInteractions;
import com.dreamfitbackend.domain.post.interactions.PostInteractionsRepository;
import com.dreamfitbackend.domain.post.models.Feed;
import com.dreamfitbackend.domain.post.models.PostOutputListElement;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.models.UserOutputName;

@Service
public class PostServices {
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private PostInteractionsRepository postInteractionsRepo;
	
	public Feed list(User loggedUser, int page) {		
		Feed feedOutput = new Feed(loggedUser.getProfilePicture());
		if (page < 1) {
			return feedOutput;
		}
		
		int start = (page-1) * 5;
		int limit = page * 5;		
		List<Post> posts = postRepo.getWithLimit(limit);
		if (start + 1 > posts.size()) {
			return feedOutput;
		}
		posts = posts.subList(start, posts.size());
		List<PostOutputListElement> postsOutput = new ArrayList<>();
		for (Post p : posts) {
			PostInteractions userPostInteraction = postInteractionsRepo.findRealtion(loggedUser.getId(), p.getId());
			Interaction userInteraction = null;
			if (userPostInteraction != null) {
				userInteraction = Interaction.toEnum(userPostInteraction.getInteraction());
			}
			postsOutput.add(new PostOutputListElement(p.getId(), new UserOutputName(p.getUser().getName(), p.getUser().getProfilePicture()), userInteraction, p.getDescription(), p.getPicture(), p.getLikes(), p.getEmotes(), p.getArms(), p.getTime()));
		}
		feedOutput.setPosts(postsOutput);
		return feedOutput;		
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
	
	public void interact(User user, long postId, PostInteractionInput interactionInput) {
		Boolean like = interactionInput.getLike();
		Boolean emote = interactionInput.getEmote();
		Boolean arm = interactionInput.getArm();
		Boolean putAction = interactionInput.getPut();
		
		Post post = postRepo.getOne(postId);		
		if (post == null) {
			throw new MessageException("Post não encontrado", HttpStatus.BAD_REQUEST);
		}
		
		PostInteractions relationUserPost = postInteractionsRepo.findRealtion(user.getId(), postId);
		if (relationUserPost == null) {
			relationUserPost = new PostInteractions(user.getId(), post.getId(), null);
		}
		
		Interaction interaction = null;		
		if (like != null && like == true) {
			interaction = Interaction.LIKE;			
		} else if (emote != null && emote == true) {
			interaction = Interaction.EMOTE;	
		} else if (arm != null && arm == true) {
			interaction = Interaction.ARM;	
		} else {
			throw new MessageException("Interação inválida", HttpStatus.BAD_REQUEST);
		}		
		
		Interaction oldInteraction = Interaction.toEnum(relationUserPost.getInteraction());
		subInteraction(oldInteraction, post);
		System.out.println(putAction);
		
		if (putAction == true) {
			relationUserPost.setInteraction(interaction.getCod());
			addInteraction(interaction, post);			
			postInteractionsRepo.save(relationUserPost);
		} else {
			if (oldInteraction != interaction) {				
				throw new MessageException("Esta interação não pode ser desfeita pois não existe", HttpStatus.BAD_REQUEST);
			}
			
			relationUserPost.setInteraction(null);
			postInteractionsRepo.save(relationUserPost);
		}
		System.out.println("asihduias");
		postRepo.save(post);
	}
	
	public void subInteraction(Interaction oldInteraction, Post post) {
		System.out.println(oldInteraction);
		if (oldInteraction != null) {
			if (oldInteraction == Interaction.LIKE) {
				post.subLike();
			} else if (oldInteraction == Interaction.EMOTE) {
				post.subEmote();
			} else if (oldInteraction == Interaction.ARM) {
				post.subArm();
			}
		}
	}
	
	public void addInteraction(Interaction interaction, Post post) {
		if (interaction != null) {
			if (interaction == Interaction.LIKE) {
				post.addLike();
			} else if (interaction == Interaction.EMOTE) {
				post.addEmote();
			} else if (interaction == Interaction.ARM) {
				post.addArm();
			}
		}
	}
	
	
}
