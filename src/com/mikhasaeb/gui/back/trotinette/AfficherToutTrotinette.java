package com.mikhasaeb.gui.back.trotinette;


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
    Button addBtn;


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
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


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
        addBtn.addActionListener(action -> {
            currentTrotinette = null;
            new AjouterTrotinette(this).show();
        });

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

     //   categorieLabel = new Label("Categorie : " + trotinette.getCategorie().);
        categorieLabel.setUIID("labelDefault");


        trotinetteModel.addAll(

                categorieLabel, marqueLabel, energieLabel, autonomieLabel, prixLabel, descriptionLabel
        );

        return trotinetteModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeTrotinetteModel(Trotinette trotinette) {

        Container trotinetteModel = makeModelWithoutButtons(trotinette);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentTrotinette = trotinette;
            new ModifierTrotinette(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce trotinette ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = TrotinetteService.getInstance().delete(trotinette.getCategorie());

                if (responseCode == 200) {
                    currentTrotinette = null;
                    dlg.dispose();
                    trotinetteModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du trotinette. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        trotinetteModel.add(btnsContainer);

        return trotinetteModel;
    }

}