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
        String ticketId = this.aETicket.getaTicketID(); // Unique ticket identifier
        String movieTitle = this.aETicket.getaShow().getMovie().getAMovie_Title();
        String showtime = this.aETicket.getaShow().getShowtime().toString();
        String screeningRoom = this.aETicket.getaShow().getScreenroom().getScreenroom_Name();
        String clientName = this.aETicket.getaClient().getaUser_Name();
        String purchaseDateTime = this.aETicket.getaPurchaseDateTime().toString();

        String labelText = String.format(
                "Ticket ID: %s\nMovie Title: %s\nShowtime: %s\nScreening Room: %s\nClient Name: %s\nPurchase Date & Time: %s",
                ticketId, movieTitle, showtime, screeningRoom, clientName, purchaseDateTime
        );


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
            controller.setClientDashboardView("shows.ser", aETicket.getaClient());

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
