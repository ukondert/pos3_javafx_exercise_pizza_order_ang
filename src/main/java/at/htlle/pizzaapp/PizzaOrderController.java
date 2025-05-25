package at.htlle.pizzaapp;

import at.htlle.pizzaapp.model.*;
import at.htlle.pizzaapp.service.CSVFileWriter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller for the Pizza Order view
 */
public class PizzaOrderController implements Initializable {
    
    @FXML private ComboBox<Dough> doughComboBox;
    
    @FXML private RadioButton smallSizeRadio;
    @FXML private RadioButton mediumSizeRadio;
    @FXML private RadioButton largeSizeRadio;
    @FXML private ToggleGroup sizeToggleGroup;
    
    @FXML private RadioButton cheeseEdgeYesRadio;
    @FXML private RadioButton cheeseEdgeNoRadio;
    @FXML private ToggleGroup cheeseEdgeToggleGroup;
    
    @FXML private CheckBox salamiCheckBox;
    @FXML private CheckBox hamCheckBox;
    @FXML private CheckBox baconCheckBox;
    @FXML private CheckBox ruccolaCheckBox;
    @FXML private CheckBox cornCheckBox;
    @FXML private CheckBox onionCheckBox;
    
    @FXML private RadioButton pickupRadio;
    @FXML private RadioButton standardDeliveryRadio;
    @FXML private RadioButton expressDeliveryRadio;
    @FXML private ToggleGroup deliveryToggleGroup;
    
    @FXML private TextArea deliveryAddressTextArea;
    
    @FXML private TextField customerNameField;
    
    @FXML private Button orderButton;
    @FXML private Label totalPriceLabel;
    
    private List<CheckBox> ingredientCheckBoxes;    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize dough combo box - Teigauswahl initialisieren
        doughComboBox.getItems().addAll(Dough.values());
    
        
        doughComboBox.setValue(Dough.STANDARD);
        
        // Set toggle group for size radio buttons
        sizeToggleGroup = new ToggleGroup();
        smallSizeRadio.setToggleGroup(sizeToggleGroup);
        mediumSizeRadio.setToggleGroup(sizeToggleGroup);
        largeSizeRadio.setToggleGroup(sizeToggleGroup);
        mediumSizeRadio.setSelected(true);
        
        // Set toggle group for cheese edge radio buttons
        cheeseEdgeToggleGroup = new ToggleGroup();
        cheeseEdgeYesRadio.setToggleGroup(cheeseEdgeToggleGroup);
        cheeseEdgeNoRadio.setToggleGroup(cheeseEdgeToggleGroup);
        cheeseEdgeNoRadio.setSelected(true);
        
        // Set toggle group for delivery options
        deliveryToggleGroup = new ToggleGroup();
        pickupRadio.setToggleGroup(deliveryToggleGroup);
        standardDeliveryRadio.setToggleGroup(deliveryToggleGroup);
        expressDeliveryRadio.setToggleGroup(deliveryToggleGroup);
        pickupRadio.setSelected(true);
        
        
        // Event-Handler für die Validierung zwischen glutenfreiem Teig und Käserand
        doughComboBox.setOnAction(event -> {
            if (doughComboBox.getSelectionModel().getSelectedItem() == Dough.GLUTEN_FREE) {
                cheeseEdgeYesRadio.setDisable(true);
                cheeseEdgeNoRadio.setSelected(true);
            } else {
                cheeseEdgeYesRadio.setDisable(false);
            }
            updateTotalPrice();
        });
        
        // Event-Handler für die Lieferadresse basierend auf der ausgewählten Lieferoption
        pickupRadio.setOnAction(event -> {
            deliveryAddressTextArea.setDisable(true);
            deliveryAddressTextArea.clear();
            updateTotalPrice();
        });
        
