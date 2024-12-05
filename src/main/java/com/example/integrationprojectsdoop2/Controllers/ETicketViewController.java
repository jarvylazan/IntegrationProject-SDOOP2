package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.ETicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

/**
 * Controller class for the E-Ticket View.
 * Displays the details of an e-ticket and allows the user to navigate back to the client dashboard.
 */
public class ETicketViewController {

    /**
     * The file path to the e-ticket view FXML file.
     */
    private static final String CLIENT_DASHBOARD_VIEW_PATH = "/com/example/integrationprojectsdoop2/client-dashboard-view.fxml";

    /**
     * The e-ticket to be displayed.
     */
    private ETicket aETicket;

    /**
     * Label to display e-ticket details.
     */
    @FXML
    private Label eTicketLabel;

    /**
     * Initializes the E-Ticket View with default values.
     */
    @FXML
    public void initialize() {
        this.eTicketLabel.setText("");
    }

    /**
     * Sets the E-Ticket data and updates the view to display the details.
     *
     * @param pTicket The e-ticket to display.
     */
    public void setETicketView(ETicket pTicket) {
        this.aETicket = pTicket;
        updateETicketLabel();
    }

    /**
     * Updates the e-ticket details label with formatted information.
     */
    private void updateETicketLabel() {
        // Extract details from the ticket
        String ticketId = this.aETicket.getETicketID();
        String movieTitle = this.aETicket.getShow().getMovie().getMovie_Title();
        String screeningRoom = this.aETicket.getShow().getScreenroom().getScreenroom_Name();
        String clientName = this.aETicket.getClient().getUser_Name();

        // Format date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String showtime = this.aETicket.getShow().getShowtime().toString();
        String purchaseDateTime = this.aETicket.getPurchaseDateTime().format(formatter);

        // Build the label text
        StringBuilder labelText = new StringBuilder();
        labelText.append("Ticket ID: ").append(ticketId).append("\n")
                .append("Movie Title: ").append(movieTitle).append("\n")
                .append("Showtime: ").append(showtime).append("\n")
                .append("Screening Room: ").append(screeningRoom).append("\n")
                .append("Client Name: ").append(clientName).append("\n")
                .append("Purchase Date & Time: ").append(purchaseDateTime).append("\n");

        // Set the text to the label
        eTicketLabel.setText(labelText.toString());
    }

    /**
     * Handles the Back button click event to navigate to the Client Dashboard.
     *
     * @param pActionEvent The ActionEvent triggered by clicking the Back button.
     */
    @FXML
    protected void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load the client dashboard view
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CLIENT_DASHBOARD_VIEW_PATH));
            Parent root = loader.load();

            // Pass client data to the Client Dashboard Controller
            ClientDashboardController controller = loader.getController();
            controller.setClientDashboardView("shows.ser", aETicket.getClient());

            // Set the new scene
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Client Dashboard");
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            System.err.println("Error loading the Client Dashboard: " + e.getMessage());
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
