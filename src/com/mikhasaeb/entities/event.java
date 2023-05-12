/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.entities;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class event {    
    private int idEvent;
    private String NomEvent;
    private Date DateDebut;
    private Date DateFin;
    private String Localisation;

 
    private String Description;
    private String HeureEvent;
    private Float prix;
    private String PhotoE;
    private int idCat;

    public event(String NomEvent, String Localisation, String Description, String HeureEvent, String PhotoE) {
        this.NomEvent = NomEvent;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
    
        this.PhotoE = PhotoE;
    }

    public event(int idEvent, String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE, int idCat) {
        this.idEvent = idEvent;
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.prix = prix;
        this.PhotoE = PhotoE;
        this.idCat = idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public event() {
    }

    public event(int idEvent) {
        this.idEvent = idEvent;
    }

    public event(int idEvent, String NomEvent) {
        this.idEvent = idEvent;
        this.NomEvent = NomEvent;
    }

    public event(int idEvent, String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE) {
        this.idEvent = idEvent;
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.prix = prix;
        this.PhotoE = PhotoE;
    }

    public event(String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, String PhotoE) {
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.PhotoE = PhotoE;
    }

    public event(String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE) {
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.prix = prix;
        this.PhotoE = PhotoE;
    }

    public event(String NomEvent, Date DateDebut, Date DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE, int idCat) {
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.prix = prix;
        this.PhotoE = PhotoE;
        this.idCat = idCat;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public String getNomEvent() {
        return NomEvent;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public String getDescription() {
        return Description;
    }

    public String getHeureEvent() {
        return HeureEvent;
    }

    public Float getPrix() {
        return prix;
    }

    public String getPhotoE() {
        return PhotoE;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public void setNomEvent(String NomEvent) {
        this.NomEvent = NomEvent;
    }

    public void setDateDebut(Date DateDebut) {
        this.DateDebut = DateDebut;
    }

    public void setDateFin(Date DateFin) {
        this.DateFin = DateFin;
    }

    public void setLocalisation(String Localisation) {
        this.Localisation = Localisation;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setHeureEvent(String HeureEvent) {
        this.HeureEvent = HeureEvent;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public void setPhotoE(String PhotoE) {
        this.PhotoE = PhotoE;
    }

    @Override
    public String toString() {
        return NomEvent;
    }

    
  
    
    
}
