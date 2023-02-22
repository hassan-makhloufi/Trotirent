/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import trotirent.Trotirent;
import entity.user;
import interfaces.userInterface;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.userService;
import utils.session;

/**
 *
 * @author 
 */
public class LoginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private static String currentMailReset;

    public void switchToverifyresetCode(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("verifyresetcode.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LinkToResetPassword() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sendresetcode.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    public void LinkToCreateAnAccount() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    public void linkToLoginPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Trotirent.primaryStage.setScene(new Scene(root));
        Trotirent.primaryStage.show();

    }

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField resetcodeField;
    @FXML
    private PasswordField newpasswordField;
    @FXML
    private PasswordField confirmpasswordField;
    @FXML
    private Hyperlink ForgotPassword;
    @FXML
    private Hyperlink CreateAnAccount;

    userInterface fn = new userService();
    user test = new user();

    @FXML
    private void loginuser() throws IOException {
        String mail = emailField.getText();
        String pass = passwordField.getText();
        if (mail.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Veuillez remplir tous les champs obligatoires");
            alert.showAndWait();
        } else {
            test.setEmail(mail);
            test.setPassword(pass);
            fn.login(test);

            if (fn.isUserBanned(test)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("null");
                alert.setContentText("This account is banned");
                alert.showAndWait();
                session.setUser(null);
            } else if (session.getUser().getRoles().contains("Admin")) {
                Parent root = FXMLLoader.load(getClass().getResource("userslist.fxml"));
                Trotirent.primaryStage.setScene(new Scene(root));
                Trotirent.primaryStage.show();
            } else if (!session.getUser().getRoles().contains("Admin")) {

                Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
                Trotirent.primaryStage.setScene(new Scene(root));
                Trotirent.primaryStage.show();
            }
        }

    }

    @FXML
    private void sendresetCode() throws IOException, Exception {
        LoginController.currentMailReset = emailField.getText();

        if (emailField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Veuillez saisir votre e-mail");
            alert.showAndWait();
        } else {
            if (fn.sendresetCode(LoginController.currentMailReset)) {
                Parent root = FXMLLoader.load(getClass().getResource("verifyresetcode.fxml"));
                Trotirent.primaryStage.setScene(new Scene(root));
                Trotirent.primaryStage.show();
            }

        }

    }

    @FXML
    private void verifyresetCode() throws IOException, Exception {
        String resetCode = resetcodeField.getText();
        int code = Integer.parseInt(resetCode);
        if (resetCode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Veuillez entrer le code de réinitialisation");
            alert.showAndWait();
        } else if (code != userService.code) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Veuillez vérifier le code de réinitialisation");
            alert.showAndWait();
        } else {
            if (code == userService.code) {
                Parent root = FXMLLoader.load(getClass().getResource("resetpassword.fxml"));
                Trotirent.primaryStage.setScene(new Scene(root));
                Trotirent.primaryStage.show();
            }

        }

    }

    @FXML
    public void resetpassword() throws IOException, Exception {
        String npass = newpasswordField.getText();
        String cpass = confirmpasswordField.getText();

        if (npass.isEmpty() || cpass.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("Veuillez remplir tous les champs obligatoires");
            alert.showAndWait();
        } else if (!npass.equals(cpass)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("null");
            alert.setContentText("les mot de passes ne correspondent pas");
            alert.showAndWait();

        } else {

            if (fn.resetPassword(LoginController.currentMailReset, npass)) {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Trotirent.primaryStage.setScene(new Scene(root));
                Trotirent.primaryStage.show();

            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources
    ) {
    }

}
