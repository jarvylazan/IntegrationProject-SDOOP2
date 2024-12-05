package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
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
import java.util.List;
import java.util.Objects;

/**
 * Controller for the Sign-Up view.
 * Handles user sign-up operations such as validating input, creating new users, and navigating between views.
 * Implements encapsulation and includes detailed Javadoc for maintainability.
 *
 * @author Samuel Mireault
 */
public class SignUpController {

    /**
     * Text field for entering the user's full name.
     */
    @FXML
    private TextField signUpName;

    /**
     * Text field for entering the user's email address.
     */
    @FXML
    private TextField signUpEmail;

    /**
     * Password field for entering the user's password.
     */
    @FXML
    private PasswordField signUpPassword;

    /**
     * Password field for confirming the user's password.
     */
    @FXML
    private PasswordField signUpConfirmPassword;

    /**
     * List of clients retrieved from the {@link UserManager} singleton instance.
     * Used for checking existing users and managing sign-ups.
     */
    private final List<User> aClientsList;

    /**
     * Constructs a new SignUpController.
     * Initializes the client list by retrieving it from the {@link UserManager} singleton instance.
     *
     * @author Samuel Mireault
     */
    public SignUpController() {
        this.aClientsList = UserManager.getInstance().getClientList();
    }

    /**
     * Handles the "Sign Up" button click event.
     * Validates the user's input fields including full name, email, and password.
     * If the input is valid, creates a new {@link Client} object and adds it to the {@link UserManager}.
     * Navigate the user to the client dashboard view upon successful sign-up.
     *
     * @throws IllegalArgumentException if there is an issue navigating to the client dashboard view or saving user data.
     * @author Samuel Mireault
     */
    public void onSignUpClickButton()  {
        try {
            // Validate the user's full name
            String fullName = signUpName.getText().trim();
            if (fullName.isEmpty() || !fullName.matches("^[a-zA-Z-]+ [a-zA-Z-]+$")) {
                throw new IllegalArgumentException("You need to provide your full name. \neg: John Doe");
            }
            if (fullName.length() < 3 || fullName.length() > 100) {
                throw new IllegalArgumentException("You need to provide at least 3 characters and no more than 100 characters.");
            }

            // Validate the user's email
            String email = signUpEmail.getText().trim();
            if (email.isEmpty() || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("You need to provide a valid email address. \neg: example@example.com");
            }
            for (User client : UserManager.getInstance().getClientList()) {
                if (email.equals(client.getUser_Email())) {
                    throw new IllegalArgumentException("This email is already subscribed. Please try again.");
                }
            }

            // Validate the user's password
            String confirmPassword = signUpConfirmPassword.getText().trim();
            if (confirmPassword.isEmpty()) {
                throw new IllegalArgumentException("You need to provide a confirmation password.");
            }
            String password = signUpPassword.getText().trim();
            if (password.isEmpty()) {
                throw new IllegalArgumentException("You need to enter a password.");
            }
            if (password.length() > 8) {
                throw new IllegalArgumentException("Your password must be no more than 8 characters.");
            }
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match.");
            }

            // Create a new client and add to UserManager
            Client newClient = new Client(fullName, email, password);
            UserManager.getInstance().addClient(newClient);
            System.out.println("New client created: " +
                    newClient.getClientID() + ", " +
                    newClient.getUser_Name() + ", " +
                    newClient.getUser_Email() + ", " +
                    newClient.getUser_Password() + ", " +
                    newClient.getFormattedSubscriptionDate());
            AlertHelper clientAdd = new AlertHelper("Client Added: " + newClient.getUser_Email());
            clientAdd.setMessage("You have successfully signed up. \n\nWelcome to TJS Theater!");
            clientAdd.executeSuccessAlert();

            // Navigate to the client dashboard
            LoginController.clientDashboard(newClient, signUpEmail);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            AlertHelper validation = new AlertHelper(e.getMessage());
            validation.executeWarningAlert();
        }
    }

    /**
     * Handles the "Cancel" button click event.
     * Navigates the user back to the login view.
     *
     * @param pActionEvent the action event triggered by the button click.
     * @author Samuel Mireault
     */
    public void onCancelClickButton(ActionEvent pActionEvent) {
        try {
            // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/Login-View.fxml"));
            Parent loginView = loader.load();

            // Create a new scene for the login view
            Scene newScene = new Scene(loginView);
            newScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

            // Set the new scene to the current stage
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Log in");
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            System.err.println("Error loading the Login-View.fxml: " + e.getMessage());
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
