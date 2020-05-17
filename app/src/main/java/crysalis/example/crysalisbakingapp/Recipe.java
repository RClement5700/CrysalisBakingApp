package crysalis.example.crysalisbakingapp;

import java.util.ArrayList;

public class Recipe {
    String name;
    int id;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;

    public Recipe(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Recipe(String name, int id, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
