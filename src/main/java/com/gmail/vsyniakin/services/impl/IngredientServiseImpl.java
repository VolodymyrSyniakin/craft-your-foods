package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.repository.interfaces.IngredientDAO;
import com.gmail.vsyniakin.services.interfaces.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiseImpl implements IngredientService {

	@Autowired
	private IngredientDAO ingredientDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> searchByName(String name) {
		return ingredientDAO.searchByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredientsById(long[] idArray) {
		return ingredientDAO.getIngredientsById(idArray);
	}
}
