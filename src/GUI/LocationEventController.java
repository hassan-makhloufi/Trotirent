/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.EventService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LocationEventController implements  Initializable {

 
    @FXML
    private TextField o;
    @FXML
    private TextField t;
     @FXML
    private Label titleLocation;
    
   
    
    
    private DecimalFormat formatter = new DecimalFormat("###.00000");
    
     private Connection conn;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize() called");
//        EventService se =new EventService();
//        event ev =new event();
////        mapReady();
////         
//

  
    
    }
    
//    public void mapReady() {
//    System.out.println("mapReady() called");
//    MapOptions mapOptions = new MapOptions();
//    mapOptions.center(new LatLong(47.6097, -122.3331))
//            .mapType(MapTypeIdEnum.ROADMAP)
//            .zoom(9);
//    map = gmap.createMap(mapOptions, false);
//
//    map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
//        LatLong location = event.getLatLong();
//        o.setText(formatter.format(location.getLatitude()));
//        t.setText(formatter.format(location.getLongitude()));
//    });
//}
     
      public boolean verify(){
          System.out.println("verify");
        if (!(ControleSaisie.isString(o.getText())&&ControleSaisie.isString(t.getText())) ){
            //message.setText("PLEASE SELECT A LOCATION OR CANCEL")
            return false;
        }
        
        return true;
        
    }
//
//    private void setPositionMArker(LatLong location) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
    private static class ControleSaisie {

          public static boolean isString(String text) {
        if (text.trim()!=null) {
            return true;
        } 
            return false;
    }
    }
//     @FXML
//    void cancel(ActionEvent event) {
//
//    }
//
    @FXML
    void save(ActionEvent event) {
         if (verify()) {
        String attitude = o.getText();
        String longitude = t.getText();
        System.out.println("attitude =" + attitude + "longitude" + longitude);
        
        // Enregistrer la localisation dans la base de données
//        Localisation loc = new Localisation(attitude, longitude);
//        LocalisationService locService = new LocalisationService();
//        locService.addLocalisation(loc);
        
        // Afficher un message de confirmation
             Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Enregistrement réussi");
        alert.setHeaderText(null);
        alert.setContentText("La localisation a été enregistrée avec succès.");
        alert.showAndWait();
    }

    }
}
