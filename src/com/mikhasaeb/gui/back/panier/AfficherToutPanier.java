package com.mikhasaeb.gui.back.panier;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Panier;
import com.mikhasaeb.services.PanierService;

import java.util.ArrayList;

public class AfficherToutPanier extends Form {

    Form previous;

    public static Panier currentPanier = null;

    TextField searchTF;
    ArrayList<Component> componentModels;


    public AfficherToutPanier(Form previous) {
        super("Paniers", new BoxLayout(BoxLayout.Y_AXIS));
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

        ArrayList<Panier> listPaniers = PanierService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher panier par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Panier panier : listPaniers) {
                if (panier.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makePanierModel(panier);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listPaniers.size() > 0) {
            for (Panier panier : listPaniers) {
                Component model = makePanierModel(panier);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    Label trotinetteLabel, userLabel, nomLabel, prixLabel, quantiteLabel;


    private Container makeModelWithoutButtons(Panier panier) {
        Container panierModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        panierModel.setUIID("containerRounded");


        trotinetteLabel = new Label("Trotinette : " + panier.getTrotinette());
        trotinetteLabel.setUIID("labelDefault");

        userLabel = new Label("User : " + panier.getUser());
        userLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + panier.getNom());
        nomLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + panier.getPrix());
        prixLabel.setUIID("labelDefault");

        quantiteLabel = new Label("Quantite : " + panier.getQuantite());
        quantiteLabel.setUIID("labelDefault");

        trotinetteLabel = new Label("Trotinette : " + panier.getTrotinette().getMarque());
        trotinetteLabel.setUIID("labelDefault");

        userLabel = new Label("User : " + panier.getUser().getEmail());
        userLabel.setUIID("labelDefault");


        panierModel.addAll(

                trotinetteLabel, userLabel, nomLabel, prixLabel, quantiteLabel
        );

        return panierModel;
    }

    private Component makePanierModel(Panier panier) {

        return makeModelWithoutButtons(panier);
    }

}