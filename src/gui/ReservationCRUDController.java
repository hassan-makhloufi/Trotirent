/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import entity.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.ResService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ReservationCRUDController implements Initializable {

    @FXML
    private TableColumn<reservation, Integer> res;
    @FXML
    private TableColumn<reservation, String> ev;
    @FXML
    private TableColumn<reservation, String> us;
    


    
        @FXML
    private TableView<reservation> table;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        ResService RS =new ResService();
        List<reservation> reservations = RS.readAll();
     
        ObservableList<reservation> reservationData = FXCollections.observableArrayList(reservations);
        
        table.setItems(reservationData);
        res.setCellValueFactory(new PropertyValueFactory<>("idRes"));
   // ev.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdEvent().getIdEvent()).asObject());
        us.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUser().getNomUser()));

          ev.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdEvent().getNomEvent()));
         
                   }

    


    @FXML
    private void Supp_Res(ActionEvent event) {
        
         if (table.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        reservation selectedEvent = table.getSelectionModel().getSelectedItem();
        ResService RS=new ResService();
        RS.delete(selectedEvent);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("Reservation Supprimé !");
        alert.showAndWait();
        
          }
         else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("Selectionner une réservation pour supprimer!");
        alert.showAndWait();
        
             
         }
         //table.refresh();
         try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ReservationCRUD.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                  


     
            } catch (IOException ex) {
                Logger.getLogger(ReservationCRUDController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
       @FXML
    void GoToStatistique(ActionEvent event) throws IOException {
 try {
            Parent root = FXMLLoader.load(getClass().getResource("statistique.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


    }
    
}
