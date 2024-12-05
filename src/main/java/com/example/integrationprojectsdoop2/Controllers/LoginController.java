package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.Models.UserManager;
import com.example.integrationprojectsdoop2.MovieTheatreApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller for handling login operations in the application.
 * This class provides functionality for validating user credentials,
 * determining user roles (Manager or Client), and navigating to appropriate dashboards.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * LoginController controller = new LoginController();
 * controller.onLoginButtonClicked(event);
 * }
 * </pre>
 * @author Samuel Mireault
 */
public class LoginController {
    /** Text field for entering the user's email address. */
    @FXML
    private TextField emailLoginTextField;
    /** Password field for entering the user's password. */
    @FXML
    private PasswordField loginPasswordField;

    /**
     * List of managers retrieved from the {@link UserManager} singleton instance.
     */
    private final List<User> aManagersList;

    /**
     * List of clients retrieved from the {@link UserManager} singleton instance.
     */
    private final List<User> aClientsList;

    /**
     * Initializes a new instance of the {@code LoginController} class.
     * Retrieves the manager and client lists from {@link UserManager}.
     *
     * @author Samuel Mireault
     */
    public LoginController() {
        this.aManagersList = UserManager.getInstance().getManagerList();
        this.aClientsList = UserManager.getInstance().getClientList();
    }

    /**
     * Handles the login button click event.
     * Validates the user's credentials and navigates to the appropriate dashboard.
     *
     * @author Samuel Mireault
     */
    @FXML
    protected void onLoginButtonClicked() {
        try {
            validateManagersExist();
            String email = validateAndGetEmail();
            String password = validateAndGetPassword();
            authenticateUser(email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            AlertHelper loginError = new AlertHelper(e.getMessage());
            loginError.executeErrorAlert();
        }
    }

    /**
     * Handles the sign-up button click event.
     * Opens the Sign-Up view and updates the stage title.
     *
     * @author Samuel Mireault
     */
    public void onSignUpClick() {
        try {
            FXMLLoader signUpLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/sign-up-view.fxml"));
            Parent signUpView = signUpLoader.load();
            // Get the current stage from the emailLoginTextField's scene
            Stage currentStage = (Stage) emailLoginTextField.getScene().getWindow();

            // Set the new title for the stage
            currentStage.setTitle("Sign-Up");

            // Set the new scene to the sign-up view
            Scene signUpScene = new Scene(signUpView);
            currentStage.setScene(signUpScene);
        } catch (IOException e) {
            System.out.println("Error loading Sign-Up view: " + e.getMessage());
            AlertHelper signupError = new AlertHelper(e.getMessage());
            signupError.executeErrorAlert();
        }
    }

    /**
     * Validates that there is at least one manager in the system.
     *
     * @throws Exception if no managers exist in the system.
     * @author Samuel Mireault
     */
    private void validateManagersExist() throws Exception {
        if (aManagersList == null || aManagersList.isEmpty()) {
            throw new Exception("No manager to manage the system. Please add at least one manager to the system.");
        }
    }

    /**
     * Validates the email input field and retrieves the entered email.
     *
     * @return the validated email address.
     * @throws Exception if the email is invalid or empty.
     * @author Samuel Mireault
     */
    private String validateAndGetEmail() throws Exception {
        String email = emailLoginTextField.getText().trim();
        if (email.isEmpty()) {
            throw new Exception("Please enter your email address.");
        }
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new Exception("Please enter a valid email address. Ex: example@login.com");
        }
        return email;
    }

    /**
     * Validates the password input field and retrieves the entered password.
     *
     * @return the validated password.
     * @throws Exception if the password is invalid or empty.
     * @author Samuel Mireault
     */
    private String validateAndGetPassword() throws Exception {
        String password = loginPasswordField.getText().trim();
        if (password.isEmpty()) {
            throw new Exception("Please enter your password.");
        }
        if (password.length() > 8) {
            throw new Exception("Password must be less than 8 characters long.");
        }
        return password;
    }

    /**
     * Authenticates the user against the stored client and manager lists.
     * Opens the appropriate dashboard view if authentication succeeds.
     *
     * @param pEmail    the user's email address.
     * @param pPassword the user's password.
     * @throws Exception if authentication fails due to invalid credentials.
     * @author Samuel Mireault
     */
    private void authenticateUser(String pEmail, String pPassword) throws Exception {
        boolean isUserAuthenticated = false;

        // Check in the client list
        for (User client : aClientsList) {
            if (client.getUser_Email().equals(pEmail) && client.getUser_Password().equals(pPassword)) {
                System.out.println("The Client dashboard view");
                clientDashboard((Client) client, emailLoginTextField);
                isUserAuthenticated = true;
                break;
            }
        }

        // Check in the manager list if not authenticated yet
        if (!isUserAuthenticated) {
            for (User manager : aManagersList) {
                if (manager.getUser_Email().equals(pEmail) && manager.getUser_Password().equals(pPassword)) {
                    System.out.println("The Manager dashboard view");
                    FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("manager-dashboard.fxml")));
                    Parent root = fxmlLoader.load();

                    Scene scene = new Scene(root);
                    Stage currentStage = (Stage) emailLoginTextField.getScene().getWindow();
                    currentStage.setTitle("Manager Dashboard");
                    currentStage.setScene(scene);
                    currentStage.show();
                    isUserAuthenticated = true;
                    break;
                }
            }
        }

        // If no match found, throw an error
        if (!isUserAuthenticated) {
            throw new Exception("Invalid email or password. Please try again.");
        }
    }
    /**
     * Navigates to the Client Dashboard view and initializes it with the provided client details.
     *
     * @param pClient    the {@link Client} instance to be used for initializing the dashboard.
     * @param pTextfield the {@link TextField} from which the current stage is obtained to display the new scene.
     * @throws IOException if an error occurs while loading the FXML file.
     * @author Samuel Mireault
     */

    static void clientDashboard(Client pClient, TextField pTextfield) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("client-dashboard-view.fxml")));
        Parent root = fxmlLoader.load();
        ClientDashboardController controller = fxmlLoader.getController();
        controller.setClientDashboardView("shows.ser", pClient);

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) pTextfield.getScene().getWindow();
        currentStage.setTitle("Client Dashboard");
        currentStage.setScene(scene);
        currentStage.show();
    }
}
