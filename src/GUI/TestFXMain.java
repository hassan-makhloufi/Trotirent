/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package GUI;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


/**
 *
 * @author ASUS
 */
public class TestFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root=FXMLLoader.load(getClass().getResource("ReadEvent.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Trottirent ♥");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
       
    }
    
}
