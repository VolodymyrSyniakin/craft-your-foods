package com.gmail.vsyniakin.model.enums;

import java.util.*;

public enum DifficultyRecipe {
    EASY, MEDIUM, HARD, VERY_HARD, INTENSE;

    public static Map<DifficultyRecipe, String> toMap() {
        Map<DifficultyRecipe, String> map = new LinkedHashMap<>();

        for (DifficultyRecipe difficultyRecipe : DifficultyRecipe.values()) {
            map.put(difficultyRecipe, difficultyRecipe.toString());
        }
        return map;
    }

    @Override
    public String toString() {
        switch (this) {
            case EASY:
                return "Легко";
            case MEDIUM:
                return "Средне";
            case HARD:
                return "Сложно";
            case VERY_HARD:
                return "Очень сложно";
            case INTENSE:
                return "Только для профи";
        }
        return "Легко";
    }

}
