package com.dreamfitbackend.domain.rewards;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dreamfitbackend.domain.rewards.models.RewardOutputRedeem;
import com.dreamfitbackend.domain.usuario.User;

@SqlResultSetMapping(
	    name = "RewardOutputRedeem",
	    classes = @ConstructorResult(
	        targetClass = RewardOutputRedeem.class,
	        columns = {
	            @ColumnResult(name = "id", type = Long.class),
	            @ColumnResult(name = "quantity", type = Integer.class)
	        }
	    )
	)

@Entity
@Table(name = "rewards")
public class Reward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(
	  name = "user_rewards", 
	  joinColumns = @JoinColumn(name = "reward_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;
	
	@NotNull
	private String title;
	
	private String description;
	
	@NotNull
	private Integer quantity;
	
	@NotNull
	private Integer price;
	
	private String picture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
