package com.gmail.vsyniakin.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.vsyniakin.model.enums.TypeIngredient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String measurement;

    @Enumerated(EnumType.STRING)
    private TypeIngredient type;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore                                                                         // may be delete??
    private List<IngredientByRecipe> ingredientByRecipes = new ArrayList<IngredientByRecipe>();

    public Ingredient(String name, String measurement, TypeIngredient type) {
        this.name = name;
        this.measurement = measurement;
        this.type = type;
    }

    public Ingredient() {
    }


    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeIngredient getType() {
        return type;
    }

    public void setType(TypeIngredient type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientByRecipe> getIngredientByRecipes() {
        return ingredientByRecipes;
    }

    public void setIngredientByRecipes(List<IngredientByRecipe> ingredientByRecipes) {
        this.ingredientByRecipes = ingredientByRecipes;
    }
}
