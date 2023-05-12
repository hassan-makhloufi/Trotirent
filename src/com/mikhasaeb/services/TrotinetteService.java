package com.mikhasaeb.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mikhasaeb.entities.Categorie;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrotinetteService {

    public static TrotinetteService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Trotinette> listTrotinettes;


    private TrotinetteService() {
        cr = new ConnectionRequest();
    }

    public static TrotinetteService getInstance() {
        if (instance == null) {
            instance = new TrotinetteService();
        }
        return instance;
    }

    public ArrayList<Trotinette> getAll() {
        listTrotinettes = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/trotinette");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listTrotinettes = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTrotinettes;
    }

    private ArrayList<Trotinette> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

          for (Map<String, Object> obj : list) {
    Trotinette trotinette = new Trotinette(
        (String) obj.get("marque"),
        (String) obj.get("energie"),
        (int) Float.parseFloat(obj.get("autonomie").toString()),
        (int) Float.parseFloat(obj.get("prix").toString()),
        (String) obj.get("description"),
        (int) Float.parseFloat(obj.get("categorie").toString())
    ); 

    listTrotinettes.add(trotinette);
}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listTrotinettes;
    }

    public Categorie makeCategorie(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Categorie categorie = new Categorie();
        categorie.setId((int) Float.parseFloat(obj.get("id").toString()));
        categorie.setCategorie((String) obj.get("categorie"));
        return categorie;
    }

    public int add(Trotinette trotinette) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/trotinette/add");

        cr.addArgument("marque", trotinette.getMarque());
        cr.addArgument("energie", trotinette.getEnergie());
        cr.addArgument("autonomie", String.valueOf(trotinette.getAutonomie()));
        cr.addArgument("prix", String.valueOf(trotinette.getPrix()));
        cr.addArgument("description", trotinette.getDescription());
                cr.addArgument("categorie", String.valueOf(trotinette.getCategorie()));
 


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int edit(Trotinette trotinette) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/trotinette/edit");
        cr.addArgument("id", String.valueOf(trotinette.getCategorie()));

                 cr.addArgument("categorie", String.valueOf(trotinette.getCategorie()));
        cr.addArgument("marque", trotinette.getMarque());
        cr.addArgument("energie", trotinette.getEnergie());
        cr.addArgument("autonomie", String.valueOf(trotinette.getAutonomie()));
        cr.addArgument("prix", String.valueOf(trotinette.getPrix()));
        cr.addArgument("description", trotinette.getDescription());


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int trotinetteId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/trotinette/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(trotinetteId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
