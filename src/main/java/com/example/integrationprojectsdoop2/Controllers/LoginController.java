package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.Models.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
 *
 * @author Samuel
 * @since 1.0
 */
public class LoginController {

    @FXML
    private TextField emailLoginTextField;

    @FXML
    private PasswordField loginPasswordField;

    /** List of managers retrieved from the {@link UserManager} singleton instance. */
    private final List<User> managersList;

    /** List of clients retrieved from the {@link UserManager} singleton instance. */
    private final List<User> clientsList;

    /**
     * Initializes a new instance of the {@code LoginController} class.
     * Retrieves the manager and client lists from {@link UserManager}.
     */
    public LoginController() {
        this.managersList = UserManager.getInstance().getaManagersList();
        this.clientsList = UserManager.getInstance().getaClientsList();
    }

    /**
     * Handles the login button click event.
     * Validates the user's credentials and determines if the user is a Manager or a Client.
     * Navigate to the appropriate dashboard based on the user's role.
     *
     * @param pEvent the event triggered by clicking the login button.
     * @author Samuel
     */
    @FXML
    protected void onLoginButtonClicked(javafx.event.ActionEvent pEvent) {
        try {
            validateManagersExist();
            String email = validateAndGetEmail();
            String password = validateAndGetPassword();
            authenticateUser(email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: Implement user-facing error display (e.g., showWarning(e.getMessage()))
        }
    }

    /**
     * Handles the sign-up button click event.
     * Navigates to the Sign-Up view.
     *
     * @param pMouseEvent the mouse event triggered by clicking the sign-up button.
     * @author Samuel
     */
    public void onSignUpClick(MouseEvent pMouseEvent) {
        try {
            FXMLLoader signUpLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/sign-up-view.fxml"));
            Parent signUpView = signUpLoader.load();
            emailLoginTextField.getScene().setRoot(signUpView);
        } catch (IOException e) {
            System.out.println("Error loading Sign-Up view: " + e.getMessage());
        }
    }

    /**
     * Validates that there is at least one manager in the system.
     *
     * @throws Exception if no managers exist in the system.
     * @author Samuel
     */
    private void validateManagersExist() throws Exception {
        if (managersList == null || managersList.isEmpty()) {
            throw new Exception("No manager to manage the system. Please add at least one manager to the system.");
        }
    }

    /**
     * Validates the email input field and retrieves the entered email.
     *
     * @return the validated email address.
     * @throws Exception if the email is invalid.
     * @author Samuel
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
     * @throws Exception if the password is invalid.
     * @author Samuel
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
     * Navigates to the appropriate dashboard view if authentication succeeds.
     *
     * @param pEmail    the user's email address.
     * @param pPassword the user's password.
     * @throws Exception if authentication fails.
     * @author Samuel
     */
    private void authenticateUser(String pEmail, String pPassword) throws Exception {
        boolean isUserAuthenticated = false;

        // Check in the client list
        for (User client : clientsList) {
            if (client.getaUser_Email().equals(pEmail) && client.getaUser_Password().equals(pPassword)) {
                System.out.println("The Client dashboard view");
                // TODO: fetch the Client view
                isUserAuthenticated = true;
                break;
            }
        }

        // Check in the manager list if not authenticated yet
        if (!isUserAuthenticated) {
            for (User manager : managersList) {
                if (manager.getaUser_Email().equals(pEmail) && manager.getaUser_Password().equals(pPassword)) {
                    System.out.println("The Manager dashboard view");
                    // TODO: fetch the Manager view
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
}
