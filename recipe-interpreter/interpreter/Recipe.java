package interpreter;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients;
    private String directions;

    public Recipe(ArrayList ingredientsList, String recipeDirections) {
        ingredients = ingredientsList;
        directions = recipeDirections;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
