/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


public class categorie {
    private int id;
    private String categorie;

    public categorie() {
    }

    public categorie(int id, String categorie) {
        this.id = id;
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return categorie ;
    }

    public categorie(String categorie) {
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
      
}
