package at.htlle.pizzaapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizza class using Builder pattern
 */
public class Pizza {
    private final Dough dough;
    private final Size size;
    private final boolean cheeseEdge;
    private final List<Ingredient> ingredients;
    private final DeliveryOption deliveryOption;
    private final String deliveryAddress;

    private Pizza(PizzaBuilder builder) {
        this.dough = builder.dough;
        this.size = builder.size;
        this.cheeseEdge = builder.cheeseEdge;
        this.ingredients = new ArrayList<>(builder.ingredients);
        this.deliveryOption = builder.deliveryOption;
        this.deliveryAddress = builder.deliveryAddress;
    }

    public Dough getDough() {
        return dough;
    }

    public Size getSize() {
        return size;
    }

    public boolean hasCheeseEdge() {
        return cheeseEdge;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public int getNumberOfIngredients() {
        return ingredients.size();
    }

    public DeliveryOption getDeliveryOption() {
        return deliveryOption;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Calculate the total price of the pizza
     */
    public double calculateTotalPrice() {
        // Base price based on size
        double totalPrice = size.getBasePrice();
        
        // Add dough price
        totalPrice += dough.getPrice();
        
        // Add cheese edge price if selected
        if (cheeseEdge) {
            totalPrice += 2.0;
        }
        
        // Add price for each ingredient
        for (Ingredient ingredient : ingredients) {
            totalPrice += ingredient.getPrice();
        }
        
        // Add delivery cost
        totalPrice += deliveryOption.getPrice();
        
        return totalPrice;
    }

    /**
     * Builder class for Pizza
     */
    public static class PizzaBuilder {
        private Dough dough = Dough.STANDARD;
        private Size size = Size.MEDIUM;
        private boolean cheeseEdge = false;
        private final List<Ingredient> ingredients = new ArrayList<>();
        private DeliveryOption deliveryOption = DeliveryOption.PICKUP;
        private String deliveryAddress = "";

        public PizzaBuilder withDough(Dough dough) {
            this.dough = dough;
            return this;
        }

        public PizzaBuilder withSize(Size size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder withCheeseEdge(boolean cheeseEdge) {
            this.cheeseEdge = cheeseEdge;
            return this;
        }

        public PizzaBuilder addIngredient(Ingredient ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        public PizzaBuilder withDeliveryOption(DeliveryOption deliveryOption) {
            this.deliveryOption = deliveryOption;
            return this;
        }

        public PizzaBuilder withDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Pizza build() {
            validate();
            return new Pizza(this);
        }

        /**
         * Validate the pizza configuration
         */
        public void validate() {
            // Check if gluten-free dough has cheese edge
            if (dough == Dough.GLUTEN_FREE && cheeseEdge) {
                throw new IllegalStateException("Gluten-free dough cannot be combined with cheese edge");
            }
            
            // Check maximum number of ingredients
            int maxIngredients = (size == Size.LARGE) ? 4 : 3;
            if (ingredients.size() > maxIngredients) {
                throw new IllegalStateException("Too many ingredients for this pizza size. Maximum allowed: " + maxIngredients);
            }
            
            // Check if delivery address is provided for delivery options
            if (deliveryOption != DeliveryOption.PICKUP && (deliveryAddress == null || deliveryAddress.trim().isEmpty())) {
                throw new IllegalStateException("Delivery address is required for delivery options");
            }
        }
    }
}
