package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.repository.interfaces.IngredientDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class IngredientDAOImpl implements IngredientDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ingredient> searchByName(String name) {
        TypedQuery<Ingredient> query = entityManager.createQuery("SELECT i FROM Ingredient i WHERE i.name  LIKE :name", Ingredient.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Ingredient> getIngredientsById(long[] idArray) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append("SELECT i FROM Ingredient i WHERE");

        for (int i = 0; i < idArray.length; i++) {
            strQuery.append(" i.id=?" + (i + 1));
            if (i < (idArray.length - 1)) {
                strQuery.append(" OR");
            }
        }

        TypedQuery<Ingredient> query = entityManager.createQuery(strQuery.toString(), Ingredient.class);
        for (int i = 0; i < idArray.length; i++) {
            query.setParameter(i + 1, idArray[i]);
        }

        return query.getResultList();
    }
}
