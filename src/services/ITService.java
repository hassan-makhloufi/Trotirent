/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entity.trotinete;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public interface ITService <T>{
    public void Ajouter(T t);
    public void Supprimer(int id);
    public void Modifier(int id , String marque , String energie , int prix , int autonomie , String descriptoion);
    public ResultSet Selectionner();
    public List<trotinete> afficher();

}
