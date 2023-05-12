package com.mikhasaeb.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mikhasaeb.entities.Commande;
import com.mikhasaeb.entities.User;
import com.mikhasaeb.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandeService {

    public static CommandeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commande> listCommandes;


    private CommandeService() {
        cr = new ConnectionRequest();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }

    public ArrayList<Commande> getAll() {
        listCommandes = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommandes = getList();
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

        return listCommandes;
    }

    private ArrayList<Commande> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commande commande = new Commande(
                              makeUser((Map<String, Object>) obj.get("iduser")),
                                (int) Float.parseFloat(obj.get("prixTotale").toString()),
                                (String) obj.get("produits"),
                        (String) obj.get("adresse")

                );

                listCommandes.add(commande);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCommandes;
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

    public int add(Commande commande) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/commande/add");

        cr.addArgument("user", String.valueOf(commande.getUser().getId()));
        cr.addArgument("prixTotale", String.valueOf(commande.getPrixTotale()));
        cr.addArgument("produits", commande.getProduits());
        cr.addArgument("adresse", commande.getAdresse());


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

    public int edit(Commande commande) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/commande/edit");
        cr.addArgument("id", String.valueOf(commande.getId()));

        cr.addArgument("user", String.valueOf(commande.getUser().getId()));
        cr.addArgument("prixTotale", String.valueOf(commande.getPrixTotale()));
        cr.addArgument("produits", commande.getProduits());
        cr.addArgument("adresse", commande.getAdresse());


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

    public int delete(int commandeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commandeId));

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
