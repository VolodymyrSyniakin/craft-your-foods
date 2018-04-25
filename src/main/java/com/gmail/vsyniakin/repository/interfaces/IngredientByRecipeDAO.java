package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.IngredientByRecipe;

public interface IngredientByRecipeDAO {

    void delete(IngredientByRecipe ingredientByRecipe);

    IngredientByRecipe getReference(long id);
}
