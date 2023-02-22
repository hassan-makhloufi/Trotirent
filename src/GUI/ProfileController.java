/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import trotirent.Trotirent;
import entity.user;
import interfaces.userInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import services.userService;
import utils.session;

/**
 *
 * @author Souhail
 */
public class ProfileController implements Initializable {

    @FXML
    public ImageView UserProfilePicutre;
    @FXML
    public ImageView UserProfilePicutre1;

    @FXML
    public Label UserFirstName;
    @FXML
    private TextField UserEmail;
    @FXML
    private TextField UserFirstName1;
    @FXML
    private TextField UserLastName;
    @FXML
    private TextField UserPhoneNumber;

    public final Background focusBackground = new Background(new BackgroundFill(Color.web("#E4E4E4"), CornerRadii.EMPTY, Insets.EMPTY));
    public final Background unfocusBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    public HBox selectedMenuItem = null;
    @FXML
    public AnchorPane menuPane;
    @FXML
    private VBox vBoxMenu;
    

    public void loadFxml(String page) throws IOException {
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(page));
        menuPane.getChildren().add(newLoadedPane);
    }

    public void LinkToChangePassword() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
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
    public void LinkToUserList() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("usersliste.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

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

    public void showProfileOnTop() {

        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\Admin\\Documents\\images\\" + session.getUser().getProfile_picture());
            UserProfilePicutre1.setImage(new Image(inputstream));
        } catch (FileNotFoundException ex) {
        }

    }
    userInterface fn = new userService();
    user test = new user();

    public void logout() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();
        fn.logout();

    }

    public void UploadProfilePicture() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("open");
        File image = fileChooser.showOpenDialog(Trotirent.primaryStage);
        if (image != null) {
            int index = (image.toString().lastIndexOf("."));
            if (index > 0) {
                String extension = image.toString().substring(index + 1);
                if (!(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg"))) {
                    System.err.println("wrong format");
                } else {
                    Random random = new Random();
                    int imageName = random.nextInt(30000000);
                    String pathname = "C:\\Users\\Admin\\Documents\\images\\" + imageName + String.valueOf(imageName) + "." + extension;
                    File file1 = new File(pathname);
                    try {
                        String newImage = imageName + String.valueOf(imageName) + "." + extension;
                        Files.copy(image.toPath(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        session.getUser().setProfile_picture(newImage);
                        fn.updateProfilePicture(session.getUser());
                    } catch (IOException e) {
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
                    Trotirent.primaryStage.setScene(new Scene(root));
                    Trotirent.primaryStage.show();
                }
            }

        }

    }

    public void DeleteAccount() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer votre compte ?");

        ButtonType buttonTypeOne = new ButtonType("Oui");

        ButtonType buttonTypeCancel = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            fn.deleteuser(session.getUser().getId());
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Trotirent.primaryStage.setScene(new Scene(root));
            Trotirent.primaryStage.show();
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    public void UpdateInfo() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Êtes-vous sûr de changer vos information? ");

        ButtonType buttonTypeOne = new ButtonType("Oui");

        ButtonType buttonTypeCancel = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            String phone_number = UserPhoneNumber.getText();
            int number = Integer.parseInt(phone_number);
            String mail = UserEmail.getText();

            String fname = UserFirstName1.getText();
            String lname = UserLastName.getText();

            test.setEmail(mail);
            test.setFirstname(fname);
            test.setLastname(lname);
            test.setPhonenumber(number);
            fn.updateuserinfo(test);
            Parent root = FXMLLoader.load(getClass().getResource("setings.fxml"));
            Trotirent.primaryStage.setScene(new Scene(root));
            Trotirent.primaryStage.show();
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.showProfile();
        this.showProfileOnTop();
        String FirstName = session.getUser().getFirstname();
        String Email = session.getUser().getEmail();
        String LastName = session.getUser().getLastname();
        int PhoneNumber = session.getUser().getPhonenumber();

        String PN = String.valueOf(PhoneNumber);
        UserEmail.setText(Email);

        UserFirstName1.setText(FirstName);
        UserLastName.setText(LastName);

        UserPhoneNumber.setText(PN);
    }

}
