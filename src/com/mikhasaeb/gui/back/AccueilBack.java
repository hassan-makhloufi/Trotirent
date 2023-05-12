package com.mikhasaeb.gui.back;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mikhasaeb.gui.Login;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
    Form previous;
    public static Form accueilForm;

    public AccueilBack(Form previous) {
        super(new BorderLayout());
        this.previous = previous;
        accueilForm = this;
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Espace administrateur"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Tabs tabs = new Tabs();
        tabs.addTab("Trotinettes", FontImage.MATERIAL_DIRECTIONS_BIKE, 5, new com.mikhasaeb.gui.back.trotinette.AfficherToutTrotinette(previous));
        tabs.addTab("Categodzdzries", FontImage.MATERIAL_CATEGORY, 5, new com.mikhasaeb.gui.ListCategorieevForm(previous));
        tabs.addTab("Commandes", FontImage.MATERIAL_DELIVERY_DINING, 5, new com.mikhasaeb.gui.back.commande.AfficherToutCommande(previous));
        tabs.addTab("Paniers", FontImage.MATERIAL_ADD_SHOPPING_CART, 5, new com.mikhasaeb.gui.back.panier.AfficherToutPanier(previous));

        this.add(BorderLayout.CENTER, tabs);
    }
}
