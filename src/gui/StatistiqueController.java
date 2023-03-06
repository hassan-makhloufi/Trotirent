/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import service.ResService;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class StatistiqueController implements Initializable {
  // Injecter les champs à partir du fichier FXML
    @FXML
    private LineChart<String, Number> statistique;
    @FXML
    private NumberAxis x;
    @FXML
    private CategoryAxis y;
    
    private FXMLLoader fxmlLoader;
    
    
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResService rs = new ResService();
        // Obtenir le nombre de réservations par événement sous forme d'une liste d'objets
        List<Object[]> ReservationsPerEvent = rs.getReservationsPerEvent();

        // Créer une nouvelle série de données pour le graphique
        XYChart.Series<String, Number> set1 = new XYChart.Series<>();
        set1.setName("Réservations par événement");

        // Parcourir la liste des réservations par événement et ajouter chaque événement et son nombre de réservations à la série de données
        for (Object[] r : ReservationsPerEvent) {
            set1.getData().add(new XYChart.Data<>(r[0].toString(), Integer.parseInt(r[1].toString())));
        }

        // Ajouter la série de données au graphique
        statistique.getData().add(set1);
    }
   
    @FXML
    void close(ActionEvent event) {
// récupérer la fenêtre parente
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // fermer la fenêtre
    stage.close();
    }


    

   }






