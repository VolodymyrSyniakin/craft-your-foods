package com.gmail.vsyniakin.model.enums;

import java.util.LinkedHashMap;

import java.util.Map;

public enum ImportantIngredient {
    NOT, LOW, MIDDLE, HEIGHT, HIGHEST;

    public static Map<ImportantIngredient, String> toMap() {
        Map<ImportantIngredient, String> map = new LinkedHashMap<>();

        for (ImportantIngredient importantIngredient : ImportantIngredient.values()) {
            map.put(importantIngredient, importantIngredient.toString());
        }
        return map;
    }

    @Override
    public String toString() {
        switch (this) {
            case NOT:
                return "На рецепт не влияет";
            case LOW:
                return "Можно обойтись";
            case MIDDLE:
                return "Попробуй заменить";
            case HEIGHT:
                return "Без меня и рецепт не нужен";
            case HIGHEST:
                return "Есть будешь сам!";
        }
        return "Попробуй заменить";
    }

}
