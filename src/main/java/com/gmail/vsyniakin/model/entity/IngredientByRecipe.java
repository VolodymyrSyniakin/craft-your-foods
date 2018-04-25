package com.gmail.vsyniakin.model.entity;

import com.gmail.vsyniakin.model.enums.ImportantIngredient;

import javax.persistence.*;

@Entity
@Table(name = "ingredients_by_recipe")
public class IngredientByRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double weight;

    @Enumerated(EnumType.STRING)
    private ImportantIngredient important;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public IngredientByRecipe() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public ImportantIngredient getImportant() {
        return important;
    }

    public void setImportant(ImportantIngredient important) {
        this.important = important;
    }
}
