/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.panier;
import entity.trotinete;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.MyListener;
import services.panierservice;
import java.awt.Color;
/**
 * FXML Controller class
 *
 * @author saeb
 */
public class ProduitController implements Initializable {

    @FXML
    private Label idtrot;
    @FXML
    private ImageView img;
    @FXML
    private Label marque;
    @FXML
    private Label energie;
    @FXML
    private Label autonomie;
    @FXML
    private Label prix;
    private trotinete trotinete;
    private MyListener myListener;
    panierservice sp = new panierservice();
    panier p = new panier();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(trotinete p, MyListener myListener) {
        this.trotinete = p;
        this.myListener = myListener;
        marque.setText(p.getMarque());
        String newid=String.valueOf(p.getId_trot());  
        idtrot.setText(newid);
        energie.setText(p.getEnergie());
        autonomie.setText(String.valueOf(p.getAutonomie())+"km");
        prix.setText(String.valueOf(p.getPrix())+" TND");
        Image image = new Image(getClass().getResourceAsStream("/img/1.png"));
        img.setImage(image);
        this.p= new panier(1,Integer.parseInt(idtrot.getText()),marque.getText(),p.getPrix(),1);
    }

    @FXML
    private void ajouter(ActionEvent event) {
        sp.Ajouter(this.p);  
    }

    @FXML
    private void code(ActionEvent event) {
            qrcode(this.trotinete);
    }
    public void qrcode(trotinete e){
         
    try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String Information = "nom : "+e.getMarque()+"\n"+"description de l'évènement : "+e.getDescription()+"\n"+"Energie : "+e.getEnergie()+"\n"+"Autonomie : "+e.getAutonomie()+"\n"+"Prix : "+e.getPrix();
            int width = 300;
            int height = 300;
            
            BufferedImage bufferedImage = null; 
            BitMatrix byteMatrix = qrCodeWriter.encode(Information, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
            System.out.println("Success...");
            

          //  ImageView qrc = new ImageView();
            img.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            // TODO
        } catch (WriterException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
}
