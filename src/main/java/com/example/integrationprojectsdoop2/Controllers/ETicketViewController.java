package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ETicket;
import com.example.integrationprojectsdoop2.Models.Showtime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ETicketViewController {

    private ETicket aETicket;

    @FXML
    private Label eTicketLabel;

    @FXML
    public void initialize() {
        this.eTicketLabel.setText("");
    }

    public void setETicketView(ETicket pTicket) {
        this.aETicket = pTicket;

        updateETicketLabel();
    }

    private void updateETicketLabel() {
        // Extract details from the ticket
        String ticketId = this.aETicket.getaTicketID(); // Assuming getId() returns a String or unique identifier
        String movieTitle = this.aETicket.getaShow().getMovie().getAMovie_Title();
        String showtime = this.aETicket.getaShow().getShowtime().toString(); // Assuming Showtime has a meaningful toString()
        String screeningRoom = this.aETicket.getaShow().getScreenroom().getScreenroom_Name(); // Adjust based on your model
        String clientName = this.aETicket.getaClient().getaUser_Name(); // Assuming getClient() returns a Client object with a getName() method
        String purchaseDateTime = this.aETicket.getaPurchaseDateTime().toString(); // Assuming this returns a formatted date-time string

        // Build the label text
        StringBuilder labelText = new StringBuilder();
        labelText.append("🎟️ E-Ticket Details 🎟️\n\n")
                .append("Ticket ID: ").append(ticketId).append("\n")
                .append("Movie Title: ").append(movieTitle).append("\n")
                .append("Showtime: ").append(showtime).append("\n")
                .append("Screening Room: ").append(screeningRoom).append("\n")
                .append("Client Name: ").append(clientName).append("\n")
                .append("Purchase Date & Time: ").append(purchaseDateTime).append("\n");

        // Set the text to the label
        eTicketLabel.setText(labelText.toString());
    }

    @FXML
    protected void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/client-dashboard-view.fxml"));
            Parent root = loader.load();
            ClientDashboardController controller = loader.getController();
            controller.setClientDashboardView("shows.ser", aETicket.getaClient());
            Scene newScene = new Scene(root);

            // Set the new scene to the current stage
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Client Dashboard");
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            System.err.println("Error loading the Client Dashboard : " + e.getMessage());
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
