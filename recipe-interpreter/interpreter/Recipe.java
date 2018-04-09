package interpreter;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients;
    private String directions;
    private String title;

    public Recipe(String title, ArrayList ingredientsList, String recipeDirections) {
        this.title = title;
        this.ingredients = ingredientsList;
        this.directions = recipeDirections;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Title: " + this.title + "\n");
        builder.append("Ingredients:\n");
        this.ingredients.forEach(ingredient -> builder.append(ingredient.toString()).append("\n"));
        builder.append("Directions:\n");
        builder.append(this.directions);
        return builder.toString();
    }
}
