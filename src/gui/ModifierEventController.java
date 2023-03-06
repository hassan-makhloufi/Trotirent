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
import javafx.scene.control.Button;
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
public class ModifierEventController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private TextField Description;

    @FXML
    private TextField Localisation;

    @FXML
    private ImageView PhotoE;

    @FXML
    private TextField heure;

    @FXML
    private TextField id;

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;
    @FXML
    private Button modifierPhoto;

    @FXML
    private Button modifierButton;
  
    private event eventToUpdate;

    @FXML
    void modifierButton(ActionEvent event) throws IOException{
    String nomEvent = nom.getText();
    String localisationEvent = Localisation.getText();
    LocalDate dateDebutEvent = DateDebut.getValue();
    LocalDate dateFinEvent = DateFin.getValue();
    String heureEvent = heure.getText();
    String descriptionEvent = Description.getText();
    float prixEvenement = Float.parseFloat(prix.getText());
    String photoE = eventToUpdate.getPhotoE();

    // Contrôle de saisie
    if (nomEvent.isEmpty() || localisationEvent.isEmpty() || dateDebutEvent == null || dateFinEvent == null || heureEvent.isEmpty() || descriptionEvent.isEmpty() || prixEvenement == 0.0) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champ(s) vide(s)");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs!");
        alert.showAndWait();
        return;
    }

    // Mettre à jour l'objet event
    eventToUpdate.setNomEvent(nomEvent);
    eventToUpdate.setLocalisation(localisationEvent);
    eventToUpdate.setDateDebut(dateDebutEvent);
    eventToUpdate.setDateFin(dateFinEvent);
    eventToUpdate.setHeureEvent(heureEvent);
    eventToUpdate.setDescription(descriptionEvent);
    eventToUpdate.setPrix(prixEvenement);
    eventToUpdate.setPhotoE(photoE);

    // Mettre à jour l'événement dans la base de données
    EventService es = new EventService();
    es.update(eventToUpdate);

    // Afficher une alerte pour signaler que la mise à jour est réussie
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Modifier");
    alert.setHeaderText(null);
    alert.setContentText("Modification avec succés!");
    alert.showAndWait();

    // Naviguer vers la page de lecture des événements
    try {
        Parent page1 = FXMLLoader.load(getClass().getResource("ReadEvent.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjoutEventController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    void modifierPhoto(ActionEvent event) {
            // Ouvrir une boîte de dialogue pour sélectionner un fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Modifier la photo de l'événement");
        Stage stage = (Stage) modifierPhoto.getScene().getWindow();
        eventToUpdate.setPhotoE(fileChooser.showOpenDialog(stage).getAbsolutePath());
        
          
        

    }
    
    
     public void setEvent(event eventToUpdate) {
        this.eventToUpdate = eventToUpdate;
        // Afficher les données de l'événement à modifier dans le formulaire
        id.setText(String.valueOf(eventToUpdate.getIdEvent()));
        nom.setText(eventToUpdate.getNomEvent());
        Localisation.setText(eventToUpdate.getLocalisation());
        DateDebut.setValue(eventToUpdate.getDateDebut());
        DateFin.setValue(eventToUpdate.getDateFin());
        heure.setText(eventToUpdate.getHeureEvent());
        Description.setText(eventToUpdate.getDescription());
        prix.setText(String.valueOf(eventToUpdate.getPrix()));
        Image image = new Image(new File(eventToUpdate.getPhotoE()).toURI().toString());
PhotoE.setImage(image);
       

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
}
