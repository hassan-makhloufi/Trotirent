/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.services;
import com.mikhasaeb.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mikhasaeb.entities.categorieevent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class ServiceCategorieEv {
        //singleton
    public static ServiceCategorieEv instance = null ;
     public boolean resultOK;
     private ConnectionRequest req;
     ArrayList<categorieevent> categorieevents;
    
    //private ConnectionRequest req;
    
    public static ServiceCategorieEv getInstance(){
        if(instance == null)
            instance = new ServiceCategorieEv();
        return instance ;
    }
    
    
    
    public ServiceCategorieEv() {
        req = new ConnectionRequest();
        
    }
  /*  public void ajoutCategorieEv(categorieevent categorie){
        String url =Statics.BASE_URL+"/categorieevent/addCategoriesJSON/new?nomcat="+categorie.getNomCat();
        req.setUrl(url);
        req.addResponseListener((e) ->{
            String str = new String(req.getResponseData());  //stocke les données
            System.err.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req); //execution /une ligne obligatoire
        
    }*/
    public ArrayList<categorieevent> parseCategorie(String jsonText) {
        try {
            categorieevents = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> categorieListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) categorieListJson.get("root");
            for (Map<String, Object> obj : list) {
                categorieevent r = new categorieevent();
                float id = Float.parseFloat(obj.get("id").toString());
                r.setId((int) id);
                r.setCategorie(obj.get("categorie").toString());
          

               categorieevents.add(r);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return categorieevents;
    }

     public ArrayList<categorieevent>afficherCategorieEv(){
    // ArrayList<categorieevent> result =new ArrayList<>();
      String url =Statics.BASE_URL+"/categorie";
      req.setUrl(url);
      req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
         @Override
         public void actionPerformed(NetworkEvent evt) {
            categorieevents = parseCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categorieevents;
    }
    
     }

    
    
    

