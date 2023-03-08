/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entity.trotinete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.connexion;


public class servicetrotinete implements ITService <trotinete>  {
    
    connexion instance = connexion.getInstance();
    Connection cn = instance.getCnx();
    
    public servicetrotinete() {
    }
    
    @Override
    public void Ajouter(trotinete t) {
        try {
            System.out.println(t);
            String req = "INSERT INTO `trotinette`(`marque`, `energie`, `autonomie`, `prix`, `description` ,`id_categorie`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cn.prepareStatement(req);
            pst.setString(1, t.getMarque());
            pst.setString(2, t.getEnergie());
            pst.setInt(3, t.getAutonomie());
            pst.setInt(4, t.getPrix());
            pst.setString(5, t.getDescription());
            pst.setInt(6, t.getId_categorie());
            pst.executeUpdate();
            System.out.println("Article ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void Supprimer(int id) {
        try {
            String req = "DELETE FROM `trotinette` WHERE `id_trot` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }     }

    @Override
    public void Modifier(int id , String marque , String energie , int prix , int autonomie , String descriptoion){
         try {
            String req = "UPDATE `trotinette` SET `marque`='"+marque+"',`energie`='"+energie+"',`autonomie`='"+autonomie+"',`prix`='"+prix+"',`description`='"+descriptoion+"' WHERE `id_trot` ="+id;
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }

    @Override
    public ResultSet Selectionner() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `trotinette`";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;      }

    @Override
       public List<trotinete> afficher() {
       List<trotinete> list = new ArrayList<>();

        try {
            String requete = "SELECT `marque`,`energie`,`autonomie`,`prix`,`description` FROM `trotinette`";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                list.add(new trotinete(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    
}
