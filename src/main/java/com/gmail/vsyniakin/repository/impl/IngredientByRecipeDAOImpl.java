package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.IngredientByRecipe;
import com.gmail.vsyniakin.repository.interfaces.IngredientByRecipeDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class IngredientByRecipeDAOImpl implements IngredientByRecipeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(IngredientByRecipe ingredientByRecipe) {
        entityManager.remove(ingredientByRecipe);
    }

    @Override
    public IngredientByRecipe getReference(long id) {
        return entityManager.getReference(IngredientByRecipe.class, id);
    }
}
