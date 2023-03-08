/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.ResultSet;


public interface ICService  <T>{
    public void Ajouter(T t);
    public void Supprimer(int id);
    public void Modifier(String cat,int id);
    public ResultSet Selectionner();
}
