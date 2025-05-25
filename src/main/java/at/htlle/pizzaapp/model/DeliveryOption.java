package at.htlle.pizzaapp.model;

/**
 * Enum representing the different delivery options for a pizza order
 */
public enum DeliveryOption {
    PICKUP("Abholung", 0.0),
    STANDARD_DELIVERY("Lieferung Standard (30 min)", 2.0),
    EXPRESS_DELIVERY("Lieferung Express (20 min)", 4.0);
    
    private final String displayValue;
    private final double price;
    
    DeliveryOption(String displayValue, double price) {
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
