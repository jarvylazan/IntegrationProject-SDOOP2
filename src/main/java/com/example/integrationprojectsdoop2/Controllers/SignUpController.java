package com.example.integrationprojectsdoop2.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField SignUpName;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private PasswordField SignUpPassword;

    @FXML
    private PasswordField SignUpConfirmPassword;





    public void onSighOnClickButton(ActionEvent actionEvent) {
    }

    public void onCancelClickButton(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/Login-View.fxml"));
            Parent loginView = loader.load();

            // Optionally, initialize the login view controller if needed
            //LoginController loginController = loader.getController();

            // Reset the scene with the login view
            javafx.scene.Scene currentScene = ((javafx.scene.Node) actionEvent.getSource()).getScene();
            javafx.stage.Stage currentStage = (javafx.stage.Stage) currentScene.getWindow();
            currentStage.setScene(new javafx.scene.Scene(loginView));


        } catch (IOException e) {
            e.printStackTrace();
        }}
}
