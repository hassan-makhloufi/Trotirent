/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.entities;

/**
 *
 * @author ASUS
 */
public class categorieevent {
    private int id;
    private String categorie;

    // Constructor
    public categorieevent(int id, String categorie) {
        this.id = id;
        this.categorie = categorie;
    }

    public categorieevent() {
       
    }

    // Getters and Setters
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

