package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.enums.DifficultyRecipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;
import com.gmail.vsyniakin.repository.interfaces.RecipeDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeDAOImpl implements RecipeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Recipe add(Recipe recipe) {
        return entityManager.merge(recipe);
    }

    @Override
    public Recipe update(Recipe recipe) {
        return entityManager.merge(recipe);
    }

    @Override
    public Recipe getRecipeByIdFetchIngrStepsAcc(long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = :id", Recipe.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph("recipe.fetchAll"));
        return query.getSingleResult();
    }

    @Override
    public Recipe getByIdFetchMessages(long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.messages WHERE r.id = :id", Recipe.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Recipe getByIdFetchRatingFromUsers(long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.ratingFromUsers WHERE r.id = :id", Recipe.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Recipe> getRecipesDescByRating(double minRating, int start, int count) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r ORDER BY r.rating DESC", Recipe.class);
        query.setFirstResult(start);
        query.setMaxResults(count);
        List<Recipe> recipes = query.getResultList();
        return recipes;
    }

    @Override
    public Recipe getReference(long id) {
        return entityManager.getReference(Recipe.class, id);
    }

    @Override
    public Recipe getByIdFetchImage(long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r LEFT JOIN FETCH r.image WHERE r.id = :id", Recipe.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Recipe> getRecipesByParameters(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients, int start, int count) {
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT r FROM Recipe r WHERE ");
        builderQuery = builderQuery(builderQuery, type, difficulty, rating, timeMin, ingredients);

        List<Object> parameters = parametersToList(type, difficulty, rating, timeMin, ingredients);

        TypedQuery<Recipe> query = entityManager.createQuery(builderQuery.toString(), Recipe.class);

        for (int i = 1; i <= parameters.size(); i++) {
            query.setParameter(i, parameters.get(i - 1));
        }

        query.setFirstResult(start);
        query.setMaxResults(count);

        List<Recipe> recipes = query.getResultList();

        return recipes;
    }

    private StringBuilder builderQuery(StringBuilder result, TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients) {
        int i = 0;
        if (type != null) {
            result = appendAndToBuilderQuery(result, i);
            i++;
            result.append("r.type = ?" + i + " ");
        }
        if (difficulty != null) {
            result = appendAndToBuilderQuery(result, i);
            i++;
            result.append("r.difficulty = ?" + i + " ");
        }
        if (rating > 0.0) {
            result = appendAndToBuilderQuery(result, i);
            i++;
            result.append("r.rating >= ?" + i + " ");
        }
        if (timeMin > 0) {
            result = appendAndToBuilderQuery(result, i);
            i++;
            result.append("r.timeMin <= ?" + i + " ");
        }
        if (ingredients != null) {
            for (int j = 0; j < ingredients.size(); j++) {
                result = appendAndToBuilderQuery(result, i);
                i++;
                result.append("EXISTS (SELECT i FROM r.ingredientsByRecipe i WHERE i.ingredient = ?" + i + ") ");
            }
        }
        if (i == 0) {
            result.replace(result.indexOf("WHERE"), (result.length() - 1), "ORDER BY r.rating DESC");
        }
        return result;
    }

    private StringBuilder appendAndToBuilderQuery(StringBuilder result, int i) {
        if (i > 0) {
            result.append("AND ");
        }
        return result;
    }

    private List<Object> parametersToList(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients) {
        List<Object> parameters = new ArrayList<>();
        if (type != null) {
            parameters.add(type);
        }
        if (difficulty != null) {
            parameters.add(difficulty);
        }
        if (rating > 0.0) {
            parameters.add(rating);
        }
        if (timeMin > 0) {
            parameters.add(timeMin);
        }
        if (ingredients != null) {
            for (int j = 0; j < ingredients.size(); j++) {
                parameters.add(ingredients.get(j));
            }
        }
        return parameters;
    }

    @Override
    public List<Recipe> getRecipesByUserAccount(UserAccount userAccount, int start, int count) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.userAccount=:userAccount", Recipe.class);
        query.setParameter("userAccount", userAccount);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public Recipe getRecipe(long id) {
        return entityManager.find(Recipe.class, id);
    }

    @Override
    public int count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Recipe r", Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public int count(TypeRecipe type, DifficultyRecipe difficulty, double rating, int timeMin, List<Ingredient> ingredients) {
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT COUNT(r) FROM Recipe r WHERE ");
        builderQuery = builderQuery(builderQuery, type, difficulty, rating, timeMin, ingredients);

        List<Object> parameters = parametersToList(type, difficulty, rating, timeMin, ingredients);

        TypedQuery<Long> query = entityManager.createQuery(builderQuery.toString(), Long.class);

        for (int i = 1; i <= parameters.size(); i++) {
            query.setParameter(i, parameters.get(i - 1));
        }

        return query.getSingleResult().intValue();
    }

    @Override
    public int count(UserAccount userAccount) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Recipe r WHERE r.userAccount = :userAccount", Long.class);
        query.setParameter("userAccount", userAccount);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<Recipe> getRecipesByModeration(boolean moderation, int start, int count) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.moderation = :moderation", Recipe.class);
        query.setParameter("moderation", moderation);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(boolean moderation) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Recipe r WHERE r.moderation = :moderation", Long.class);
        query.setParameter("moderation", moderation);
        return query.getSingleResult().intValue();
    }

    @Override
    public void delete(Recipe recipe) {
        entityManager.remove(recipe);
    }

    @Override
    public List<Recipe> getRecipesByName(String name, int start, int count) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name  LIKE :name", Recipe.class);
        query.setParameter("name", "%" + name + "%");
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(String name) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Recipe r WHERE r.name LIKE :name", Long.class);
        query.setParameter("name", "%" + name + "%");
        return query.getSingleResult().intValue();
    }

    @Override
    public List<Recipe> getRecipesDescByDate(int start, int count) {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r ORDER BY r.date DESC", Recipe.class);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }
}
