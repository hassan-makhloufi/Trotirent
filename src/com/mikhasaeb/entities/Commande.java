package com.mikhasaeb.entities;

import com.mikhasaeb.utils.Statics;

public class Commande implements Comparable<Commande> {

    private int id;
    private User user;
    private int prixTotale;
    private String produits;
    private String adresse;

    public Commande() {
    }

    public Commande(int id, User user, int prixTotale, String produits, String adresse) {
        this.id = id;
        this.user = user;
        this.prixTotale = prixTotale;
        this.produits = produits;
        this.adresse = adresse;
    }

    public Commande(User user, int prixTotale, String produits, String adresse) {
        this.user = user;
        this.prixTotale = prixTotale;
        this.produits = produits;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPrixTotale() {
        return prixTotale;
    }

    public void setPrixTotale(int prixTotale) {
        this.prixTotale = prixTotale;
    }

    public String getProduits() {
        return produits;
    }

    public void setProduits(String produits) {
        this.produits = produits;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public int compareTo(Commande commande) {
        switch (Statics.compareVar) {
            case "User":
                return this.getUser().getEmail().compareTo(commande.getUser().getEmail());
            case "PrixTotale":
                return Integer.compare(this.getPrixTotale(), commande.getPrixTotale());
            case "Produits":
                return this.getProduits().compareTo(commande.getProduits());
            case "Adresse":
                return this.getAdresse().compareTo(commande.getAdresse());

            default:
                return 0;
        }
    }

}