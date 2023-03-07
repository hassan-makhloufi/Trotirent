/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entity.user;
import interfaces.userInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import services.userService;

/**
 *
 * @author Souhail
 */
public class UsersListController1 implements Initializable {

    @FXML
    private ListView<user> usersList;

    public void loadUsers() {
        userInterface fn = new userService();
        List<user> li = new ArrayList<user>();

        ArrayList<user> userlist = (ArrayList<user>) fn.getalluser();
        ObservableList<user> userslist = FXCollections.observableArrayList(fn.getalluser());
        System.out.println(userslist.toString());

        usersList.setItems(userslist);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        userInterface fn = new userservice();
//        List<user> li = fn.getalluser();
//        ObservableList<user> data = FXCollections.observableArrayList(li);
//        usersList.setItems(data);
        this.loadUsers();

    }

}
