/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Commande;
import entities.categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import entities.trotinete;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import services.commandeservice;
import services.servicetrotinete;
import services.servicecategorie;



public class BackofficeController implements Initializable {
    
    
    servicetrotinete st = new servicetrotinete();
    servicecategorie sc = new servicecategorie();
    trotinete tmpt = new trotinete();
    @FXML
    private TableColumn<trotinete,String> marque;
    @FXML
    private TableColumn<trotinete,String> energie;
    @FXML
    private TableColumn<trotinete,String> autonomie;
    @FXML
    private TableColumn<trotinete,String> prix;
    @FXML
    private TableColumn<trotinete,String> description;
    @FXML
    private TableColumn<trotinete,String> option;
    @FXML
    private TextField tfmarque;
    @FXML
    private TextField tfenergie;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfautonomie;
    @FXML
    private TextField tfdescription;
    @FXML
    private Pane pnafficher;
    @FXML
    private Pane pnajouter;
    @FXML
    private TableView<trotinete> display;
    private TableColumn<trotinete,String> id;
    @FXML
    private Pane pnmodifier;
    @FXML
    private TextField tfmmarque;
    @FXML
    private TextField tfmenergie;
    @FXML
    private TextField tfmprix;
    @FXML
    private TextField tfmautonomie;
    @FXML
    private TextField tfmdescription;
    @FXML
    private TextField tfid;
    @FXML
    private Pane pn_ajouter_cat;
    @FXML
    private TextField tf_aj_cat;
    @FXML
    private Pane pn_modifier_cat;
    @FXML
    private TextField tf_modif_cat;
    @FXML
    private Pane pnafficher1;
    @FXML
    private TableView<categorie> display1;
    @FXML
    private TableColumn<categorie,String> option1;
    @FXML
    private TableColumn<categorie,String> categorie1;
    @FXML
    private TextField tf_modif_cat_id;
    @FXML
    private ComboBox<categorie> combo_aj;
    @FXML
    private ComboBox<categorie> combo_aj1;
    @FXML
    private AnchorPane main;
    @FXML
    private Pane pn_commande;
    @FXML
    private TableView<Commande> display3;
    @FXML
    private TableColumn<Commande,String> id3;
    @FXML
    private TableColumn<Commande,String> prix3;
    @FXML
    private TableColumn<Commande,String> produit3;
    @FXML
    private TableColumn<Commande,String> adresse3;
    @FXML
    private TableColumn<Commande,String> option3;
    commandeservice cs = new commandeservice();
    Commande tmpc = new Commande();
    @FXML
    private TableColumn<Commande,String> idcommande3;
    @FXML
    private TextField rechereche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher();
        recherche_avance();
    }    

    @FXML
    private void ajouterbtn(ActionEvent event) {
        ObservableList<categorie>  p = FXCollections.observableArrayList();
        ResultSet resultSet = sc.Selectionner();
        p.clear();   
        try {
            while (resultSet.next()){
                p.add(new  categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("categorie")));                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        combo_aj.setItems(p);
        combo_aj.valueProperty().addListener((obs, oldVal, newVal) -> 
        System.out.println(newVal.getCategorie()));
        pnajouter.toFront();
    }

    @FXML
    private void listebtn(ActionEvent event) {
        pnafficher.toFront();
    }

    @FXML
    private void refresh(ActionEvent event) {
        ObservableList<trotinete>  p = FXCollections.observableArrayList();
        ResultSet resultSet = st.Selectionner();
        p.clear();   
        try {
            while (resultSet.next()){
                p.add(new  trotinete(
                        resultSet.getInt("id_trot"),
                        resultSet.getString("marque"),
                        resultSet.getString("energie"),
                        resultSet.getInt("prix"),
                        resultSet.getInt("autonomie"),
                        resultSet.getString("description")));
                display.setItems(p);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        energie.setCellValueFactory(new PropertyValueFactory<>("energie"));
        autonomie.setCellValueFactory(new PropertyValueFactory<>("autonomie"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        //id.setCellValueFactory(new PropertyValueFactory<>("id_trot"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<trotinete, String>, TableCell<trotinete, String>> cellFoctory = (TableColumn<trotinete, String> param) -> {
            // make cell containing buttons
            final TableCell<trotinete, String> cell = new TableCell<trotinete, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);


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
                        
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpt = display.getSelectionModel().getSelectedItem();
                            System.out.println(tmpt.getId_trot());
                            st.Supprimer( tmpt.getId_trot());
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            tmpt = display.getSelectionModel().getSelectedItem();
                            ObservableList<categorie>  p = FXCollections.observableArrayList();
                            ResultSet resultSet = sc.Selectionner();
                            p.clear();   
                            try {
                                while (resultSet.next()){
                                    p.add(new  categorie(
                                            resultSet.getInt("id"),
                                            resultSet.getString("categorie")));                        
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            combo_aj1.setItems(p);
                            combo_aj1.valueProperty().addListener((obs, oldVal, newVal) -> 
                            System.out.println(newVal.getCategorie()));
                            pnmodifier.toFront();
                            tfmmarque.setText(tmpt.getMarque());
                            tfmenergie.setText(tmpt.getEnergie());
                            String str1 = Integer.toString(tmpt.getPrix());
                            tfmprix.setText(str1);
                            String str2 = Integer.toString(tmpt.getAutonomie());
                            tfmautonomie.setText(str2);
                            tfmdescription.setText(tmpt.getDescription());
                            String str3 = Integer.toString(tmpt.getId_trot());
                            tfid.setText(str3);
                        });
                        
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                       
                        setGraphic(managebtn);
                        setText(null);

                    }
                }
            };

            return cell;
        };
         option.setCellFactory(cellFoctory);
  
    }

    @FXML
    private void ajouter(ActionEvent event) {
        categorie tmpcat=combo_aj.getValue();
        String marque = tfmarque.getText();
        String energie = tfenergie.getText();
        String description =tfdescription.getText();
        String prixx = tfprix.getText();
        int prix = Integer.parseInt(prixx);
        String autonomiee = tfautonomie.getText();
        int autonomie = Integer.parseInt(autonomiee);
        trotinete t = new trotinete(marque , energie ,prix , autonomie ,description,tmpcat.getId());
        st.Ajouter(t);
    }

    @FXML
    private void modifier(ActionEvent event) {
        String marque = tfmmarque.getText();
        String energie = tfmenergie.getText();
        String description =tfmdescription.getText();
        String prixx = tfmprix.getText();
        int prix = Integer.parseInt(prixx);
        String autonomiee = tfmautonomie.getText();
        int autonomie = Integer.parseInt(autonomiee);
        String idd = tfid.getText();
        int id = Integer.parseInt(idd);
        st.Modifier(id,marque ,energie ,prix ,autonomie ,description);
        pnafficher.toFront();

        
    }

    @FXML
    private void ajouter2btn(ActionEvent event) {
        pn_ajouter_cat.toFront();
    }

    @FXML
    private void afficher2btn(ActionEvent event) {
        pnafficher1.toFront();
    }

    @FXML
    private void btn_ajouter_cat(ActionEvent event) {
        categorie cat = new categorie(tf_aj_cat.getText());
        sc.Ajouter(cat);
    }

    @FXML
    private void btn_modifier_cat(ActionEvent event) {
        sc.Modifier(tf_modif_cat.getText(),Integer.parseInt(tf_modif_cat_id.getText()));
    }

    @FXML
    private void refresh1(ActionEvent event) {
        ObservableList<categorie>  p = FXCollections.observableArrayList();
        ResultSet resultSet = sc.Selectionner();
        p.clear();   
        try {
            while (resultSet.next()){
                p.add(new  categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("categorie")));
                display1.setItems(p);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        categorie1.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<categorie, String>, TableCell<categorie, String>> cellFoctory = (TableColumn<categorie, String> param) -> {
            // make cell containing buttons
            final TableCell<categorie, String> cell = new TableCell<categorie, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);


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
                        
                        deleteIcon.setOnMouseClicked((event) -> {
                            categorie tmpc = display1.getSelectionModel().getSelectedItem();
                            sc.Supprimer( tmpc.getId());
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            categorie tmpc = display1.getSelectionModel().getSelectedItem();
                            tf_modif_cat_id.setText(Integer.toString(tmpc.getId()));
                            tf_modif_cat.setText(tmpc.getCategorie());
                            pn_modifier_cat.toFront();
                            
                        });
                        
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                       
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
         option1.setCellFactory(cellFoctory);
    }

    @FXML
    private void pdf(ActionEvent event) {
        try {
            genererListetProduitPDF(st, main.getScene().getWindow());
        } catch (DocumentException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void genererListetProduitPDF(servicetrotinete st, Window parentWindow) throws DocumentException, FileNotFoundException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Enregistrer les données");
    File selectedFile = fileChooser.showSaveDialog(parentWindow);
 
    if (selectedFile != null) {
        try {
            // Créer un document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
            document.open();
 
            // Ajouter les éléments de l'interface utilisateur pour le ticket d'achat
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Liste des produits", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10f);
            document.add(title);
 
            com.itextpdf.text.Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph date = new Paragraph("Date: " + LocalDate.now().toString(), regularFont);
            date.setAlignment(Element.ALIGN_LEFT);
            date.setSpacingAfter(10f);
            document.add(date);
 
            Paragraph produits = new Paragraph("Produits:", regularFont);
            produits.setAlignment(Element.ALIGN_LEFT);
            produits.setSpacingAfter(10f);
            document.add(produits);
 
            ArrayList<trotinete> products=(ArrayList<trotinete>)st.afficher();          
       
            
            PdfPTable table = new PdfPTable(5); // 3 colonnes pour Nom, Prix et Quantité
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
 
            // En-tête de table
            table.addCell(new PdfPCell(new Phrase("Marque", regularFont)));
            table.addCell(new PdfPCell(new Phrase("Prix", regularFont)));
            table.addCell(new PdfPCell(new Phrase("Energie", regularFont)));
            table.addCell(new PdfPCell(new Phrase("Autonomie", regularFont)));
            table.addCell(new PdfPCell(new Phrase("Description", regularFont)));

            // Contenu de table
            for (trotinete p : products) {
                System.out.println(p);
                table.addCell(new PdfPCell(new Phrase(p.getMarque(), regularFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getPrix()), regularFont)));
                table.addCell(new PdfPCell(new Phrase(p.getEnergie(), regularFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getAutonomie()), regularFont)));
                table.addCell(new PdfPCell(new Phrase(p.getDescription(), regularFont)));
            }

            document.add(table);
 
            document.close();
        } catch (IOException | DocumentException ex) {
            System.err.println("Erreur lors de l'écriture dans le fichier: " + ex.getMessage());
        }
    } else {
        System.out.println("La sélection de fichier a été annulée");
    }
    }

    @FXML
    private void affichercommande(ActionEvent event) {
        pn_commande.toFront();
    }

    @FXML
    private void refresh3(ActionEvent event) {
        ObservableList<Commande>  c = FXCollections.observableArrayList();
        ResultSet resultSet = cs.Selectionner();
        c.clear();   
        try {
            while (resultSet.next()){
                c.add(new  Commande(
                        resultSet.getInt("id_commande"),
                        resultSet.getInt("id_client"),
                        resultSet.getInt("prix_totale"),
                        resultSet.getString("produits"),
                        resultSet.getString("adresse")));
                display3.setItems(c);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(commandeservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        prix3.setCellValueFactory(new PropertyValueFactory<>("prix_totale"));
        id3.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        produit3.setCellValueFactory(new PropertyValueFactory<>("produits"));
        adresse3.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        idcommande3.setCellValueFactory(new PropertyValueFactory<>("id_commande"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<Commande, String>, TableCell<Commande, String>> cellFoctory = (TableColumn<Commande, String> param) -> {
            // make cell containing buttons
            final TableCell<Commande, String> cell = new TableCell<Commande, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
               
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpc = display3.getSelectionModel().getSelectedItem();
                            cs.Supprimer( tmpc.getId_commande());
                        });
                        
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");                       
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
         option3.setCellFactory(cellFoctory);
    }
    
    public void recherche_avance() {
        servicetrotinete ServL = new servicetrotinete();
        ObservableList<trotinete> listL = FXCollections.observableList(ServL.afficher());
        System.out.println("*****************");
        FilteredList<trotinete> filtereddata = new FilteredList<>(listL, b -> true);
        System.out.println(rechereche.getText());
        rechereche.textProperty().addListener((observable, oldvalue, newValue) -> {
            filtereddata.setPredicate((trotinete trotinete) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercasefilter = newValue.toLowerCase();
                if (trotinete.getMarque().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });
        //System.out.println(filtereddata);
        SortedList<trotinete> sorteddata = new SortedList<>(filtereddata);
        sorteddata.comparatorProperty().bind(display.comparatorProperty());
        display.setItems(filtereddata);
    }
    
    public void afficher(){
        ObservableList<trotinete>  p = FXCollections.observableArrayList();
        ResultSet resultSet = st.Selectionner();
        p.clear();   
        try {
            while (resultSet.next()){
                p.add(new  trotinete(
                        resultSet.getInt("id_trot"),
                        resultSet.getString("marque"),
                        resultSet.getString("energie"),
                        resultSet.getInt("prix"),
                        resultSet.getInt("autonomie"),
                        resultSet.getString("description")));
                display.setItems(p);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        energie.setCellValueFactory(new PropertyValueFactory<>("energie"));
        autonomie.setCellValueFactory(new PropertyValueFactory<>("autonomie"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        //id.setCellValueFactory(new PropertyValueFactory<>("id_trot"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<trotinete, String>, TableCell<trotinete, String>> cellFoctory = (TableColumn<trotinete, String> param) -> {
            // make cell containing buttons
            final TableCell<trotinete, String> cell = new TableCell<trotinete, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);


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
                        
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpt = display.getSelectionModel().getSelectedItem();
                            System.out.println(tmpt.getId_trot());
                            st.Supprimer( tmpt.getId_trot());
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            tmpt = display.getSelectionModel().getSelectedItem();
                            ObservableList<categorie>  p = FXCollections.observableArrayList();
                            ResultSet resultSet = sc.Selectionner();
                            p.clear();   
                            try {
                                while (resultSet.next()){
                                    p.add(new  categorie(
                                            resultSet.getInt("id"),
                                            resultSet.getString("categorie")));                        
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(BackofficeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            combo_aj1.setItems(p);
                            combo_aj1.valueProperty().addListener((obs, oldVal, newVal) -> 
                            System.out.println(newVal.getCategorie()));
                            pnmodifier.toFront();
                            tfmmarque.setText(tmpt.getMarque());
                            tfmenergie.setText(tmpt.getEnergie());
                            String str1 = Integer.toString(tmpt.getPrix());
                            tfmprix.setText(str1);
                            String str2 = Integer.toString(tmpt.getAutonomie());
                            tfmautonomie.setText(str2);
                            tfmdescription.setText(tmpt.getDescription());
                            String str3 = Integer.toString(tmpt.getId_trot());
                            tfid.setText(str3);
                        });
                        
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                       
                        setGraphic(managebtn);
                        setText(null);

                    }
                }
            };

            return cell;
        };
         option.setCellFactory(cellFoctory);
        
    }
    
}
