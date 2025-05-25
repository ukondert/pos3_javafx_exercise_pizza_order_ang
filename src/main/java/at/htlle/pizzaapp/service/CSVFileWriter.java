package at.htlle.pizzaapp.service;

import at.htlle.pizzaapp.model.Pizza;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for writing pizza orders to a CSV file
 */
public class CSVFileWriter {
    
    private static final String DELIMITER = ";";
    private static final String CSV_FILE = "pizza_orders.csv";
    
    /**
     * Write a pizza order to a CSV file
     * @param pizza The pizza to save
     * @param customerName Customer name
     */
    public static void writePizzaOrder(Pizza pizza, String customerName) throws IOException {
        // Create header if file doesn't exist
        Path path = Paths.get(CSV_FILE);
        boolean newFile = !Files.exists(path);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            if (newFile) {
                writer.write(createHeader());
                writer.newLine();
            }
            
            writer.write(createOrderEntry(pizza, customerName));
            writer.newLine();
        }
    }
    
    private static String createHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("Datum");
        headers.add("Kunde");
        headers.add("Teig");
        headers.add("Größe");
        headers.add("Käserand");
        headers.add("Zutaten");
        headers.add("Lieferung");
        headers.add("Adresse");
        headers.add("Preis");
        
        return String.join(DELIMITER, headers);
    }
    
    private static String createOrderEntry(Pizza pizza, String customerName) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        List<String> values = new ArrayList<>();
        values.add(now.format(formatter));
        values.add(customerName);
        values.add(pizza.getDough().getDisplayValue());
        values.add(pizza.getSize().getDisplayValue());
        values.add(pizza.hasCheeseEdge() ? "Ja" : "Nein");
        values.add(pizza.getIngredients().stream()
                .map(ingredient -> ingredient.getDisplayValue())
                .collect(Collectors.joining(",")));
        values.add(pizza.getDeliveryOption().getDisplayValue());
        values.add(pizza.getDeliveryAddress() != null ? pizza.getDeliveryAddress() : "");
        values.add(String.format("%.2f", pizza.calculateTotalPrice()));
        
        return String.join(DELIMITER, values);
    }
}
