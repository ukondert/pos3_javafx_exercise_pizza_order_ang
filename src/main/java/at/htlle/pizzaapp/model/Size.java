package at.htlle.pizzaapp.model;

/**
 * Enum representing the different pizza sizes
 */
public enum Size {
    SMALL("Klein (Ø 24 cm)", 6.0),
    MEDIUM("Mittel (Ø 30 cm)", 8.0),
    LARGE("Groß (Ø 36 cm)", 10.0);
    
    private final String displayValue;
    private final double basePrice;
    
    Size(String displayValue, double basePrice) {
        this.displayValue = displayValue;
        this.basePrice = basePrice;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    @Override
    public String toString() {
        return displayValue;
    }
}
