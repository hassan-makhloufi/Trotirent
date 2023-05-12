 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikhasaeb.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;


public class BaseForm extends Form{
        public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }
    
    
    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
        Image img = res.getImage("ezyra.png");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
      
        
      //  tb.addMaterialCommandToSideMenu("Newsfeed", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
       // tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
       // tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show());
    }

    void addButtonBar(Resources res, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     public Component myImageStyle(Container swipe, Image img,Resources res) {
         
         Container page1 ;
         try {
             int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
             if (img.getHeight() < size) {
                     System.out.println(img);
                     System.out.println(size);
                     img = img.scaledHeight(size);
                 }
                
             
             if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
                 img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
             }
             ScaleImageLabel image = new ScaleImageLabel(img);
             image.setUIID("Container");
             image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
             Label overlay = new Label(" ", "ImageOverlay");
             
             page1
                     = LayeredLayout.encloseIn(
                             image, overlay
                     //              
                     );
         } catch (Exception e) {
             Image imge = res.getImage("ezyra.png");
             int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
             if (imge.getHeight() < size) {
                     
                     imge = imge.scaledHeight(size);
                 }
                
             
             if (imge.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
                 imge = imge.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
             }
             ScaleImageLabel image = new ScaleImageLabel(imge);
             image.setUIID("Container");
             image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
             Label overlay = new Label(" ", "ImageOverlay");
             
             page1
                     = LayeredLayout.encloseIn(
                             image, overlay
                     //              
                     );
         }

        return page1;
        
   
    }


}