package interpreter;

public class DatabaseTestClass {
    public static void main(String[] args) {
        SQLService sqlService = new SQLService();
        sqlService.addRecipe("Chocolate Chip Cookies", 16, 1, "cookie", "Mix everything together and bake at 350 degree for 14 minutes.");
        sqlService.addIngredient("Chocolate Chip Cookies", "Flour", "cups", 3);
        sqlService.addIngredient("Chocolate Chip Cookies", "Sugar", "cups", 2);
        sqlService.addIngredient("Chocolate Chip Cookies", "Eggs", "large", 2);
    }
}
