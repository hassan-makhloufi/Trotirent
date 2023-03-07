/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class event {
    private int idEvent;
    private String NomEvent;
    private LocalDate DateDebut;
    private LocalDate DateFin;
    private String Localisation;
    private String Description;
    private String HeureEvent;
    private Float prix;
    private String PhotoE;

    public event() {
    }

    public event(int idEvent) {
        this.idEvent = idEvent;
    }

    public event(int idEvent, String NomEvent) {
        this.idEvent = idEvent;
        this.NomEvent = NomEvent;
    }

    public event(int idEvent, String NomEvent, LocalDate DateDebut, LocalDate DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE) {
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

    public event(String NomEvent, LocalDate DateDebut, LocalDate DateFin, String Localisation, String Description, String HeureEvent, Float prix, String PhotoE) {
        this.NomEvent = NomEvent;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.Localisation = Localisation;
        this.Description = Description;
        this.HeureEvent = HeureEvent;
        this.prix = prix;
        this.PhotoE = PhotoE;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public String getNomEvent() {
        return NomEvent;
    }

    public LocalDate getDateDebut() {
        return DateDebut;
    }

    public LocalDate getDateFin() {
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

    public void setDateDebut(LocalDate DateDebut) {
        this.DateDebut = DateDebut;
    }

    public void setDateFin(LocalDate DateFin) {
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
        return "event{" + "idEvent=" + idEvent + ", NomEvent=" + NomEvent + ", DateDebut=" + DateDebut + ", DateFin=" + DateFin + ", Localisation=" + Localisation + ", Description=" + Description + ", HeureEvent=" + HeureEvent + ", prix=" + prix + ", PhotoE=" + PhotoE + '}';
    }
    
    
}
