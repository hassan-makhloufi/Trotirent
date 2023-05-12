package com.mikhasaeb.gui.front.commande;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Commande;
import com.mikhasaeb.entities.User;
import com.mikhasaeb.gui.front.AccueilFront;
import com.mikhasaeb.services.CommandeService;
import com.mikhasaeb.services.UserService;

import java.util.ArrayList;

public class AjouterCommande extends Form {


    TextField prixTotaleTF;
    TextField produitsTF;
    TextField adresseTF;
    Label prixTotaleLabel;
    Label produitsLabel;
    Label adresseLabel;


    ArrayList<User> listUsers;
    PickerComponent userPC;
    User selectedUser = null;


    Button manageButton;

    Form previous;

    public AjouterCommande(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> AccueilFront.accueilForm.showBack());
    }

    private void addGUIs() {

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


        prixTotaleLabel = new Label("PrixTotale : ");
        prixTotaleLabel.setUIID("labelDefault");
        prixTotaleTF = new TextField();
        prixTotaleTF.setHint("Tapez le prixTotale");


        produitsLabel = new Label("Produits : ");
        produitsLabel.setUIID("labelDefault");
        produitsTF = new TextField();
        produitsTF.setHint("Tapez le produits");


        adresseLabel = new Label("Adresse : ");
        adresseLabel.setUIID("labelDefault");
        adresseTF = new TextField();
        adresseTF.setHint("Tapez le adresse");


        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                prixTotaleLabel, prixTotaleTF,
                produitsLabel, produitsTF,
                adresseLabel, adresseTF,
                userPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = CommandeService.getInstance().add(
                        new Commande(


                                selectedUser,
                                (int) Float.parseFloat(prixTotaleTF.getText()),
                                produitsTF.getText(),
                                adresseTF.getText()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Commande ajouté avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutCommande) previous).refresh();
        AccueilFront.accueilForm.showBack();
    }

    private boolean controleDeSaisie() {


        if (prixTotaleTF.getText().equals("")) {
            Dialog.show("Avertissement", "PrixTotale vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTotaleTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTotaleTF.getText() + " n'est pas un nombre valide (prixTotale)", new Command("Ok"));
            return false;
        }


        if (produitsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Produits vide", new Command("Ok"));
            return false;
        }


        if (adresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Adresse vide", new Command("Ok"));
            return false;
        }


        if (selectedUser == null) {
            Dialog.show("Avertissement", "Veuillez choisir un user", new Command("Ok"));
            return false;
        }


        return true;
    }
}