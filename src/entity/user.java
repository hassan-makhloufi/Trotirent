/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author sassi
 */
public class User {
    private int idUser;
    private String nomUser;
    private String prenomUser;
    private String pwd;
    private String email;
    private int numTel;
    private String typeUser;
    private String photo;

    public User(int idUser) {
        this.idUser = idUser;
    }

    public User(int idUser, String nomUser) {
        this.idUser = idUser;
        this.nomUser = nomUser;
    }

    public User(int idUser, String nomUser, String prenomUser) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
    }

    public User(int idUser, String nomUser, String prenomUser, String pwd, String email, int numTel, String typeUser, String photo) {
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.pwd = pwd;
        this.email = email;
        this.numTel = numTel;
        this.typeUser = typeUser;
        this.photo = photo;
    }

    public User(String nomUser, String prenomUser, String pwd, String email, int numTel, String typeUser, String photo) {
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.pwd = pwd;
        this.email = email;
        this.numTel = numTel;
        this.typeUser = typeUser;
        this.photo = photo;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }

    public int getNumTel() {
        return numTel;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public String getPhoto() {
        return photo;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", nomUser=" + nomUser + ", prenomUser=" + prenomUser + ", pwd=" + pwd + ", email=" + email + ", numTel=" + numTel + ", typeUser=" + typeUser + ", photo=" + photo + '}';
    }
    
    
    
    
}