package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.IngredientByRecipe;
import com.gmail.vsyniakin.repository.interfaces.IngredientByRecipeDAO;
import com.gmail.vsyniakin.services.interfaces.IngredientByRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientByRecipeServiceImpl implements IngredientByRecipeService {

    @Autowired
    IngredientByRecipeDAO ingredientByRecipeDAO;

    @Override
    @Transactional
    public void delete(long id) {
        IngredientByRecipe ingredientByRecipe = ingredientByRecipeDAO.getReference(id);
        if (ingredientByRecipe != null) {
            ingredientByRecipeDAO.delete(ingredientByRecipe);
        }
    }
}
