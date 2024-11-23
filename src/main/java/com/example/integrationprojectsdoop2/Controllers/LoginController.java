package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.MovieTheatreApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField emailLoginTextField;

    @FXML
    private PasswordField loginPasswordField;

    private List<User> aManagersList;

    private List<User> aClientsList;

    public void initialize() {
        aClientsList = loginReader("clients.ser");
        aManagersList = loginReader("managers.ser");
    }

    @FXML
    protected void onLoginButtonClicked(javafx.event.ActionEvent pEvent) {
        //FXMLLoader nextViewLoader;

        try {
            // Check if managers list is initialized and not empty
            if (aManagersList == null || aManagersList.isEmpty()) {
                throw new Exception("No manager to manage the system. Please add at least one manager to the system.");
            }

            // Validate email input
            String email = emailLoginTextField.getText().trim();
            if (email.isEmpty()) {
                throw new Exception("Please enter your email address.");
            }
            if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new Exception("Please enter a valid email address. Ex: example@login.com");
            }

            // Validate password input
            String password = loginPasswordField.getText().trim();
            if (password.isEmpty()) {
                throw new Exception("Please enter your password.");
            }
            if (password.length() > 8) {
                throw new Exception("Password must be less than 8 characters long.");
            }

            //Check if the user is a client or manager
            boolean isUserAuthenticated = false;

            // Check in clients list
            for (User client : aClientsList) {
                if (client.getaUser_Email().equals(email) && client.getaUser_Password().equals(password)) {
                    // Load client dashboard
                    /*nextViewLoader = new FXMLLoader(MovieTheatreApplication.class.getResource("client-dashboard-view.fxml"));
                    nextViewLoader.load();*/
                    System.out.println("The Client dashboard view");
                    isUserAuthenticated = true;
                    break;
                }
            }

            // Check in managers list if not authenticated yet
            if (!isUserAuthenticated) {
                for (User manager : aManagersList) {
                    if (manager.getaUser_Email().equals(email) && manager.getaUser_Password().equals(password)) {
                        // Load manager dashboard
                       /* nextViewLoader = new FXMLLoader(MovieTheatreApplication.class.getResource("manager-dashboard-view.fxml"));
                        nextViewLoader.load();*/
                        System.out.println("The Manager dashboard view");
                        isUserAuthenticated = true;
                        break;
                    }
                }
            }

            // If no match found, throw an error
            if (!isUserAuthenticated) {
                throw new Exception("Invalid email or password. Please try again.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //showWarning(e.getMessage()); TO BE BUILD
        }
    }

    public void onSignUpClick(MouseEvent mouseEvent){
        try{
            FXMLLoader SignUpfxmlLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/SignUp-View.fxml"));
            Parent signUpView = SignUpfxmlLoader.load();
            emailLoginTextField.getScene().setRoot(signUpView);
        } catch (IOException e) {
            System.out.println("Error loading SignUp view: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<User> loginReader(String filename) {
        List<User> users = List.of(); // Start with an immutable empty list
        try {
            ReadObjects readObjects = new ReadObjects(filename);
            List<Object> rawObjects = readObjects.read(); // Read as Object list

            // Safely cast Object list to User list
            users = rawObjects.stream()
                    .filter(User.class::isInstance) // Ensure objects are of type User
                    .map(User.class::cast)          // Cast objects to User
                    .toList();                      // Collect as List<User>
        } catch (Exception e) {
            e.printStackTrace(); // Add logging to debug issues
        }

        return users;
    }

}