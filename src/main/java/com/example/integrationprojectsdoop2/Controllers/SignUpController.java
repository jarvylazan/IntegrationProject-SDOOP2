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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Controller for the Sign-Up view.
 * Handles user sign-up operations such as validating input, creating new users, and navigating between views.
 * Implements encapsulation and includes detailed Javadoc for maintainability.
 *
 * @author Samuel
 */
public class SignUpController {

    /** Text field for entering the user's full name. */
    @FXML
    private TextField signUpName;

    /** Text field for entering the user's email address. */
    @FXML
    private TextField signUpEmail;

    /** Password field for entering the user's password. */
    @FXML
    private PasswordField signUpPassword;

    /** Password field for confirming the user's password. */
    @FXML
    private PasswordField signUpConfirmPassword;

    /** List of clients retrieved from the {@link UserManager} singleton instance. */
    private final List<User> aClientsList;



    public SignUpController() {
        this.aClientsList = UserManager.getInstance().getaClientsList();
    }

    /**
     * Handles the "Sign Up" button click event.
     * Validates the user's input, creates a new client, and adds it to the UserManager.
     * If the sign-up is successful, navigate to the client dashboard.
     *
     * @param pActionEvent the action event triggered by the button click.
     * @throws IOException if there is an issue navigating to the new view or saving user data.
     * @author Samuel
     */
    public void onSignUpClickButton(ActionEvent pActionEvent) throws IOException {
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
            for (User client : UserManager.getInstance().getaClientsList()) {
                if (email.equals(client.getaUser_Email())) {
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
            User newClient = new Client(fullName, email, password);
            UserManager.getInstance().addClient(newClient);
            System.out.println("New client created: " +
                    ((Client) newClient).getClientID() + ", " +
                    newClient.getaUser_Name() + ", " +
                    newClient.getaUser_Email() + ", " +
                    newClient.getaUser_Password() + ", " +
                    ((Client) newClient).getFormattedSubscriptionDate());
            // TODO: might want to change the title and message inside the alert. ( need to change the AlertHelper)
            AlertHelper clientAdd = new AlertHelper("Client Added: "+ newClient.getaUser_Email());
            clientAdd.executeSuccessAlert();


            // TODO: connect the client dashboard controller.
            /* TO BE DONE once the controller is done for the view.
            FXMLLoader SignUpfxmlLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/client-dashboard-view.fxml"));
            Parent clientDashboardView = SignUpfxmlLoader.load();

            // Get the current stage from any component's scene (like SignUpName)
            Stage stage = (Stage) SignUpName.getScene().getWindow();
            stage.setScene(new Scene(clientDashboardView)); // Set the new scene
            stage.show(); // Make sure to show the stage*/

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
     * @author Samuel
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
