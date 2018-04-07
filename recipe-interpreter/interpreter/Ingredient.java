package interpreter;

public class Ingredient {
    private String value;
    private String metric;
    private String ingredient;

    public Ingredient(String val, String met, String ing) {
        value = val;
        metric = met;
        ingredient = ing;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Ingredient: " + ingredient + ": " + "Metric: " + metric + " Value: " + value;
    }
}
