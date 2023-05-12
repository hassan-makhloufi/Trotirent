package com.mikhasaeb.gui.front.panier;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Panier;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.entities.User;
import com.mikhasaeb.gui.front.AccueilFront;
import com.mikhasaeb.services.PanierService;
import com.mikhasaeb.services.TrotinetteService;
import com.mikhasaeb.services.UserService;

import java.util.ArrayList;

public class ModifierPanier extends Form {


    Panier currentPanier;

    TextField nomTF;
    TextField prixTF;
    TextField quantiteTF;
    Label nomLabel;
    Label prixLabel;
    Label quantiteLabel;


    ArrayList<Trotinette> listTrotinettes;
    PickerComponent trotinettePC;
    Trotinette selectedTrotinette = null;
    ArrayList<User> listUsers;
    PickerComponent userPC;
    User selectedUser = null;


    Button manageButton;

    Form previous;

    public ModifierPanier(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentPanier = AfficherToutPanier.currentPanier;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> AccueilFront.accueilForm.showBack());
    }

    private void addGUIs() {

        String[] trotinetteStrings;
        int trotinetteIndex;
        trotinettePC = PickerComponent.createStrings("").label("Trotinette");
        listTrotinettes = TrotinetteService.getInstance().getAll();
        trotinetteStrings = new String[listTrotinettes.size()];
        trotinetteIndex = 0;
        for (Trotinette trotinette : listTrotinettes) {
            trotinetteStrings[trotinetteIndex] = trotinette.getMarque();
            trotinetteIndex++;
        }
        if (listTrotinettes.size() > 0) {
            trotinettePC.getPicker().setStrings(trotinetteStrings);
            trotinettePC.getPicker().addActionListener(l -> selectedTrotinette = listTrotinettes.get(trotinettePC.getPicker().getSelectedStringIndex()));
        } else {
            trotinettePC.getPicker().setStrings("");
        }

        String[] userStrings;
        int userIndex;
        userPC = PickerComponent.createStrings("").label("User");
        listUsers = UserService.getInstance().getAll();
        userStrings = new String[listUsers.size()];
        userIndex = 0;
        for (User user : listUsers) {
            userStrings[userIndex] = user.getEmail();
            userIndex++;
        }
        if (listUsers.size() > 0) {
            userPC.getPicker().setStrings(userStrings);
            userPC.getPicker().addActionListener(l -> selectedUser = listUsers.get(userPC.getPicker().getSelectedStringIndex()));
        } else {
            userPC.getPicker().setStrings("");
        }


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        quantiteLabel = new Label("Quantite : ");
        quantiteLabel.setUIID("labelDefault");
        quantiteTF = new TextField();
        quantiteTF.setHint("Tapez le quantite");


        nomTF.setText(currentPanier.getNom());
        prixTF.setText(String.valueOf(currentPanier.getPrix()));
        quantiteTF.setText(String.valueOf(currentPanier.getQuantite()));

        trotinettePC.getPicker().setSelectedString(currentPanier.getTrotinette().getMarque());
        selectedTrotinette = currentPanier.getTrotinette();
        userPC.getPicker().setSelectedString(currentPanier.getUser().getEmail());
        selectedUser = currentPanier.getUser();


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                nomLabel, nomTF,
                prixLabel, prixTF,
                quantiteLabel, quantiteTF,
                trotinettePC, userPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = PanierService.getInstance().edit(
                        new Panier(
                                currentPanier.getId(),


                                selectedTrotinette,
                                selectedUser,
                                nomTF.getText(),
                                (int) Float.parseFloat(prixTF.getText()),
                                (int) Float.parseFloat(quantiteTF.getText())

                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Panier modifié avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de panier. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutPanier) previous).refresh();
        AccueilFront.accueilForm.showBack();
    }

    private boolean controleDeSaisie() {


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
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


        if (quantiteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Quantite vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(quantiteTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", quantiteTF.getText() + " n'est pas un nombre valide (quantite)", new Command("Ok"));
            return false;
        }


        if (selectedTrotinette == null) {
            Dialog.show("Avertissement", "Veuillez choisir un trotinette", new Command("Ok"));
            return false;
        }

        if (selectedUser == null) {
            Dialog.show("Avertissement", "Veuillez choisir un user", new Command("Ok"));
            return false;
        }


        return true;
    }
}