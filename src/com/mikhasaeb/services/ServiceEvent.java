/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.services;
import com.mikhasaeb.utils.Statics;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mikhasaeb.entities.event;
import com.mikhasaeb.entities.event;
import static com.mikhasaeb.services.ServiceCategorieEv.instance;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class ServiceEvent {
            //singleton
    public static ServiceEvent instance = null ;
     public boolean resultOK;
     private ConnectionRequest req;
     ArrayList<event> events;
    
     public ServiceEvent() {
        req = new ConnectionRequest();
        
    }
    //private ConnectionRequest req;
     
        public void ajouteEvent(event event){
        String url =Statics.BASE_URL+"/event/addEventJSON/new?nomevent="+event.getNomEvent()+"&datedebut="+event.getDateDebut()+"&datefin="+event.getDateFin()+"&localisation="+event.getLocalisation()+"&description="+event.getDescription()+"&heure="+event.getHeureEvent()+"&prix="+event.getPrix()+"&photoe="+event.getPhotoE()+"&idcat="+event.getIdCat();
        req.setUrl(url);
        req.addResponseListener((e) ->{
            String str = new String(req.getResponseData());  //stocke les données
            System.err.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req); //execution /une ligne obligatoire
        
    }
    
    public static ServiceEvent getInstance(){
        if(instance == null)
            instance = new ServiceEvent();
        return instance ;
    }
    
    
            public void ModifierEvent(event event){
        String url =Statics.BASE_URL+"/event/updateEventJSON/a/"+event.getIdEvent()+"?nomevent="+event.getNomEvent()+"&datedebut="+event.getDateDebut()+"&datefin="+event.getDateFin()+"&localisation="+event.getLocalisation()+"&description="+event.getDescription()+"&heure="+event.getHeureEvent()+"&prix="+event.getPrix()+"&photoe="+event.getPhotoE()+"&idcat=48";
        req.setUrl(url);
        req.addResponseListener((e) ->{
            String str = new String(req.getResponseData());  //stocke les données
            System.err.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req); //execution /une ligne obligatoire
        
    }
   
    
     public ArrayList<event> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) eventListJson.get("root");
            for (Map<String, Object> obj : list) {
                event r = new event();
                float id = Float.parseFloat(obj.get("idevent").toString());
                r.setIdEvent((int) id);
                r.setNomEvent(obj.get("nomevent").toString());
                r.setPhotoE(obj.get("photoe").toString());
          

               events.add(r);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }

     public ArrayList<event>afficherEv(){
    // ArrayList<categorieevent> result =new ArrayList<>();
      String url =Statics.BASE_URL+"/event/allEvents/test";
      req.setUrl(url);
      req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
         @Override
         public void actionPerformed(NetworkEvent evt) {
            events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
     


public boolean suppEvent(event t)
    {

         String url = Statics.BASE_URL + "/event/deleteeventJSON/"+t.getIdEvent();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);             }
         });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;



    }
         
public void modifierStation(int idEvent,String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE, int idCat) {
    String url = Statics.BASE_URL + "event/updateEventJSON/a/" + idEvent + "?&nomevent=" + NomEvent;
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setHttpMethod("PUT");
    req.addResponseListener((NetworkEvent evt) -> {
        if (req.getResponseCode() == 200) {
            JSONParser parser = new JSONParser();
            try {
                Map<String, Object> response = parser.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                System.out.println(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
}


}
