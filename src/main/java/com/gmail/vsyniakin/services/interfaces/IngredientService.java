package com.gmail.vsyniakin.services.interfaces;

import com.gmail.vsyniakin.model.entity.Ingredient;

import java.util.List;

public interface IngredientService {

	List<Ingredient> searchByName(String name);

	List<Ingredient> getIngredientsById(long[] idArray);
}
