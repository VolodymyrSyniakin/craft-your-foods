package com.gmail.vsyniakin.controllers;


import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.services.interfaces.IngredientService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @RequestMapping(value = "/search_ingredients", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Ingredient> searchIngredientsByName(@RequestParam String name) {
        List<Ingredient> ingredients = ingredientService.searchByName(name);
        return ingredients;
    }
}