        ingredientCheckBoxes = Arrays.asList(
            salamiCheckBox, hamCheckBox, baconCheckBox, ruccolaCheckBox, cornCheckBox, onionCheckBox
        );
       
        
        // Event-Handler für Größenänderung
        sizeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            validateNumOfIngredients();
            updateTotalPrice();
        });
        
        // Event-Handler für Käserand-Änderung
        cheeseEdgeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalPrice();
        });
        
        // Initial validation and price update
        validateNumOfIngredients();
        updateTotalPrice();
    }
    
    /**
     * Validate the number of ingredients based on pizza size
     */
    @FXML
    private void validateNumOfIngredients() {
        int selectedCount = 0;
        for (CheckBox checkBox : ingredientCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedCount++;
            }
        }
        
        int maxIngredients = getSelectedSize() == Size.LARGE ? 4 : 3;
        
        // Disable checkboxes if max is reached
        for (CheckBox checkBox : ingredientCheckBoxes) {
            if (!checkBox.isSelected()) {
                checkBox.setDisable(selectedCount >= maxIngredients);
            }
        }
    }
    
    /**
     * Update the total price display
     */
    private void updateTotalPrice() {
        try {
            Pizza pizza = buildPizzaFromInputs();
            double totalPrice = pizza.calculateTotalPrice();
            totalPriceLabel.setText(String.format("%.2f €", totalPrice));
        } catch (Exception e) {
            totalPriceLabel.setText("Error");
        }
    }
    
    /**
     * Build a pizza object from the current form state
     */
    private Pizza buildPizzaFromInputs() {
        Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder()
                .withDough(doughComboBox.getValue())
                .withSize(getSelectedSize())
                .withCheeseEdge(cheeseEdgeYesRadio.isSelected())
                .withDeliveryOption(getSelectedDeliveryOption());
        
        // Add selected ingredients
        if (salamiCheckBox.isSelected()) builder.addIngredient(Ingredient.SALAMI);
        if (hamCheckBox.isSelected()) builder.addIngredient(Ingredient.HAM);
        if (baconCheckBox.isSelected()) builder.addIngredient(Ingredient.BACON);
        if (ruccolaCheckBox.isSelected()) builder.addIngredient(Ingredient.RUCCOLA);
        if (cornCheckBox.isSelected()) builder.addIngredient(Ingredient.CORN);
        if (onionCheckBox.isSelected()) builder.addIngredient(Ingredient.ONION);
        
        // Set delivery address if applicable
        if (getSelectedDeliveryOption() != DeliveryOption.PICKUP) {
            builder.withDeliveryAddress(deliveryAddressTextArea.getText());
        }
        
        return builder.build();
    }
    
    /**
     * Get the currently selected size from the radio buttons
     */
    private Size getSelectedSize() {
        if (smallSizeRadio.isSelected()) return Size.SMALL;
        if (largeSizeRadio.isSelected()) return Size.LARGE;
        return Size.MEDIUM; // Default
    }
    
    /**
     * Get the currently selected delivery option
     */
    private DeliveryOption getSelectedDeliveryOption() {
        if (standardDeliveryRadio.isSelected()) return DeliveryOption.STANDARD_DELIVERY;
        if (expressDeliveryRadio.isSelected()) return DeliveryOption.EXPRESS_DELIVERY;
        return DeliveryOption.PICKUP; // Default
    }
    
    /**
     * Handle the order button action
     */
    @FXML
    private void handleOrder() {
        // Validate customer name
        if (customerNameField.getText().trim().isEmpty()) {
            showAlert("Bitte geben Sie einen Namen ein.", Alert.AlertType.WARNING);
            return;
        }
        
        // Validate delivery address if delivery option is selected
        if (getSelectedDeliveryOption() != DeliveryOption.PICKUP && 
            deliveryAddressTextArea.getText().trim().isEmpty()) {
            showAlert("Bitte geben Sie eine Lieferadresse ein.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            // Build the pizza from inputs
            Pizza pizza = buildPizzaFromInputs();
            
            // Save to CSV
            CSVFileWriter.writePizzaOrder(pizza, customerNameField.getText().trim());
            
            // Show success message
            showAlert("Ihre Bestellung wurde erfolgreich aufgenommen!", Alert.AlertType.INFORMATION);
            
            // Reset the form
            resetForm();
        } catch (IllegalStateException e) {
            showAlert("Fehler in der Bestellung: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            showAlert("Fehler beim Speichern der Bestellung: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Reset the form to default values
     */
    @FXML
    private void resetForm() {
        doughComboBox.setValue(Dough.STANDARD);
        mediumSizeRadio.setSelected(true);
        cheeseEdgeNoRadio.setSelected(true);
        pickupRadio.setSelected(true);
        
        for (CheckBox checkBox : ingredientCheckBoxes) {
            checkBox.setSelected(false);
        }
        
        deliveryAddressTextArea.clear();
        customerNameField.clear();
        
        validateNumOfIngredients();
        updateTotalPrice();
    }
    
    /**
     * Show an alert dialog
     */
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Pizza Bestellung");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
