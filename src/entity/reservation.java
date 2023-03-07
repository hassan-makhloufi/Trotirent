/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ASUS
 */
public class reservation {
    private int idRes;
    private event idEvent;
    private user idUser;

    public reservation(int idRes) {
        this.idRes = idRes;
    }

    public reservation(int idRes, event idEvent, user idUser) {
        this.idRes = idRes;
        this.idEvent = idEvent;
        this.idUser = idUser;
    }

    public reservation(event idEvent, user idUser) {
        this.idEvent = idEvent;
        this.idUser = idUser;
    }

    public reservation() {
    }
    

    public int getIdRes() {
        return idRes;
    }

    public event getIdEvent() {
        return idEvent;
    }

    public user getIdUser() {
        return idUser;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public void setIdEvent(event idEvent) {
        this.idEvent = idEvent;
    }

    public void setIdUser(user idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "reservation{" + "idRes=" + idRes + ", idEvent=" + idEvent.getIdEvent() +",NomEvent="+ idEvent.getNomEvent()+", idUser=" + idUser.getId()+", NomUser=" + idUser.getFirstname()+ '}';
      //return"hello";
    }

   
    
    
}
