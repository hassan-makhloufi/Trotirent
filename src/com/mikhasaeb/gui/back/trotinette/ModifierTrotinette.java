package com.mikhasaeb.gui.back.trotinette;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Categorie;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.gui.back.AccueilBack;
import com.mikhasaeb.services.CategorieService;
import com.mikhasaeb.services.TrotinetteService;
import com.mikhasaeb.utils.AlertUtils;

import java.util.ArrayList;

public class ModifierTrotinette extends Form {


    Trotinette currentTrotinette;

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

    public ModifierTrotinette(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentTrotinette = AfficherToutTrotinette.currentTrotinette;

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


        marqueTF.setText(currentTrotinette.getMarque());
        energieTF.setText(currentTrotinette.getEnergie());
        autonomieTF.setText(String.valueOf(currentTrotinette.getAutonomie()));
        prixTF.setText(String.valueOf(currentTrotinette.getPrix()));
        descriptionTF.setText(currentTrotinette.getDescription());

/*        categoriePC.getPicker().setSelectedString(currentTrotinette.getCategorie().getCategorie());
        selectedCategorie = currentTrotinette.getCategorie();
*/

        manageButton = new Button("Modifier");
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
            if (controleDeSaisie()) {
                int responseCode = TrotinetteService.getInstance().edit(
                        new Trotinette(
                            


                 
                              marqueTF.getText(),
                                energieTF.getText(),
                                (int) Float.parseFloat(autonomieTF.getText()),
                                (int) Float.parseFloat(prixTF.getText()),
                                descriptionTF.getText(),
                             selectedCategorie.getId()
                                

                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification("Trotinette modifié avec succes");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de trotinette. Code d'erreur : " + responseCode, new Command("Ok"));
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