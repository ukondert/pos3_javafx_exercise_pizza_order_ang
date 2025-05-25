package at.htlle.pizzaapp.model;

/**
 * Enum representing the different types of pizza dough
 */
public enum Dough {
    STANDARD("Standard", 0.0),
    WHOLEGRAIN("Vollkorn", 1.0),
    GLUTEN_FREE("Glutenfrei", 2.0);
    
    private final String displayValue;
    private final double price;
    
    Dough(String displayValue, double price) {
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
