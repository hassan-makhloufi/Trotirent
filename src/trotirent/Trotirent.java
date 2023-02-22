/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trotirent;

import entity.user;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.userService;

/**
 *
 * @author Admin
 */
public class Trotirent extends Application{
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create your JavaFX scene here
       Trotirent.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
      
        // Launch the JavaFX application
        launch(args);
    }
}

