/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import trotirent.Trotirent;
import entity.user;
import interfaces.userInterface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import services.userService;

/**
 *
 * @author Souhail
 */
public class UsersListController implements Initializable {

    userInterface fn = new userService();
    user test = new user();
    user selecteduser = null;
    private static int row;
    private static int size = 5;
    private static int nb;

    @FXML
    TableView<user> usersList;
    @FXML
    private TableColumn<user, Integer> userId;
    @FXML
    private TableColumn<user, String> userFirstName;
    @FXML
    private TableColumn<user, Image> userProfilePicture;

    @FXML
    private TableColumn<user, String> userLastName;

    @FXML
    private TableColumn<user, String> userRole;

    @FXML

    private TableColumn<user, Integer> userCountryCode;

    @FXML
    private TableColumn<user, Integer> userPhoneNumber;
    @FXML
    private TableColumn<user, Boolean> userBanStatus;
    @FXML
    private Button NextPage;
    @FXML
    private Button PreviousPage;

    ObservableList<user> oblist = FXCollections.observableArrayList();

    @FXML
    private void banselectedUser() {
        selecteduser = usersList.getSelectionModel().getSelectedItem();
        fn.banUser(selecteduser.getId());
    }

    @FXML
    private void unbanselectedUser() {
        selecteduser = usersList.getSelectionModel().getSelectedItem();
        fn.unbanUser(selecteduser.getId());
    }

    public void logout() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
        fn.logout();

    }
    

    public void GoToBackProds() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BackOfficeProducts.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
    }

    public void GoToCat() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addcategory.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
    }

    private void userList(int i, int j) {

        ImageView imageView = new ImageView(getClass().getResource("..\\images\\icons8-next-page-100.png").toExternalForm());
        NextPage.setGraphic(imageView);
        ImageView imageViewv = new ImageView(getClass().getResource("..\\images\\icons8-next-page-99.png").toExternalForm());
        PreviousPage.setGraphic(imageViewv);
        int testEndPage = fn.getRowCount() - UsersListController.row;

        if (this.size >= fn.getRowCount() || this.size >= testEndPage) {
            NextPage.setDisable(true);

        } else {
            NextPage.setDisable(false);

        }
        if (UsersListController.row < this.size) {
            PreviousPage.setDisable(true);
        } else {
            PreviousPage.setDisable(false);
        }

        // TODO
        List<user> li = fn.pagination(i, j);

        li.forEach(e
                -> {
            oblist.add(e);

            userId.setCellValueFactory(new PropertyValueFactory<>("id"));
            userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            userLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            userRole.setCellValueFactory(new PropertyValueFactory<>("email"));
            userCountryCode.setCellValueFactory(new PropertyValueFactory<>("country_code"));
            userPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            userBanStatus.setCellValueFactory(new PropertyValueFactory<>("isbanned"));
            userProfilePicture.setCellValueFactory(new PropertyValueFactory<>("profile_picture"));

        }
        );
        userProfilePicture.setCellValueFactory((CellDataFeatures<user, Image> p) -> {
            String userImage = p.getValue().getProfile_picture();
            FileInputStream inputstream = null;
            try {
                inputstream = new FileInputStream("C:\\Users\\Admin\\Documents\\images\\" + userImage);
            } catch (FileNotFoundException ex) {
            }

            return new SimpleObjectProperty<>(new Image(inputstream, 100, 100, false, false));
        });
        userProfilePicture.setStyle("-fx-alignment:center");
        userProfilePicture.setCellFactory((TableColumn<user, Image> p) -> new TableCell<user, Image>() {
            @Override
            protected void updateItem(Image i, boolean empty) {
                super.updateItem(i, empty);
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                double width = 60;
                double height = 60;
                ImageView imageView = (i == null || empty) ? null : ImageViewBuilder.create().image(i).build();
                setGraphic(imageView);
            }
        });

//
        usersList.setItems(oblist);

    }

    @FXML
    private void LinkToNextPage() throws IOException {
        UsersListController.row = UsersListController.row + UsersListController.size;
        Parent root = FXMLLoader.load(getClass().getResource("userslist.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    @FXML
    private void LinkPreviousPage() throws IOException {
        UsersListController.row = UsersListController.row - UsersListController.size;
        Parent root = FXMLLoader.load(getClass().getResource("userslist.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

//
    }

    public void LinkToStats() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userstats.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userList(UsersListController.size, UsersListController.row);
    }

}
