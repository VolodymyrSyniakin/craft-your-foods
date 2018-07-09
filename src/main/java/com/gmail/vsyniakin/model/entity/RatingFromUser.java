package com.gmail.vsyniakin.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating_from_users")
public class RatingFromUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "acc_id")
	private UserAccount userAccount;

	public RatingFromUser() {
	}

	public RatingFromUser(int value, Recipe recipe, UserAccount userAccount) {
		this.value = value;
		this.recipe = recipe;
		this.userAccount = userAccount;
	}

	public static RatingFromUser add(int value, Recipe recipe, UserAccount userAccount) {
		RatingFromUser ratingFromUser = new RatingFromUser(value, recipe, userAccount);
		recipe.getRatingFromUsers().add(ratingFromUser);
		userAccount.getRatingFromUser().add(ratingFromUser);
		return ratingFromUser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
