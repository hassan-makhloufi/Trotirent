/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.User;
import entity.event;
import entity.reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author ASUS
 */
public class ResService implements IReservation<reservation>{
    private Connection conn;

    public ResService() {
        conn=DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(reservation p) {
         String requete="insert into reservation (idEvent,idUser) values (?,?)";
   try {
      PreparedStatement pst=conn.prepareStatement(requete);
      pst.setInt(1, p.getIdEvent().getIdEvent());
      pst.setInt(2, p.getIdUser().getIdUser());
      pst.executeUpdate();
      System.out.println("Ajout effectué avec succès");
   } catch (SQLException ex) {
      System.out.println("Erreur d'ajout");
      Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
   }
        
    }

    @Override
    public void delete(reservation t) {
  String requete="delete from reservation where IdRes = "+t.getIdRes();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
            System.out.println("delete success");
        } catch (SQLException ex) {
            Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public void update(reservation t) {
String requete="update reservation set idEvent=?, idUser=? where IdRes=?";
   try {
            PreparedStatement pst=conn.prepareStatement(requete);
                pst.setInt(1,t.getIdEvent().getIdEvent() );
                pst.setInt(2,t.getIdUser().getIdUser() );
                pst.setInt(3,t.getIdRes() );
                
                pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public List<reservation> readAll() {
 String requete ="SELECT r.idRes, e.idEvent, e.NomEvent, u.idUser, u.NomUser, u.PrenomUser \n" +
    "FROM reservation r \n" +
    "JOIN event e ON r.idEvent=e.idEvent \n" +
    "JOIN user u ON r.idUser=u.idUser;";
   List<reservation> list =new ArrayList<>();
   try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            int idRes = rs.getInt("idRes");
            int idEvent = rs.getInt("idEvent");
            String nomEvent = rs.getString("nomEvent");
            
            event evt = new event(idEvent, nomEvent);
            //event evt=new event(idEvent);
            int idUser = rs.getInt("idUser");
            String nomUser = rs.getString("nomUser");
            String prenomUser = rs.getString("prenomUser");
            User usr = new User(idUser, nomUser, prenomUser);
            //ser usr =new User(idUser);
            reservation r = new reservation(idRes, evt, usr);
            
          
            list.add(r);
            
        }
    } catch (SQLException ex) {
        Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
   
   
   
    }

    @Override
    public reservation readById(int id) {
         String requete ="SELECT r.idRes, e.idEvent, e.NomEvent, u.idUser, u.NomUser, u.PrenomUser \n" +
    "FROM reservation r \n" +
    "JOIN event e ON r.idEvent=e.idEvent \n" +
    "JOIN user u ON r.idUser=u.idUser WHERE idRes="+id;
         reservation r=null;
            try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            int idRes = rs.getInt("idRes");
            int idEvent = rs.getInt("idEvent");
            String nomEvent = rs.getString("nomEvent");
            event evt = new event(idEvent, nomEvent);
            int idUser = rs.getInt("idUser");
            String nomUser = rs.getString("nomUser");
            String prenomUser = rs.getString("prenomUser");
            User usr = new User(idUser, nomUser, prenomUser);
            r = new reservation(idRes, evt, usr);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return r;
    }
    
    
    
    public int getTotalReservations() {
    String requete = "SELECT COUNT(*) AS total FROM reservation";
    int totalReservations = 0;
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            totalReservations = rs.getInt("total");
        }
    } catch (SQLException ex) {
        Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return totalReservations;
}
    
 public List<Object[]> getReservationsPerEvent() {
    String requete = "SELECT e.NomEvent, COUNT(r.idRes) AS total FROM reservation r " +
                     "JOIN event e ON r.idEvent=e.idEvent " +
                     "GROUP BY r.idEvent";
    List<Object[]> reservationsPerEvent = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            String nomEvent = rs.getString("NomEvent");
            int totalReservations = rs.getInt("total");
            reservationsPerEvent.add(new Object[] {nomEvent, totalReservations});
        }
    } catch (SQLException ex) {
        Logger.getLogger(ResService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return reservationsPerEvent;
}
        
    }
    
    
