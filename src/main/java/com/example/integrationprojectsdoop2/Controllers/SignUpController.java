package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.Models.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpController {
    @FXML
    private TextField SignUpName;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private PasswordField SignUpPassword;

    @FXML
    private PasswordField SignUpConfirmPassword;


    public void onSighOnClickButton(ActionEvent pActionEvent) throws IOException {
        try {
            boolean flag = false;

            String fullName = SignUpName.getText().trim();
            if (fullName.isEmpty() || !fullName.matches("^[a-zA-Z]+ [a-zA-Z]+$")) {
                throw new Exception("You need to provide your full name. eg: John Doe");
            }
            if (fullName.length() < 3 || fullName.length() > 100) {
                throw new Exception("You need to provide at least 3 characters long and no longer than 100 characters.");
            }

            String email = SignUpEmail.getText().trim();
            if (email.isEmpty() || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new Exception("You need to provide a valid email address. eg: example@example.com");
            }

            for(User clients: UserManager.getInstance().getaClientsList()){
                if(email.equals(clients.getaUser_Email())){
                    throw new Exception("This email is already subscribe. Please try again.");
                }
            }

            String ConfirmPassword = SignUpConfirmPassword.getText().trim();
            if (ConfirmPassword.isEmpty()) {
                throw new Exception("You need to provide a Confirmation password");
            }

            String signUpPassword = SignUpPassword.getText().trim();
            if (signUpPassword.isEmpty()) {
                throw new Exception("You need to enter a password.");
            }
            if (signUpPassword.length() > 8) {
                throw new Exception("You need to provide a password no more than 8 characters.");
            }
            if (!signUpPassword.equals(SignUpConfirmPassword.getText())) {
                throw new Exception("Passwords do not match.");
            }


            User newClient = new Client(fullName, email, signUpPassword);
            UserManager.getInstance().addClient(newClient);
            System.out.println(((Client) newClient).getClientID() + ", " + newClient.getaUser_Name() + ", " + newClient.getaUser_Email() + ", " + newClient.getaUser_Password() + ", " + (((Client) newClient).getFormattedSubscriptionDate()));
            System.out.println("New client created."); // add a success MESSAGE INSTEAD.
/* TO BE DONE once the controller is done for the view.
            FXMLLoader SignUpfxmlLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/client-dashboard-view.fxml"));
            Parent clientDashboardView = SignUpfxmlLoader.load();

            // Get the current stage from any component's scene (like SignUpName)
            Stage stage = (Stage) SignUpName.getScene().getWindow();
            stage.setScene(new Scene(clientDashboardView)); // Set the new scene
            stage.show(); // Make sure to show the stage*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onCancelClickButton(javafx.event.ActionEvent pActionEvent) {
        try { // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/Login-View.fxml"));
            Parent loginView = loader.load();

            // Create a new scene for the login view
            javafx.scene.Scene newScene = new javafx.scene.Scene(loginView);

            // Apply the stylesheet to the new scene
            newScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

            // Get the current stage and set the new scene
            javafx.stage.Stage currentStage = (javafx.stage.Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the Login-View.fxml: " + e.getMessage());
        }
    }
}
