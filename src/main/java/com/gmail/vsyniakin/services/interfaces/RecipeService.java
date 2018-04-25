package com.gmail.vsyniakin.services.interfaces;

import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.enums.DifficultyRecipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;

import java.util.List;

public interface RecipeService {

    Recipe add(Recipe recipe);

    Recipe update(Recipe recipe);

    Recipe getRecipe(long id);

    Recipe getRecipeByIdFetchIngrStepsAcc(long id);

    Recipe getByIdFetchMessages(long id);

    Recipe getByIdFetchRatingFromUsers(long id);

    Recipe getReference(long id);

    Recipe getByIdFetchImage(long id);

    List<Recipe> getRecipesDescByRating(double minRating, int start, int count);

    int count();

    List<Recipe> getRecipesByParameters(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients, int start, int count);

    int count(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients);

    List<Recipe> getRecipesByUserAccount(UserAccount userAccount, int start, int count);

    int count(UserAccount userAccount);

    List<Recipe> getRecipesByModeration(boolean moderation, int start, int count);

    int count(boolean moderation);

    void delete(long id);

    List<Recipe> getRecipesByName(String name, int start, int count);

    int count(String name);

    List<Recipe> getRecipesDescByDate (int start, int count);

}
