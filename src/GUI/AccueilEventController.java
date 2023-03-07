/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import entity.user;
import entity.event;
import entity.reservation;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.EventService;
import services.ResService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AccueilEventController implements Initializable {
    
      
    @FXML
    private TextField txt_datedebut;

    @FXML
    private TextField txt_datefin;

    @FXML
    private TextField txt_dsecription;

    @FXML
    private TextField txt_heure;

    @FXML
    private ImageView txt_image;

    @FXML
    private TextField txt_localisation;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prix;

    @FXML
    private TextField nb_event;
      @FXML
    private TextField rech;

      
    private Connection conn;
    
    private List<event> eventsList;
    
    private int currentIndex;
    
    public AccueilEventController(){
        conn = DataSource.getInstance().getCnx();
        eventsList = new ArrayList<>();
        currentIndex = 0;

    }
    public void ShowEvent(int index){
        event e = eventsList.get(index);
        txt_nom.setText(e.getNomEvent());
        txt_datedebut.setText(e.getDateDebut().toString());
        txt_datefin.setText(e.getDateFin().toString());
        txt_localisation.setText(e.getLocalisation());
        txt_dsecription.setText(e.getDescription());
        txt_heure.setText(e.getHeureEvent());
        txt_prix.setText(String.valueOf(e.getPrix()));
        Image image = new Image(new File(e.getPhotoE()).toURI().toString());
        txt_image.setImage(image);
        currentIndex = index; 
        EventService ES = new EventService();
        int x = ES.CountEvent(); 
        nb_event.setText(currentIndex+1+"-" + String.valueOf(x));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        EventService ES = new EventService();
//        //calculer nombre des evenements disponible
//        int x = ES.CountEvent(); 
//        nb_event.setText(currentIndex+"-" + String.valueOf(x));
//        
        
        String requete = "select * from event";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("IdEvent");
                String nom = rs.getString("NomEvent");
                LocalDate dateDebut = rs.getDate("DateDebut").toLocalDate();
                LocalDate dateFin = rs.getDate("DateFin").toLocalDate();
                String localisation = rs.getString("localisation");
                String description = rs.getString("Description");
                String heure = rs.getString("HeureEvent");
                float prix = rs.getFloat("prix");
                String photo = rs.getString("photoE");
                
                event e = new event(id, nom, dateDebut, dateFin, localisation, description, heure, prix, photo);
                eventsList.add(e);
            }
            // show the first event in the list index=0
            ShowEvent(0);
                } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
                    }
         }    
    
        @FXML
        void pred(ActionEvent event) {
            if (currentIndex > 0) {
            ShowEvent(currentIndex - 1);
             }
        }

        @FXML
        void suiv(ActionEvent event) {
             if (currentIndex < eventsList.size() - 1) {
            ShowEvent(currentIndex + 1);
          }
         }
    @FXML
    void reserver(ActionEvent event) {
        // récupérer l'événement actuellement affiché
        event e = eventsList.get(currentIndex);
        reservation r = new reservation();
        user u=new user(1, "test"); 
        r.setIdEvent(e);
        r.setIdUser(u); // TODO: remplacer 1 par l'ID de l'utilisateur connecté
        ResService RS = new ResService();
        RS.insert(r);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Félicitation");
        alert.setHeaderText(null);
        alert.setContentText("Reservation ajouté avec succès !");
        alert.showAndWait();   
    }
     @FXML
    void search(ActionEvent event) {
           String searchQuery = rech.getText();
    EventService eventService = new EventService();
    List<event> searchResults = eventService.recherche(searchQuery);
 
        eventsList.clear();
        eventsList.addAll(searchResults);
//        nb_event.setText("il y a " + eventsList.size() + " évènements disponible");
//        ShowEvent(0);

Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultats de recherche");
        alert.setHeaderText(null);
        alert.setContentText("il y a "+searchResults.size()+" évènement(s) disponible");
        alert.showAndWait();


    }
}

