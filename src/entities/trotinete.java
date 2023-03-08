/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


public class trotinete {
    private int id_trot; 
    private String marque ; 
    private String energie;
    private int autonomie; 
    private int prix ; 
    private String description;
    private int id_categorie;

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public trotinete(String marque, String energie, int autonomie, int prix, String description, int id_categorie) {
        this.marque = marque;
        this.energie = energie;
        this.autonomie = autonomie;
        this.prix = prix;
        this.description = description;
        this.id_categorie = id_categorie;
    }

    public trotinete(int id_trot, String marque, String energie, int autonomie, int prix, String description, int id_categorie) {
        this.id_trot = id_trot;
        this.marque = marque;
        this.energie = energie;
        this.autonomie = autonomie;
        this.prix = prix;
        this.description = description;
        this.id_categorie = id_categorie;
    }

    public trotinete(String marque, String energie, int autonomie, int prix, String description) {
        this.marque = marque;
        this.energie = energie;
        this.autonomie = autonomie;
        this.prix = prix;
        this.description = description;
    }

    @Override
    public String toString() {
        return "trotinete{" + "marque=" + marque + ", energie=" + energie + ", autonomie=" + autonomie + ", prix=" + prix + ", description=" + description + '}';
    }

    public trotinete() {
    }

    public trotinete(int id_trot, String marque, String energie, int autonomie, int prix, String description) {
        this.id_trot = id_trot;
        this.marque = marque;
        this.energie = energie;
        this.autonomie = autonomie;
        this.prix = prix;
        this.description = description;
    }

    public int getId_trot() {
        return id_trot;
    }

    public void setId_trot(int id_trot) {
        this.id_trot = id_trot;
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
    
}
