package at.htlle.pizzaapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the Pizza Order System
 */
public class PizzaOrderApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaOrderView.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Pizza Order System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
