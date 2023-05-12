package com.mikhasaeb.entities;

public class Panier {

    private int id;
    private Trotinette trotinette;
    private User user;
    private String nom;
    private int prix;
    private int quantite;

    public Panier() {
    }

    public Panier(int id, Trotinette trotinette, User user, String nom, int prix, int quantite) {
        this.id = id;
        this.trotinette = trotinette;
        this.user = user;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Panier(Trotinette trotinette, User user, String nom, int prix, int quantite) {
        this.trotinette = trotinette;
        this.user = user;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trotinette getTrotinette() {
        return trotinette;
    }

    public void setTrotinette(Trotinette trotinette) {
        this.trotinette = trotinette;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


}