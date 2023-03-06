/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trottirent;

import entity.User;
import entity.event;
import entity.reservation;
import java.time.LocalDate;
import service.EventService;
import service.ResService;
import utils.DataSource;

/**
 *
 * @author ASUS
 */
public class Trottirent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         DataSource ds1 = DataSource.getInstance();
//        System.out.println(ds1);
            EventService ES=new EventService();

       // event e1=new event("Ziara", LocalDate.of(2023, 04, 12),
          //      LocalDate.of(2023, 04, 14), "Sousse", "jolie",
              //  "14H", 3.5f, "hella.png");
       // ES.insert(e1);
//         event e2=new event(4,"7adhra", LocalDate.of(2023, 04, 12),
//                LocalDate.of(2023, 04, 14), "Sousse", "jolie",
//                "14H", 3.5f, "hella.png");
         event e2=new event(2,"7adhra", LocalDate.of(2023, 04, 12),
                LocalDate.of(2023, 04, 14), "Sousse", "jolie",
                "14H", 3.5f, "hella.png");
        // ES.insert(e2);
         //ES.update(e2);
        ES.readAll().forEach(System.out::println);
        
        User u1=new User(1, "Ali", "sasi", "aaa", "aaa", 1230, "aaa", "aa");
        ResService RS=new ResService();
        reservation r1=new reservation(3,e2, u1);
       // RS.delete(r1);
        
        //RS.insert(r1);
        RS.readAll().forEach(System.out::println);
        System.out.println("---------------------------------");
        
       // ES.recherche("sousse").forEach(System.out::println);
        System.out.println(ES.CountEvent()) ;

    }
    
}
