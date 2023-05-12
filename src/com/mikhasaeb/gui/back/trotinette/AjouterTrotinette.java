package com.mikhasaeb.gui.back.trotinette;


import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Categorie;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.gui.back.AccueilBack;
import com.mikhasaeb.services.CategorieService;
import com.mikhasaeb.services.TrotinetteService;
import com.mikhasaeb.utils.AlertUtils;
import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;


public class AjouterTrotinette extends Form {


    TextField marqueTF;
    TextField energieTF;
    TextField autonomieTF;
    TextField prixTF;
    TextField descriptionTF;
    Label marqueLabel;
    Label energieLabel;
    Label autonomieLabel;
    Label prixLabel;
    Label descriptionLabel;


    ArrayList<Categorie> listCategories;
    PickerComponent categoriePC;
    Categorie selectedCategorie = null;


    Button manageButton;

    Form previous;

    public AjouterTrotinette(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> AccueilBack.accueilForm.showBack());
    }

    private void addGUIs() {

        String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("Categorie");
        listCategories = CategorieService.getInstance().getAll();
        categorieStrings = new String[listCategories.size()];
        categorieIndex = 0;
        for (Categorie categorie : listCategories) {
            categorieStrings[categorieIndex] = categorie.getCategorie();
            categorieIndex++;
        }
        if (listCategories.size() > 0) {
            categoriePC.getPicker().setStrings(categorieStrings);
            categoriePC.getPicker().addActionListener(l -> selectedCategorie = listCategories.get(categoriePC.getPicker().getSelectedStringIndex()));
        } else {
            categoriePC.getPicker().setStrings("");
        }


        marqueLabel = new Label("Marque : ");
        marqueLabel.setUIID("labelDefault");
        marqueTF = new TextField();
        marqueTF.setHint("Tapez le marque");


        energieLabel = new Label("Energie : ");
        energieLabel.setUIID("labelDefault");
        energieTF = new TextField();
        energieTF.setHint("Tapez le energie");


        autonomieLabel = new Label("Autonomie : ");
        autonomieLabel.setUIID("labelDefault");
        autonomieTF = new TextField();
        autonomieTF.setHint("Tapez le autonomie");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                marqueLabel, marqueTF,
                energieLabel, energieTF,
                autonomieLabel, autonomieTF,
                prixLabel, prixTF,
                descriptionLabel, descriptionTF,
                categoriePC,
                manageButton
        );

        this.addAll(container);
    }


    private void addActions() {

        manageButton.addActionListener(action -> {
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl("http://127.0.0.1:8000/mobile/trotinette/add");
        request.setPost(true);
        request.addRequestHeader("Content-Type", "application/json");

        // Create the object that represents the request body
        Trotinette trotinette = new Trotinette("test", "Electric", 20, 500, "This is my awesome Trotinette!", 3);

        // Convert the object to a JSON string using Gson
        Gson gson = new GsonBuilder().create();
        String requestBody = gson.toJson(trotinette);

  request.addArgument(requestBody, requestBody.getBytes());

        request.addResponseListener(e -> {
            if (request.getResponseCode() == 200) {
                // Success handling here
            } else {
                // Error handling here
            }
        });

        NetworkManager.getInstance().addToQueue(request);
      String url = "http://127.0.0.1:8000/mobile/trotinette/add";
        
        // Create the request body as a string

            
            if (controleDeSaisie()) {
                int responseCode = TrotinetteService.getInstance().add(
                        new Trotinette(


                             
                                marqueTF.getText(),
                                energieTF.getText(),
                                (int) Float.parseFloat(autonomieTF.getText()),
                                (int) Float.parseFloat(prixTF.getText()),
                                descriptionTF.getText(),
                                 3
                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification("Trotinette ajouté avec succes");
                    showBackAndRefresh();
                } else {
                    
                    Dialog.show("Erreur", "Erreur d'ajout de trotinette. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutTrotinette) previous).refresh();
        AccueilBack.accueilForm.showBack();
    }

    private boolean controleDeSaisie() {


        if (marqueTF.getText().equals("")) {
            Dialog.show("Avertissement", "Marque vide", new Command("Ok"));
            return false;
        }


        if (energieTF.getText().equals("")) {
            Dialog.show("Avertissement", "Energie vide", new Command("Ok"));
            return false;
        }


        if (autonomieTF.getText().equals("")) {
            Dialog.show("Avertissement", "Autonomie vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(autonomieTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", autonomieTF.getText() + " n'est pas un nombre valide (autonomie)", new Command("Ok"));
            return false;
        }


        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prix vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un nombre valide (prix)", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (selectedCategorie == null) {
            Dialog.show("Avertissement", "Veuillez choisir un categorie", new Command("Ok"));
            return false;
        }


        return true;
    }
}