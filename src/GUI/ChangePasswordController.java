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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import services.userService;
import utils.session;

/**
 *
 * @author Souhail
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private VBox vBoxMenu;
    @FXML
    private ImageView UserProfilePicutre;
    @FXML
    private Label UserFirstName;

    private final Background focusBackground = new Background(new BackgroundFill(Color.web("#E4E4E4"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    private final Border border = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null));

    private HBox selectedMenuItem = null;
    @FXML
    private AnchorPane menuPane;

    public void loadFxml(String page) throws IOException {
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(page));
        menuPane.getChildren().add(newLoadedPane);
    }

    public void showProfile() {
        String firstName = session.getUser().getFirstname();
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\Admin\\Documents\\images\\" + session.getUser().getProfile_picture());
            UserProfilePicutre.setImage(new Image(inputstream));
        } catch (FileNotFoundException ex) {
        }
        UserFirstName.setText(firstName);

    }

    public void LinkToSettings() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    public void LinkToConditions() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("conditions.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    public void LinkToChangePassword() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
    }

    public void LinkToProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }
 public void logout() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
        fn.logout();

    }
    @FXML
    public void selectMenueItem(MouseEvent event) {
        HBox hb = (HBox) event.getSource();

        if (!(hb).equals(selectedMenuItem)) {
            if (selectedMenuItem != null) {
                selectedMenuItem.setBackground(unfocusBackground);
            }

            selectedMenuItem = hb;
            selectedMenuItem.setBackground(focusBackground);
        }
        try {
            loadFxml("EventsView.fxml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    

    @FXML
    private PasswordField oldpasswordField;
    @FXML
    private PasswordField newpasswordField;
    @FXML
    private PasswordField confirmpasswordField;

    userInterface fn = new userService();
    user test = new user();

    @FXML
    private void changepassword() {
        String opass = oldpasswordField.getText();
        String npass = newpasswordField.getText();
        String cpass = confirmpasswordField.getText();

        if (opass.isEmpty() || npass.isEmpty() || cpass.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Please fill all required fields");
            alert.showAndWait();
        } else if (!npass.equals(cpass)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("password don't match");
            alert.showAndWait();

        } else if (newpasswordField.getText().length() < 8) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("le mdp doit conteir plus que 8 characters");
            alert.showAndWait();

        } else {

            fn.updatePassword(opass, npass);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.showProfile();

    }
}
