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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for the movie shows view.
 * Manages the display and functionality related to shows and ticket booking.
 */
public class MovieShowsController {

    /**
     * The file path to the e-ticket view FXML file.
     */
    private static final String ETICKET_VIEW_PATH = "/com/example/integrationprojectsdoop2/e-ticket-view.fxml";

    /**
     * The file path to the e-ticket view FXML file.
     */
    private static final String CLIENT_DASHBOARD_VIEW_PATH = "/com/example/integrationprojectsdoop2/client-dashboard-view.fxml";

    /**
     * Map storing show details as strings mapped to their corresponding Show objects.
     */
    private Map<String, Show> aShowMap = new HashMap<>();

    /**
     * List of available show options.
     */
    private List<Show> aShowOptions = new ArrayList<>();

    /**
     * Logged-in client interacting with the application.
     */
    private Client aLoggedClient;

    /**
     * The currently selected show.
     */
    private Show aShow;

    /** ListView to display shows. */
    @FXML
    private ListView<String> showListView;

    /** Title Label to display the movie and date selected. */
    @FXML
    private Label movieTitleAndDateLabel;


    /**
     * Sets the data for the movie shows view and updates UI components.
     *
     * @param pShow         The selected movie show.
     * @param pLoggedClient The logged-in client.
     * @param pShowOptions  The list of available show options.
     */
    public void setMovieShowsView(Show pShow, Client pLoggedClient, List<Show> pShowOptions) {
        this.aShowOptions = pShowOptions;
        this.aLoggedClient = pLoggedClient;
        this.aShow = pShow;

        updateMovieTitleAndDateLabel();
        updateShowListView();
    }

    /**
     * Updates the label displaying the movie title and date.
     */
    private void updateMovieTitleAndDateLabel() {
        this.movieTitleAndDateLabel.setText(this.aShow.getMovie().getAMovie_Title() + ", " + this.aShow.getShowDate());
    }

    /**
     * Updates the ListView to display the available show options.
     */
    private void updateShowListView() {
        ObservableList<String> showDetails = FXCollections.observableArrayList();
        aShowMap.clear();

        for (Show show : this.aShowOptions) {
            showDetails.add(show.toString());
            aShowMap.put(show.toString(), show);
        }

        this.showListView.setItems(showDetails);
    }

    /**
     * Handles the action when the "Buy Ticket" button is clicked.
     * Creates and saves an ETicket for the selected show and navigates to the ETicket view.
     *
     * @param pEvent The action event triggered by the button click.
     */
    @FXML
    protected void onBuyTicketButtonClick(ActionEvent pEvent) {
        String selectedShowDetails = showListView.getSelectionModel().getSelectedItem();

        if (selectedShowDetails == null) {
            AlertHelper alert = new AlertHelper("Please select a show before buying a ticket.");
            alert.executeWarningAlert();
            return;
        }

        Show selectedShow = aShowMap.get(selectedShowDetails);

        if (selectedShow != null) {
            ETicket eTicket = new ETicket(selectedShow, aLoggedClient);

            try {
                ReadObjects reader = new ReadObjects("etickets.ser");
                List<ETicket> eTicketList = reader.read().stream()
                        .filter(ETicket.class::isInstance)
                        .map(ETicket.class::cast)
                        .collect(Collectors.toList());

                eTicketList.add(eTicket);

                WriteObjects writer = new WriteObjects("etickets.ser");
                writer.write(eTicketList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            } catch (IOException | ClassNotFoundException e) {
                AlertHelper errorAlert = new AlertHelper("Error saving ETicket: " + e.getMessage());
                errorAlert.executeErrorAlert();
            }

            navigateToETicketView(pEvent, eTicket);

        } else {
            AlertHelper alert = new AlertHelper("Selected show not found. Please try again.");
            alert.executeWarningAlert();
        }
    }

    /**
     * Navigates to the ETicket view.
     *
     * @param pEvent  The triggering action event.
     * @param eTicket The generated ETicket to display.
     */
    private void navigateToETicketView(ActionEvent pEvent, ETicket eTicket) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(ETICKET_VIEW_PATH));
            Parent root = fxmlLoader.load();
            ETicketViewController controller = fxmlLoader.getController();
            controller.setETicketView(eTicket);

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((javafx.scene.Node) pEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("ETicket Booking Confirmation");
            currentStage.setScene(scene);
            currentStage.show();

        } catch (IOException e) {
            AlertHelper alert = new AlertHelper("Error navigating to the ETicket view: " + e.getMessage());
            alert.executeErrorAlert();
        }
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * Navigates back to the Client Dashboard view.
     *
     * @param pActionEvent The action event triggered by the button click.
     */
    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CLIENT_DASHBOARD_VIEW_PATH));
            Parent root = loader.load();

            ClientDashboardController controller = loader.getController();
            controller.setClientDashboardView("shows.ser", aLoggedClient);

            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Client Dashboard");
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (IOException e) {
            AlertHelper alert = new AlertHelper("Error loading the Client Dashboard: " + e.getMessage());
            alert.executeErrorAlert();
        }
    }
}
