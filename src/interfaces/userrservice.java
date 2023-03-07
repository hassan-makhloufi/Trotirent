
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import entity.user;

/**
 *
 * @author admin
 */
public interface  userrservice  {
    
    //CRUD
    public void createuser(user u);
    public List<user> readusers();
   
  
    
}