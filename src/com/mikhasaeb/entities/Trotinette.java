package com.mikhasaeb.entities;

import com.mikhasaeb.utils.Statics;

public class Trotinette implements Comparable<Trotinette> {


    private String marque;
    private String energie;
    private int autonomie;
    private int prix;
    private String description;
    private int  categorie;

    public Trotinette() {
    }

    public Trotinette( String marque, String energie, int autonomie, int prix, String description, int categorie) {
     
        this.marque = marque;
        this.energie = energie;
        this.autonomie = autonomie;
        this.prix = prix;
        this.description = description;
        this.categorie = categorie;
    }

   



    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getEnergie() {
        return energie;
    }

    public void setEnergie(String energie) {
        this.energie = energie;
    }

    public int getAutonomie() {
        return autonomie;
    }

    public void setAutonomie(int autonomie) {
        this.autonomie = autonomie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int compareTo(Trotinette trotinette) {
        switch (Statics.compareVar) {
            case "Categorie":
                return Integer.compare(this.getCategorie(), trotinette.getCategorie());
            case "Marque":
                return this.getMarque().compareTo(trotinette.getMarque());
            case "Energie":
                return this.getEnergie().compareTo(trotinette.getEnergie());
            case "Autonomie":
                return Integer.compare(this.getAutonomie(), trotinette.getAutonomie());
            case "Prix":
                return Integer.compare(this.getPrix(), trotinette.getPrix());
            case "Description":
                return this.getDescription().compareTo(trotinette.getDescription());

            default:
                return 0;
        }
    }


}