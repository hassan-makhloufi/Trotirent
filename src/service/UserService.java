/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author sassi
 */
public class UserService implements IUser<User>{
    private Connection conn;

    public UserService() {
        conn=DataSource.getInstance().getCnx();
    }
    

    @Override
    public void insert(User p) {
         String requete="insert into user(nomUser,prenomUser,pwd,email,numTel,typeUser,photo) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNomUser());
            pst.setString(2, p.getPrenomUser());
            pst.setString(3, p.getPwd());
            pst.setString(4, p.getEmail());
            pst.setInt(5, p.getNumTel());
            pst.setString(6, p.getTypeUser());
            pst.setString(7, p.getPhoto());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    @Override
    public void delete(User t) {
           String requete="delete from user where idUser ="+t.getIdUser();
        try {
           
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User p) {
String requete="update user set nomUser=?, prenomUser=?, pwd=?, email=?, numTel=?, typeUser=? ,photo=? where idUser=?";
   try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNomUser());
            pst.setString(2, p.getPrenomUser());
            pst.setString(3, p.getPwd());
            pst.setString(4, p.getEmail());
            pst.setInt(5, p.getNumTel());
            pst.setString(6, p.getTypeUser());
            pst.setString(7, p.getPhoto());
            pst.setInt(8, p.getIdUser());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public List<User> readAll() {
          String requete="select * from user";
        List<User> list=new ArrayList<>();
        
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                User p=new User(rs.getInt("idUser"),
                        rs.getString("nomUser"),
                        rs.getString("prenomUser"),
                        rs.getString("pwd"),
                        rs.getString("email"),
                        rs.getInt("numTel"),
                        rs.getString("typeUser"),
                        rs.getString("photo"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public User readByID(int idUser) {
String requete ="select * from user where idUser="+idUser;
    User p = null;
    try {
        Statement st=conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        if (rs.next()) {
            p = new User(rs.getInt("idUser"),
                        rs.getString("nomUser"),
                        rs.getString("prenomUser"),
                        rs.getString("pwd"),
                        rs.getString("email"),
                        rs.getInt("numTel"),
                        rs.getString("typeUser"),
                        rs.getString("photo"));}
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return p;
    }
    
    
    public User getUserByEmail(String email) throws SQLException {
    User u1 = null;
    String query = "SELECT * FROM user WHERE email = ?";
    try (Connection connection = DataSource.getInstance().getCnx();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("idUser");
                String nom = resultSet.getString("nomUser");
                String prenom = resultSet.getString("prenomUser");
                int num_tel = resultSet.getInt("numTel");
                String mdp = resultSet.getString("pwd");
                String type = resultSet.getString("typeUser");
                String photo = resultSet.getString("photo");
                u1 = new User(id, nom, prenom, mdp, email, num_tel, type, photo);
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }
    return u1;
}

    @Override
    public List<User> readIdNom() {
          String requete="select idUser,nomUser from user";
        List<User> list=new ArrayList<>();
        
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                User p=new User(rs.getInt("idUser"),rs.getString("nomUser") );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
   }
    
}
