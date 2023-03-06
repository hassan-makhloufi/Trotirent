package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import entity.User;
import entity.event;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EventService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.itextpdf.text.Image.*;

import javafx.stage.Stage;
import service.ResService;
import service.UserService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import static com.itextpdf.text.pdf.BidiOrder.PDF;
import static com.itextpdf.text.pdf.PdfName.PDF;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.print.DocFlavor.URL.PDF;
//import com.itextpdf.text.com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.BLACK;






public class ReadEventController implements Initializable{

    @FXML
    private TableColumn<event, LocalDate> DDebut;

    @FXML
    private TableColumn<event, LocalDate> DFin;

    @FXML
    private TableColumn<event, String> Desc;

    @FXML
    private TableColumn<event, String> heure;

    @FXML
    private TableColumn<event, Integer> id;

    @FXML
    private TableColumn<event,String> loca;

    @FXML
    private TableColumn<event, String> nom;
     
   

    @FXML
    private TableColumn<event, Float> prix;

    @FXML
    private TableView<event> table;
//     @FXML
//    private ComboBox<User> userComboBox;
        @FXML
    private ImageView imageSet;

        
        // Constants for image positioning and scaling
private static final int IMAGE_X = 100;
private static final int IMAGE_Y = 500;
private static final int IMAGE_WIDTH = 400;
private static final int IMAGE_HEIGHT = 400;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        UserService userService = new UserService();
        List<User> users = userService.readIdNom();
           
//        ObservableList<User> userData = FXCollections.observableArrayList(users);
//        userComboBox.setItems(userData);
        
        
        EventService Es=new EventService();
        // Appeler la méthode "readAll()" de "EventService" pour récupérer la liste des événements
        List<event> events = Es.readAll();
        // Convertir la liste en une "ObservableList" pour pouvoir l'utiliser avec "TableView"
        ObservableList<event> eventData = FXCollections.observableArrayList(events);
        // Ajouter les données à la table
        //eventData.addAll(events);
        table.setItems(eventData);
        
            
            id.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
            loca.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            DDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            DFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
            heure.setCellValueFactory(new PropertyValueFactory<>("heureEvent"));
            Desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            
            
            //afficher le image 
 
        table.setOnMouseClicked(event -> {
        // Get the selected event
        event selectedEvent = table.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            // Update the ImageView with the event's photo
            Image image = new Image(new File(selectedEvent.getPhotoE()).toURI().toString());
            imageSet.setImage(image);
        }
    });
    } 
      @FXML
    void ToAjouter(ActionEvent event) {
   try {
                Parent page1 = FXMLLoader.load(getClass().getResource("AjoutEvent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                  


     
            } catch (IOException ex) {
                Logger.getLogger(ReadEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     @FXML
    void update(ActionEvent event) {
       if (table.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        event selectedEvent = table.getSelectionModel().getSelectedItem();
        // Charger la vue de modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEvent.fxml"));
            Parent root = (Parent) loader.load();
            ModifierEventController controller = loader.getController();
            controller.setEvent(selectedEvent);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ReadEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       }
    }
    
    
     @FXML
    void delete(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        event selectedEvent = table.getSelectionModel().getSelectedItem();
        EventService es = new EventService();
        es.delete(selectedEvent);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("Suppression avec succès !");
        alert.showAndWait();
        
          }
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
    void Consulter_reservaion(ActionEvent event) {
        try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ReservationCRUD.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                  


     
            } catch (IOException ex) {
                Logger.getLogger(ReadEventController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
     @FXML
    void Contrat(ActionEvent event) {
        
        
if (table.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        event selectedEvent = table.getSelectionModel().getSelectedItem();
        
           Document document = new Document();
         try {
            // Créer un écrivain PDF
            PdfWriter.getInstance(document, new FileOutputStream("..\\TrottirentPro\\src\\DocumentContrat\\"+selectedEvent.getNomEvent()+"Contrat.pdf"));

            // Ouvrir le document
            document.open();
            Font titleFont = FontFactory.getFont("Loki Cola", 24, Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph(selectedEvent.getNomEvent()+"_Contrat", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.add(new Chunk(new LineSeparator(0.1f, 100, BaseColor.BLACK, Element.ALIGN_CENTER, -2f)));
            document.add(title);
            // Ajouter du texte au document
//
//            document.add(new Paragraph("\n\n\n \n\n\n\n"));
//            
//            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(selectedEvent.getPhotoE());
//            image.setAbsolutePosition(100, 500); // Positionner l'image à 100 points du bord gauche et 500 points du bas du document
//            image.scaleToFit(400, 400);
//            document.add(image);
//            
//             document.add(new Paragraph("\n\n\n \n\n\n\n\n\n\n\n \n\n\n\n\n"));
// Add the image to the document
com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(selectedEvent.getPhotoE());
image.setAbsolutePosition(IMAGE_X, IMAGE_Y);
image.scaleToFit(IMAGE_WIDTH, IMAGE_HEIGHT);
document.add(image);

            // Ajouter une image au document
           
            document.add(new Paragraph("Description : "+selectedEvent.getDescription()));
            document.add(new Paragraph("Localisation : "+selectedEvent.getLocalisation()));
            document.add(new Paragraph("Heure : "+selectedEvent.getHeureEvent()));
            document.add(new Paragraph("Prix du ticket : "+selectedEvent.getPrix()));
            document.add(new Paragraph("Date debut : "+selectedEvent.getDateDebut()+" Date Fin : "+selectedEvent.getDateFin()));



            // Fermer le document
            document.close();

            // Ouvrir le document PDF avec le lecteur de PDF par défaut
            Desktop.getDesktop().open(new File("..\\TrottirentPro\\src\\DocumentContrat\\"+selectedEvent.getNomEvent()+"Contrat.pdf"));
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(ReadEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
       }
    }
    
    
    
}
   


