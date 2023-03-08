/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.twilio.Twilio;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Commande;
import entities.MailSender;
import entities.panier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import entities.trotinete;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import services.MyListener;
import services.commandeservice;
import services.panierservice;
import services.servicetrotinete;
import static utils.BadWords.checkWords;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TempController implements Initializable {
    

    
    
    public static final String ACCOUNT_SID = "AC87db9f3778dc3e438592482fd0142b10";
    public static final String AUTH_TOKEN = "371b3b327efc4723acff0f289367d95a";
    public static final String TWILIO_NUMBER = "+15074797918";
    @FXML
    private Pane pnafficher;
    servicetrotinete st = new servicetrotinete();
    trotinete tmpt = new trotinete();
    panierservice sp = new panierservice();
    private MyListener myListener;
    @FXML
    private Pane pn_boutique;
    @FXML
    private Pane pn_panier;
    @FXML
    private GridPane grid;
    commandeservice sc = new commandeservice(); 
    String mail ="mikhail.mannai@esprit.tn";
    
    @FXML
    private TableView<panier> display;
    @FXML
    private TableColumn<panier,String> nom;
    @FXML
    private TableColumn<panier ,String> prix;
    @FXML
    private TableColumn<panier ,String> qty;
    
    panier tmpp = null ; 
    @FXML
    private TableColumn<panier,String> edit;
    @FXML
    private TableColumn<panier,String> id;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
    }    

    @FXML
    private void boutique(ActionEvent event) {
        pn_boutique.toFront();
        display2();
    }

    @FXML
    private void panier(ActionEvent event) {
        pn_panier.toFront();
        refresh();
    }
    
    private void setChosenprod(trotinete t) {
        
    }
    
    private void display2(){
        ///////////////////////////////////////////////////////////////
                ObservableList<trotinete>  l2 = FXCollections.observableArrayList();
                ResultSet resultSet2 = st.Selectionner();
                l2.clear(); 
                trotinete pppp = new trotinete("","",10,10,"");
                l2.add(pppp);
                int column =0;
                int row =2;
                if (l2.size() > 0) {
                setChosenprod(l2.get(0));
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(trotinete trotinete) {
                        setChosenprod(trotinete);
                    }
                };
                }
                try {
                    while (resultSet2.next()){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/gui/produit.fxml"));
                        try {
                            AnchorPane anchorPane = fxmlLoader.load();
                            ProduitController itemController = fxmlLoader.getController();
                            int id=resultSet2.getInt("id_trot");
                            String marque =resultSet2.getString("marque");
                            String energie =resultSet2.getString("energie");
                            int autonomie =resultSet2.getInt("autonomie");
                            int prix=resultSet2.getInt("prix");
                            String description =resultSet2.getString("description");
                            trotinete ppppp = new trotinete(id,marque,energie,autonomie,prix,description);
                            itemController.setData(ppppp,myListener);
                            if (column == 5) {
                                column = 0;
                                row++;
                            }
                            grid.add(anchorPane, column++, row); //(child,column,row)
                            //set grid width
                            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            grid.setMaxWidth(Region.USE_PREF_SIZE);
                            //set grid height
                            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            grid.setMaxHeight(Region.USE_PREF_SIZE);
                            GridPane.setMargin(anchorPane, new Insets(10));
                        } catch (IOException ex) {
                            Logger.getLogger(TempController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }   
                } catch (SQLException ex) {
                    Logger.getLogger(TempController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    private void refresh() {
        ObservableList<panier>  l = FXCollections.observableArrayList();
        ResultSet resultSet = sp.Selectionner(1);
        l.clear();   
        try {
            while (resultSet.next()){
                l.add(new  panier(
                        resultSet.getInt("id_panier"),
                        resultSet.getString("nom"),
                        resultSet.getInt("prix"),
                        resultSet.getInt("qty")));
                display.setItems(l);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(TempController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        id.setCellValueFactory(new PropertyValueFactory<>("id_panier"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<panier, String>, TableCell<panier, String>> cellFoctory = (TableColumn<panier, String> param) -> {
            // make cell containing buttons
            final TableCell<panier, String> cell = new TableCell<panier, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
                        FontAwesomeIconView deditIcon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);


                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deditIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpp = display.getSelectionModel().getSelectedItem();
                            sp.Supprimer( tmpp.getId_panier());
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            tmpp = display.getSelectionModel().getSelectedItem();
                            //incqty
                            int id= tmpp.getId_panier();
                            int qty = tmpp.getQty();
                            qty++;
                            sp.Modifier(id, qty);
                        });
                        deditIcon.setOnMouseClicked((event) -> {
                            tmpp = display.getSelectionModel().getSelectedItem();
                            //dicqty
                            int id= tmpp.getId_panier();
                            int qty = tmpp.getQty();
                            if(qty>1){
                                qty--;
                            }
                            sp.Modifier(id, qty);

                        });
                        
                        HBox managebtn = new HBox(deditIcon,deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                       
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
         edit.setCellFactory(cellFoctory);
    }
    int attention=0;
    @FXML
    private void valider(ActionEvent event) {
        ObservableList<panier>  l = FXCollections.observableArrayList();
        ResultSet resultSet = sp.Selectionner(1);
        l.clear();  
        int total = 0;
        String listp = "";
        try {
            while (resultSet.next()){
                listp+=resultSet.getString("nom");
                int a = resultSet.getInt("prix");
                int b = resultSet.getInt("qty");
                total+=a*b;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TempController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String adresse =  tf_adresse.getText(); 
        String message =  tf_message.getText(); 
        Commande t = new Commande(1,total,listp,adresse,tf_message.getText());
        System.out.println(total);
        if(checkWords(message).equals("false")){
                sc.Ajouter(t);
                try {
                    MailSender.sendMail(mail);
                } catch (Exception ex) {
                    Logger.getLogger(TempController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String toPhoneNumber = "+21653051969";
                if (toPhoneNumber == null || toPhoneNumber.trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Worning !! ");
                        alert.setContentText("number not valide ");
                        alert.show();                    return;
                }

                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String messageText = "Merci pour votre commande  " + formatter.format(currentDate);
                Message messages = Message.creator(new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_NUMBER),
                        messageText).create();

                if (messages.getSid() != null) {
                    System.out.println("SMS sent successfully to " + toPhoneNumber + "!");
                } else {
                    System.out.println("Error sending SMS to " + toPhoneNumber + ".");
                }
            }
   
              {
                  attention++;
                  //clean();
                  Alert alert = new Alert(Alert.AlertType.WARNING);
                  alert.setTitle("Worning !! ");
                  alert.setContentText("vous ne pouvez pas ajouter ce message avec ces mots ! ");
                  alert.show();
                  
                  if(attention>2)
                  {
                      System.out.println(attention);
//                          Mail.envoyer(user);
                  }
                  
              }
    }

    @FXML
    private void rfr(ActionEvent event) {
        refresh();
    }
    
}
