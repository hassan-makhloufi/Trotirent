package com.mikhasaeb.gui.front.panier;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Panier;
import com.mikhasaeb.services.PanierService;

import java.util.ArrayList;

public class AfficherToutPanier extends Form {

    Form previous;

    public static Panier currentPanier = null;
    Button addBtn;

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
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


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
        addBtn.addActionListener(action -> {
            currentPanier = null;
            new AjouterPanier(this).show();
        });

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

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makePanierModel(Panier panier) {

        Container panierModel = makeModelWithoutButtons(panier);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentPanier = panier;
            new ModifierPanier(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce panier ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = PanierService.getInstance().delete(panier.getId());

                if (responseCode == 200) {
                    currentPanier = null;
                    dlg.dispose();
                    panierModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du panier. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        panierModel.add(btnsContainer);

        return panierModel;
    }

}