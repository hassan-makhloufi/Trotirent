package com.mikhasaeb.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mikhasaeb.entities.Panier;
import com.mikhasaeb.entities.Trotinette;
import com.mikhasaeb.entities.User;
import com.mikhasaeb.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanierService {

    public static PanierService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Panier> listPaniers;


    private PanierService() {
        cr = new ConnectionRequest();
    }

    public static PanierService getInstance() {
        if (instance == null) {
            instance = new PanierService();
        }
        return instance;
    }

    public ArrayList<Panier> getAll() {
        listPaniers = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/panier");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listPaniers = getList();
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

        return listPaniers;
    }

    private ArrayList<Panier> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");
            System.out.println("com.mikhasaeb.services.PanierService.getList() " + list);
            for (Map<String, Object> obj : list) {
                Panier panier = new Panier(
                        (int) Float.parseFloat(obj.get("idPanier").toString()),
                        makeTrotinette((Map<String, Object>) obj.get("trotinette")),
                        makeUser((Map<String, Object>) obj.get("user")),
                        (String) obj.get("nom"),
                        (int) Float.parseFloat(obj.get("prix").toString()),
                        (int) Float.parseFloat(obj.get("qty").toString())

                );
                

                listPaniers.add(panier);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listPaniers;
    }

    public Trotinette makeTrotinette(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Trotinette trotinette = new Trotinette();
        trotinette.setId((int) Float.parseFloat(obj.get("idTrot").toString()));
        trotinette.setMarque((String) obj.get("marque"));
        return trotinette;
    }

    public User makeUser(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        User user = new User();
        user.setId((int) Float.parseFloat(obj.get("iduser").toString()));
        user.setEmail((String) obj.get("email"));
        return user;
    }

    public int add(Panier panier) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/panier/add");

        cr.addArgument("trotinette", String.valueOf(panier.getTrotinette().getId()));
        cr.addArgument("user", String.valueOf(panier.getUser().getId()));
        cr.addArgument("nom", panier.getNom());
        cr.addArgument("prix", String.valueOf(panier.getPrix()));
        cr.addArgument("quantite", String.valueOf(panier.getQuantite()));


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

    public int edit(Panier panier) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/panier/edit");
        cr.addArgument("id", String.valueOf(panier.getId()));

        cr.addArgument("trotinette", String.valueOf(panier.getTrotinette().getId()));
        cr.addArgument("user", String.valueOf(panier.getUser().getId()));
        cr.addArgument("nom", panier.getNom());
        cr.addArgument("prix", String.valueOf(panier.getPrix()));
        cr.addArgument("quantite", String.valueOf(panier.getQuantite()));


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

    public int delete(int panierId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/panier/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(panierId));

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
