/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.gui;


import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mikhasaeb.entities.event;
import com.mikhasaeb.services.ServiceEvent;
import com.mikhasaeb.utils.Statics;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ASUS
 */
public class AjoutEvent extends BaseForm {

    Form current;
    private TextField localisationTF;
    private Button selectLocationBtn;

    public AjoutEvent(BaseForm previous) {
        super("Ajout Event", BoxLayout.y());

        current = this;

        TextField NomEvent = new TextField("", "Nom Event");
        NomEvent.setUIID("TextFieldBlack");
        addStringValue("Nom", NomEvent);

             TextField Localisation = new TextField("", "Localisation");
            Localisation.setUIID("TextFieldBlack");
            addStringValue("Localisation",Localisation);
       

      
       
        TextField Description = new TextField("", "Description");
        Description.setUIID("TextFieldBlack");
        addStringValue("Description", Description);

        TextField Heure = new TextField("", "Heure");
        Heure.setUIID("TextFieldBlack");
        addStringValue("Heure", Heure);

//             TextField PhotoE = new TextField("", "Photo");
//            PhotoE.setUIID("TextFieldBlack");
//            addStringValue("Photo",PhotoE);
        Picker debutPicker = new Picker();
        debutPicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("Date début", debutPicker);

        // Créer un Picker pour la date de fin
        Picker finPicker = new Picker();
        finPicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("Date fin", finPicker);

        // Ajouter le champ de prix
        TextField prixField = new TextField("", "Prix");
        prixField.setUIID("TextFieldBlack");
        addStringValue("Prix", prixField);

        TextField idcat = new TextField("", "idcat");
        idcat.setUIID("TextFieldBlack");
        addStringValue("id cat", idcat);

        Label photoLabel = new Label("Photo");
        Button selectPhoto = new Button("parcourir");
        TextField photoField = new TextField("", "Importer une photo", 10, TextArea.ANY);
        photoField.setEditable(false);

        selectPhoto.addActionListener((evt) -> {
            if (Dialog.show("Photo!", "une annonce avec des  photos est 10 fois plus visible", "app photo", "Gallerie") == false) {
                Display.getInstance().openGallery((e) -> {
                    if (e != null && e.getSource() != null) {

                        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiii\n");

                        String file = (String) e.getSource();
                        System.out.println(file);
                        photoField.setText(file);

                    }
                }, Display.GALLERY_IMAGE);
            } else {
                System.out.println("ici on va accerder à l'appareille photo");
            }
        });

        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        photoContainer.add(photoLabel);
        photoContainer.add(photoField);
        photoContainer.add(selectPhoto);
        add(photoContainer);

//
//  
//        TextField tfImageName = new TextField("", "Image name");
//        Button btnUpload = new Button("Upload");
//        btnUpload.addActionListener((evt) -> {
//            if (!"".equals(tfImageName.getText())) {
//                MultipartRequest cr = new MultipartRequest();
//                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
//                
//                cr.setUrl(Statics.USER_IMAGE_URL);
//                cr.setPost(true);
//                String mime = "image/jpeg";
//                try {
//                    cr.addData("file", filePath, mime);
//                } catch (IOException ex) {
//                    Dialog.show("Error", ex.getMessage(), "OK", null);
//                }
//                cr.setFilename("file", tfImageName.getText() + ".jpg");//any unique name you want
//
//                InfiniteProgress prog = new InfiniteProgress();
//                Dialog dlg = prog.showInifiniteBlocking();
//                cr.setDisposeOnCompletion(dlg);
//                NetworkManager.getInstance().addToQueueAndWait(cr);
//                Dialog.show("Success", "Image uploaded", "OK", null);
//            }else{
//                Dialog.show("Error", "Invalid image name", "Ok", null);
//            }
//        });
//        add(tfImageName);
//        add(btnUpload);
//        
//        getToolbar().addCommandToLeftBar("Back", null, (evt) -> {
//            previous.showBack();
//        });
        // ComboBox<String> categorieComboBox = new ComboBox<>();
        // categorieComboBox.addItem();
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);

        btnAjouter.addActionListener((e) -> {

            try {
                if (NomEvent.getText() == "") {
                    Dialog.show("Veuillez Remplir le nom", "", "Annuler", "OK");

                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    // SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
                    float prix = Float.parseFloat(prixField.getText());
                    event ce = new event(String.valueOf(NomEvent.getText()),
                            debutPicker.getDate(),
                            finPicker.getDate(),
                            localisationTF.getText(),
                            String.valueOf(Description.getText()),
                            String.valueOf(Heure.getText()), prix,
                            photoField.getText(),
                            Integer.parseInt(idcat.getText()));
                    ServiceEvent.getInstance().ajouteEvent(ce);

                    iDialog.dispose(); //loading remove

                    //  new ListCategorieevForm(this).show();
                    refreshTheme();//Actualisation
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));

    }

}
