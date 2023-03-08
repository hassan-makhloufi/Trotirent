/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entities.categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyConnection;


public class servicecategorie implements ICService <categorie>  {
    
    Connection cn = MyConnection.getTest().getCnx();
    
    public servicecategorie() {
    }
    

    @Override
    public void Supprimer(int id) {
        try {
            String req = "DELETE FROM `categorie` WHERE `id` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }     }

    @Override
    public ResultSet Selectionner() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `categorie`";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;      }

    @Override
    public void Ajouter(categorie t) {
        try {
            System.out.println(t);
            String req = "INSERT INTO `categorie`(`categorie`) VALUES (?)";
            PreparedStatement pst = cn.prepareStatement(req);
            pst.setString(1, t.getCategorie());
            pst.executeUpdate();
            System.out.println("categorie ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void Modifier(String cat,int id) {
        try {
            String req = "UPDATE `categorie` SET  `categorie`='"+cat+"' WHERE `id`="+id;
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }     }
    
}
