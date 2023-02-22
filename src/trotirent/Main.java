/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trotirent;

import entity.user;
import interfaces.userInterface;
import services.userService;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          userInterface us = new userService();
        user newUser = new user();
       /*newUser.setPassword("password123");
        newUser.setEmail("example@example.com");
        newUser.setRoles("user");
        newUser.setIsVerfied(true);
        newUser.setFirstname("John");
        newUser.setLastname("Doe");
        newUser.setPhonenumber(1234567890);
        newUser.setProfile_picture("profile_pic.jpg");
        newUser.setIsbanned(false);
        newUser.setCountry_code("TN");

        us.createuser(newUser);*/
        us.getalluser();


    }

}
