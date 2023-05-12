/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.gui;

import com.codename1.components.InfiniteProgress;
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
public class AjoutcategoieevForm extends BaseForm{
    Form current;
    public AjoutcategoieevForm(BaseForm previous) {
        
        
            super("Newsfeed",BoxLayout.y());
            Toolbar tb= new Toolbar(true);
            current =this;
            setToolbar(tb);
           
           
           setTitle("Ajout Categorie Event \n\n\n");
            getContentPane().setScrollVisible(false);
            
            
            
            TextField Nom = new TextField("", "entrer Objet!!");
            Nom.setUIID("TextFieldBlack");
            addStringValue("Objet",Nom);
            
          
            
            Button btnAjouter = new Button("Ajouter");
            addStringValue("",btnAjouter);
            
            btnAjouter.addActionListener((e) -> {
                
                try{
                   if(Nom.getText() == "" ) {
                       Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                       
                   }else{
                       InfiniteProgress ip =new InfiniteProgress();
                       final Dialog iDialog =ip.showInfiniteBlocking();
                      // SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
                       categorieevent ce=new categorieevent(String.valueOf(Nom.getText()));
                       System.out.println("data categorievent == "+ce);
                       ServiceCategorieEv.getInstance().ajoutCategorieEv(ce);
                       
                       iDialog.dispose(); //loading remove
                       
                     //  new ListCategorieevForm(this).show();
                       refreshTheme();//Actualisation
                   }
                    
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            });
            
        
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    
    
    
    }
    

    
    
}
