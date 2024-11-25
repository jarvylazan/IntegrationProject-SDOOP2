package com.example.integrationprojectsdoop2.Helpers;

import javafx.scene.control.Alert;

/**
 * Helper class for displaying different types of alerts in a JavaFX application.
 * Provides methods to display success, error, and warning alerts with a customizable message.
 *
 * @author Jarvy Lazan
 */
public class AlertHelper {
    private String aAlertMessage;

    /**
     * Constructor for AlertHelper.
     *
     * @param pAlertMessage the message to display in the alert.
     * @throws IllegalArgumentException if the alert message is null or empty.
     * @author Jarvy Lazan
     */
    public AlertHelper(String pAlertMessage) {
        if (pAlertMessage == null || pAlertMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert message cannot be null or empty.");
        }
        aAlertMessage = pAlertMessage;
    }

    /**
     * Displays a success alert (CONFIRMATION type) with the provided message.
     * Uses JavaFX's Alert class to show the alert.
     *
     * @throws IllegalStateException if the JavaFX runtime is not initialized properly.
     * @author Jarvy Lazan
     */
    public void executeSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, aAlertMessage);
        alert.showAndWait();
    }

    /**
     * Displays an error alert (ERROR type) with the provided message.
     * Uses JavaFX's Alert class to show the alert.
     *
     * @throws IllegalStateException if the JavaFX runtime is not initialized properly.
     * @author Jarvy Lazan
     */
    public void executeErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, aAlertMessage);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert (WARNING type) with the provided message.
     * Uses JavaFX's Alert class to show the alert.
     *
     * @throws IllegalStateException if the JavaFX runtime is not initialized properly.
     * @author Jarvy Lazan
     */
    public void executeWarningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, aAlertMessage);
        alert.showAndWait();
    }
}
