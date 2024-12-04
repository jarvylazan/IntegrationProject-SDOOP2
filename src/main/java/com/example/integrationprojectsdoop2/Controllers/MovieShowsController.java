package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.ETicket;
import com.example.integrationprojectsdoop2.Models.Show;
import com.example.integrationprojectsdoop2.MovieTheatreApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class MovieShowsController {

    private Map<String, Show> showMap = new HashMap<>();

    private List<Show> aShowOptions = new ArrayList<>();

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

    public void setMovieShowsView(Show pShow, Client pLoggedClient, List<Show> pShowOptions) {
        this.aShowOptions = pShowOptions;
        this.aLoggedClient = pLoggedClient;
        this.aShow = pShow;

        updateMovieTitleAndDateLabel();

        updateShowListView();
    }

    private void updateMovieTitleAndDateLabel() {
        this.movieTitleAndDateLabel.setText(this.aShow.getMovie().getMovie_Title() + ", " + this.aShow.getShowDate());
    }

    private void updateShowListView() {
        ObservableList<String> showDetails = FXCollections.observableArrayList();
        showMap.clear();

        for (Show show : this.aShowOptions) {
            showDetails.add(show.toString());
            showMap.put(show.toString(), show);
        }

        this.showListView.setItems(showDetails);
    }

    @FXML
    protected void onBuyTicketButtonClick(ActionEvent pEvent) {
        String selectedShowDetails = (String) showListView.getSelectionModel().getSelectedItem();

        if (selectedShowDetails == null) {
            AlertHelper alert = new AlertHelper("Please select a show before buying a ticket.");
            alert.executeWarningAlert();
            return;
        }

        // Get the corresponding Show object
        Show selectedShow = showMap.get(selectedShowDetails);

        if (selectedShow != null) {
            // Pass the show ID to the Ticket constructor
            ETicket eTicket = new ETicket(selectedShow, aLoggedClient);

            try {
                ReadObjects reader = new ReadObjects("etickets.ser");
                List<Object> rawObjects = reader.read();
                List<ETicket> eTicketList = rawObjects.stream()
                        .filter(ETicket.class::isInstance)
                        .map(ETicket.class::cast)
                        .collect(Collectors.toList());

                eTicketList.add(eTicket);

                // Write the updated list back to the file
                WriteObjects writer = new WriteObjects("etickets.ser");
                writer.write(eTicketList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            } catch (IOException | ClassNotFoundException e) {
                AlertHelper errorAlert = new AlertHelper("Error saving ETicket: " + e.getMessage());
                errorAlert.executeErrorAlert();
            }

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/e-ticket-view.fxml")));
                Parent root = fxmlLoader.load();
                ETicketViewController controller = fxmlLoader.getController();
                controller.setETicketView(eTicket);

                Scene scene = new Scene(root);
                Stage currentStage = (Stage) ((javafx.scene.Node) pEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("ETicket Booking Confirmation");
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertHelper alert = new AlertHelper("Selected show not found. Please try again.");
            alert.executeWarningAlert();
        }
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
