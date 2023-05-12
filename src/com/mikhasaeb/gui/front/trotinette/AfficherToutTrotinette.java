package com.mikhasaeb.gui.front.trotinette;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.services.TrotinetteService;
import com.mikhasaeb.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class AfficherToutTrotinette extends Form {

    Form previous;

    public static Trotinette currentTrotinette = null;

    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public AfficherToutTrotinette(Form previous) {
        super("Trotinettes", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        ArrayList<Trotinette> listTrotinettes = TrotinetteService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Categorie", "Marque", "Energie", "Autonomie", "Prix", "Description").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listTrotinettes);
            for (Trotinette trotinette : listTrotinettes) {
                Component model = makeTrotinetteModel(trotinette);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listTrotinettes.size() > 0) {
            for (Trotinette trotinette : listTrotinettes) {
                Component model = makeTrotinetteModel(trotinette);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    Label categorieLabel, marqueLabel, energieLabel, autonomieLabel, prixLabel, descriptionLabel;


    private Container makeModelWithoutButtons(Trotinette trotinette) {
        Container trotinetteModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        trotinetteModel.setUIID("containerRounded");


        categorieLabel = new Label("Categorie : " + trotinette.getCategorie());
        categorieLabel.setUIID("labelDefault");

        marqueLabel = new Label("Marque : " + trotinette.getMarque());
        marqueLabel.setUIID("labelDefault");

        energieLabel = new Label("Energie : " + trotinette.getEnergie());
        energieLabel.setUIID("labelDefault");

        autonomieLabel = new Label("Autonomie : " + trotinette.getAutonomie());
        autonomieLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + trotinette.getPrix());
        prixLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + trotinette.getDescription());
        descriptionLabel.setUIID("labelDefault");

        categorieLabel = new Label("Categorie : " + trotinette.getCategorie());
        categorieLabel.setUIID("labelDefault");


        trotinetteModel.addAll(

                categorieLabel, marqueLabel, energieLabel, autonomieLabel, prixLabel, descriptionLabel
        );

        return trotinetteModel;
    }

    private Component makeTrotinetteModel(Trotinette trotinette) {

        return makeModelWithoutButtons(trotinette);
    }

}