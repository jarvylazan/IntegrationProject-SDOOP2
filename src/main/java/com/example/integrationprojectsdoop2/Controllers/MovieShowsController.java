package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.Show;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.Objects;


public class MovieShowsController {

    private Client aLoggedClient;

    private Show aShow;

    @FXML
    private ListView showListView;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Label movieTitleAndDateLabel;

    @FXML
    public void initialize() {
    }

    public void setMovieShowsView(Show pShow, Client pLoggedClient) {
        this.aLoggedClient = pLoggedClient;
        this.aShow = pShow;

        updateMovieTitleAndDateLabel();
    }

    private void updateMovieTitleAndDateLabel() {
        movieTitleAndDateLabel.setText(aShow.getMovie().getAMovie_Title() + ", " + aShow.getShowDate());
    }

    public void onBuyTicketButtonClick() {
    }

    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/client-dashboard-view.fxml"));
            Parent root = loader.load();
            ClientDashboardController controller = loader.getController();
            controller.setClientDashboardView("shows.ser", aLoggedClient);
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
