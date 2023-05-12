package com.mikhasaeb.gui.front.categorie;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mikhasaeb.entities.Categorie;
import com.mikhasaeb.services.CategorieService;

import java.util.ArrayList;

public class AfficherToutCategorie extends Form {

    Form previous;

    public static Categorie currentCategorie = null;

    TextField searchTF;
    ArrayList<Component> componentModels;


    public AfficherToutCategorie(Form previous) {
        super("Categories", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {
        ArrayList<Categorie> listCategories = CategorieService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher categorie par Categorie");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Categorie categorie : listCategories) {
                if (categorie.getCategorie().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCategorieModel(categorie);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listCategories.size() > 0) {
            for (Categorie categorie : listCategories) {
                Component model = makeCategorieModel(categorie);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    Label categorieLabel;


    private Container makeModelWithoutButtons(Categorie categorie) {
        Container categorieModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        categorieModel.setUIID("containerRounded");


        categorieLabel = new Label("Categorie : " + categorie.getCategorie());
        categorieLabel.setUIID("labelDefault");


        categorieModel.addAll(

                categorieLabel
        );

        return categorieModel;
    }

    private Component makeCategorieModel(Categorie categorie) {

        return makeModelWithoutButtons(categorie);
    }

}