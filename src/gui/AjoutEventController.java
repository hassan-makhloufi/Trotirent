/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.event;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.EventService;



/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AjoutEventController implements Initializable {

    
   
    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private TextField Description;

    @FXML
    private TextField HeureEvent;

    @FXML
    private TextField Localisation;

    @FXML
    private TextField NomEvent;

    @FXML
    private TextField Prix;
    @FXML
    private ImageView PhotoE;

    
    String path="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    


    @FXML
    void ajout_image(ActionEvent event) {
         FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        // load the selected image into the image view
         path=selectedFile.getAbsolutePath();
        //System.out.println(path);
        Image image = new Image(selectedFile.toURI().toString());
        PhotoE.setImage(image);
    }

    }
    @FXML
    void AjoutEvent(ActionEvent event) throws IOException{
    String nom = NomEvent.getText().trim();
    String localisation = Localisation.getText().trim();
    String description = Description.getText().trim();
    String heure = HeureEvent.getText().trim();
    String prixStr = Prix.getText().trim();
    Image photo = PhotoE.getImage();
       // System.out.println(photo);

    if (nom.isEmpty() || localisation.isEmpty() || description.isEmpty() || heure.isEmpty() || prixStr.isEmpty() || photo == null) {
        // Show an error message if any required fields are empty
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs obligatoires !");
        alert.showAndWait();
        return;
    }

    // Validate the Prix field
    float prix;
    try {
        prix = Float.parseFloat(prixStr);
        if (prix <= 0) {
            throw new NumberFormatException();
        }
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ Prix doit être un nombre positif !");
        alert.showAndWait();
        return;
    }

    // Create the event object and insert it into the database
    LocalDate dateDebut = DateDebut.getValue();
    LocalDate dateFin = DateFin.getValue();
    if (dateDebut == null || dateFin == null || dateDebut.isAfter(dateFin)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("La date de début doit être antérieure à la date de fin !");
        alert.showAndWait();
        return;
    }
    event e = new event(nom, dateDebut, dateFin, localisation, description, heure, prix, path);
    EventService ES = new EventService();
    ES.insert(e);

    // Show a success message and navigate back to the ReadEvent view
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Evenement ajouté avec succès !");
    alert.showAndWait();

    Parent page1 = FXMLLoader.load(getClass().getResource("ReadEvent.fxml"));
    Scene scene = new Scene(page1);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();

    }
    
    
}
