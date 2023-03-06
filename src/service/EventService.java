/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import entity.event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;
 
/**
 *
 * @author ASUS
 */
public class EventService implements IEvent<event>{
     private Connection conn;

    public EventService() {
        conn=DataSource.getInstance().getCnx();
    }
     

    @Override
    public void insert(event e) {
        String requete="insert into event (NomEvent,DateDebut,DateFin,Localisation,Description,HeureEvent,prix,PhotoE) values (?,?,?,?,?,?,?,?)";
   try {
      PreparedStatement pst=conn.prepareStatement(requete);
      pst.setString(1, e.getNomEvent());
      pst.setDate(2, java.sql.Date.valueOf(e.getDateDebut()));
      pst.setDate(3, java.sql.Date.valueOf(e.getDateFin()));
      pst.setString(4, e.getLocalisation());
      pst.setString(5, e.getDescription());
      pst.setString(6, e.getHeureEvent());
      pst.setFloat(7, e.getPrix());
      pst.setString(8,e.getPhotoE() );
      pst.executeUpdate();
      System.out.println("Ajout effectué avec succès");
   } catch (SQLException ex) {
      System.out.println("Erreur d'ajout");
      Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
   }
        
    }

    @Override
    public void delete(event e) {
          String requete="delete from event where IdEvent = "+e.getIdEvent();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(event e) {
         String requete="update event set NomEvent=?, DateDebut=?, DateFin=?, Localisation=?, Description=?, HeureEvent=?, prix=?, photoE=? where IdEvent=?";
   try {
            PreparedStatement pst=conn.prepareStatement(requete);
                pst.setString(1, e.getNomEvent());
                pst.setDate(2, java.sql.Date.valueOf(e.getDateDebut()));
                pst.setDate(3, java.sql.Date.valueOf(e.getDateFin()));
                pst.setString(4, e.getLocalisation());
                pst.setString(5, e.getDescription());
                pst.setString(6, e.getHeureEvent());
                pst.setFloat(7, e.getPrix());
                pst.setString(8,e.getPhotoE() );
                pst.setInt(9, e.getIdEvent());
                pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<event> readAll() {
         String requete ="select * from event";
    List<event> list=new ArrayList<>();
    try {
        Statement st=conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        while(rs.next()){
            event e=new event(rs.getInt("IdEvent"), 
                    rs.getString("NomEvent"), 
                    rs.getDate("DateDebut").toLocalDate(), 
                    rs.getDate("DateFin").toLocalDate(), 
                    rs.getString("localisation"), 
                    rs.getString("Description"), 
                    rs.getString("HeureEvent"), 
                    rs.getFloat("prix"),
                    rs.getString("photoE"));
            list.add(e);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }

    @Override
    public event readById(int idEvent) {
         String requete ="select * from event where idEvent="+idEvent;
    event e = null;
    try {
        Statement st=conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        if (rs.next()) {
          e=new event(rs.getInt("IdEvent"), 
                    rs.getString("NomEvent"), 
                    rs.getDate("DateDebut").toLocalDate(), 
                    rs.getDate("DateFin").toLocalDate(), 
                    rs.getString("localisation"), 
                    rs.getString("Description"), 
                    rs.getString("HeureEvent"), 
                    rs.getFloat("prix"),
                    rs.getString("photoE"));        
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return e;
    }

    @Override
    public ObservableList<event> recherche(String charac) {
        String requete = "SELECT * FROM event WHERE NomEvent LIKE ? OR Localisation LIKE ? OR Description LIKE ? OR HeureEvent LIKE ?";
    ObservableList<event> list = FXCollections.observableArrayList();
    try {
        PreparedStatement ps = conn.prepareStatement(requete);
        ps.setString(1, "%" + charac + "%");
        ps.setString(2, "%" + charac + "%");
        ps.setString(3, "%" + charac + "%");
        ps.setString(4, "%" + charac + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            event e = new event(rs.getInt("IdEvent"),
                    rs.getString("NomEvent"),
                    rs.getDate("DateDebut").toLocalDate(),
                    rs.getDate("DateFin").toLocalDate(),
                    rs.getString("localisation"),
                    rs.getString("Description"),
                    rs.getString("HeureEvent"),
                    rs.getFloat("prix"),
                    rs.getString("photoE"));
            list.add(e);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

    @Override
    public int CountEvent() {
       int count = 0;
    String query = "SELECT COUNT(*) FROM event";
    try (Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return count;
    }
}

            


    
    

