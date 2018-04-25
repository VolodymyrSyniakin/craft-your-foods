package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.enums.DifficultyRecipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;
import com.gmail.vsyniakin.repository.interfaces.RecipeDAO;
import com.gmail.vsyniakin.services.interfaces.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Override
    @Transactional
    public Recipe add(Recipe recipe) {
        return recipeDAO.add(recipe);
    }

    @Override
    @Transactional
    public Recipe update(Recipe recipe) {
        return recipeDAO.update(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getRecipeByIdFetchIngrStepsAcc(long id) {
        return recipeDAO.getRecipeByIdFetchIngrStepsAcc(id);
    }

    @Override
    @Transactional
    public Recipe getByIdFetchMessages(long id) {
        return recipeDAO.getByIdFetchMessages(id);
    }

    @Override
    @Transactional
    public Recipe getByIdFetchRatingFromUsers(long id) {
        return recipeDAO.getByIdFetchRatingFromUsers(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> getRecipesDescByRating(double minRating, int start, int count) {
        return recipeDAO.getRecipesDescByRating(minRating, start, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getReference(long id) {
        return recipeDAO.getReference(id);
    }

    @Override
    @Transactional
    public Recipe getByIdFetchImage(long id) {
        return recipeDAO.getByIdFetchImage(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByParameters(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients, int start, int count) {
        return recipeDAO.getRecipesByParameters(type, difficulty, rating, timeMin, ingredients, start, count);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Recipe> getRecipesByUserAccount(UserAccount userAccount, int start, int count) {
        return recipeDAO.getRecipesByUserAccount(userAccount, start, count);
    }

    @Override
    @Transactional
    public Recipe getRecipe(long id) {
        return recipeDAO.getRecipe(id);
    }

    @Override
    @Transactional (readOnly = true)
    public int count() {
        return recipeDAO.count();
    }

    @Override
    @Transactional (readOnly = true)
    public int count(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients) {
        return recipeDAO.count(type, difficulty, rating, timeMin, ingredients);
    }

    @Override
    @Transactional (readOnly = true)
    public int count(UserAccount userAccount) {
        return recipeDAO.count(userAccount);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Recipe> getRecipesByModeration(boolean moderation, int start, int count) {
        return recipeDAO.getRecipesByModeration(moderation, start, count);
    }

    @Override
    @Transactional (readOnly = true)
    public int count(boolean moderation) {
        return recipeDAO.count(moderation);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Recipe recipe = recipeDAO.getReference(id);
        recipeDAO.delete(recipe);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Recipe> getRecipesByName(String name, int start, int count) {
        return recipeDAO.getRecipesByName(name, start, count);
    }

    @Override
    @Transactional (readOnly = true)
    public int count(String name) {
        return recipeDAO.count(name);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Recipe> getRecipesDescByDate(int start, int count) {
        return recipeDAO.getRecipesDescByDate(start, count);
    }
}
