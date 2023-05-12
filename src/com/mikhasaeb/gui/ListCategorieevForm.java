/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.util.Base64;
import com.mikhasaeb.entities.categorieevent;
import com.mikhasaeb.services.ServiceCategorieEv;
import com.sun.java.accessibility.util.SwingEventMonitor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mikhasaeb.entities.categorieevent;
import com.mikhasaeb.services.ServiceCategorieEv;

/**
 *
 * @author ASUS
 */
public class ListCategorieevForm extends BaseForm{
    
    public ListCategorieevForm(Form previous) {
        setTitle("List categorie");
        setLayout(BoxLayout.y());
        ArrayList<categorieevent> categorieevents = ServiceCategorieEv.getInstance().afficherCategorieEv();

        // Add the list view to the form
        for (categorieevent t : categorieevents) {
            addElement(t);
        }

        // Add a button below the list view
        Button addButton = new Button("Add new category");
        addButton.addActionListener(e-> new AjoutEvent(this).show());
        addComponent(addButton);

        // Add back button to the toolbar
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());


    } 

    private void addElement(categorieevent t) {
  CheckBox cb = new CheckBox(t.getCategorie());
        cb.setEnabled(false);
        
        add(cb); 
    }
}
        
       
       
    
