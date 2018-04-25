package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.Ingredient;

import java.util.List;

public interface IngredientDAO {

    List<Ingredient> searchByName(String name);

    List<Ingredient> getIngredientsById(long[] idArray);

}
