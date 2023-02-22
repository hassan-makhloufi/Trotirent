    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Admin
 */
public class user {

    private int id;
    private String password;
    private String email;
    private String roles;
    private boolean isVerfied;
    private String firstname;
    private String lastname;
    private int phonenumber;
    private String profile_picture;
    private boolean isbanned;
    private String country_code;

    public user() {
    }

    public user(int id, String password, String email, String roles, boolean isVerfied, String firstname, String lastname, int phonenumber, String profile_picture, boolean isbanned, String country_code) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.isVerfied = isVerfied;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.profile_picture = profile_picture;
        this.isbanned = isbanned;
        this.country_code = country_code;
    }

    public user(String password, String email, String roles, boolean isVerfied, String firstname, String lastname, int phonenumber, String profile_picture, boolean isbanned, String country_code) {
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.isVerfied = isVerfied;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.profile_picture = profile_picture;
        this.isbanned = isbanned;
        this.country_code = country_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isIsVerfied() {
        return isVerfied;
    }

    public void setIsVerfied(boolean isVerfied) {
        this.isVerfied = isVerfied;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public boolean isIsbanned() {
        return isbanned;
    }

    public void setIsbanned(boolean isbanned) {
        this.isbanned = isbanned;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

}