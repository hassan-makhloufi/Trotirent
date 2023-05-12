package com.mikhasaeb.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mikhasaeb.gui.back.AccueilBack;
import com.mikhasaeb.gui.front.AccueilFront;

public class Login extends Form {
    
    Resources theme = UIManager.initFirstTheme("/theme");
    
    public static Form loginForm;

    public Login() {
        super(new BorderLayout());
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {
        Label label = new Label("Welcome to the Login View!");
        label.setUIID("links");
        
        Button btnToFront = new Button("Front");
        btnToFront.addActionListener(action -> {
            new AccueilFront(this).show();
        });
        
        Button btnToBack = new Button("Back");
        btnToBack.addActionListener(action -> {
            new AccueilBack(this).show();
        });
        
        Container buttons = new Container(new FlowLayout(Component.CENTER));
        buttons.add(btnToFront);
        buttons.add(btnToBack);
        
        this.add(BorderLayout.NORTH, label);
        this.add(BorderLayout.CENTER, buttons);
    }
}
