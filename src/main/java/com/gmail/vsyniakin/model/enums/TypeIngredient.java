package com.gmail.vsyniakin.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeIngredient {
    MEAT, SEAFOOD, VEGETABLES, FRUIT, OTHER;

    public static Map<TypeIngredient, String> toMap() {
        Map<TypeIngredient, String> map = new HashMap<>();

        for (TypeIngredient typeIngredient : TypeIngredient.values()) {
            map.put(typeIngredient, typeIngredient.toString());
        }
        return map;
    }

    @Override
    public String toString() {
        switch (this) {
            case MEAT:
                return "Мясо";
            case SEAFOOD:
                return "Морепродукты";
            case VEGETABLES:
                return "Овощи";
            case FRUIT:
                return "Фрукты";
            case OTHER:
                return "Другое";
        }
        return "Другое";
    }

}
