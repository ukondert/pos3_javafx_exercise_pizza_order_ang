package at.htlle.pizzaapp.model;

/**
 * Enum representing the different ingredients (toppings) for a pizza
 */
public enum Ingredient {
    SALAMI("Salami", 1.0),
    HAM("Schinken", 1.0),
    BACON("Speck", 1.0),
    RUCCOLA("Ruccola", 1.0),
    CORN("Mais", 1.0),
    ONION("Zwiebel", 1.0);
    
    private final String displayValue;
    private final double price;
    
    Ingredient(String displayValue, double price) {
        this.displayValue = displayValue;
        this.price = price;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return displayValue;
    }
}
