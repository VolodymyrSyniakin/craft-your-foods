package com.gmail.vsyniakin.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeRecipe {

    SALAD, SOUP, MAIN_COURSE, DESSERT, OTHER;

    public static Map<TypeRecipe, String> toMap(){
        Map<TypeRecipe, String> map = new HashMap<>();

        for (TypeRecipe typeRecipe: TypeRecipe.values()) {
            map.put(typeRecipe, typeRecipe.toString());
        }
        return map;
    }

    @Override
    public String toString() {
        switch (this){
            case SALAD:
                return "Салат";
            case SOUP:
                return "Суп";
            case MAIN_COURSE:
                return "Второе блюдо";
            case DESSERT:
                return"Десерт";
            case OTHER:
                return"Другое";
        }
        return "Другое";
    }


}
