package com.gmail.vsyniakin.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "steps")
public class Step {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int position;
	@Column(length = 1024)
	private String text;

	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	public Step() {
	}

	public Step(int position, String text, Recipe recipe) {
		this.position = position;
		this.text = text;
		this.recipe = recipe;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Step))
			return false;

		Step step = (Step) o;

		return position == step.position;
	}

	@Override
	public int hashCode() {
		return position;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
