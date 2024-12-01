package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.Show;
import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.Models.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDashboardController {

    private final List<Show> aShowsList;

    private List<User> aClientsList;

    @FXML
    private Label welcomeLabel;

    @FXML
    private DatePicker movieDatePicker;

    @FXML
    private ListView movieListView;

    /**
     * Constructor initializes the clients list and loads shows from the serialized file.
     */
    public ClientDashboardController() {
        this.aClientsList = UserManager.getInstance().getaClientsList();
        this.aShowsList = showsReader("shows.ser");
    }

    private List<Show> showsReader(String pFilename) {
        List<Show> shows = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read();

            // Safely cast raw objects to Show instances
            shows = rawObjects.stream()
                    .filter(Show.class::isInstance)
                    .map(Show.class::cast)
                    .toList();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading shows from file: " + e.getMessage());
            e.printStackTrace();
        }

        return shows;
    }


    @FXML
    private void initialize() {
        // Populate ListView with today's shows
        updateMovieListView(LocalDate.now());

        // Add a listener to DatePicker for dynamically updating the ListView
        movieDatePicker.valueProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                updateMovieListView(newValue);
            }
        });
    }


    /**
     * Updates the ListView to display movie titles for the selected date.
     *
     * @param selectedDate The date to filter shows.
     */
    private void updateMovieListView(LocalDate selectedDate) {
        ObservableList<String> movieTitles = FXCollections.observableArrayList();

        for (Show show : aShowsList) {
            if (show.getShowDate().equals(selectedDate)) { // Filter by date
                movieTitles.add(show.getMovie().getAMovie_Title());
            }
        }

        // Show a message if no movies are available for the selected date
        if (movieTitles.isEmpty()) {
            AlertHelper alert = new AlertHelper("No movies available for that date.");
            alert.executeWarningAlert();
        }

        movieListView.setItems(movieTitles);
    }

    public void onSeeShowOptionsButtonClick() {}

    /**
     * Handles the "Sign out" button click event.
     * Navigates the user back to the login view.
     *
     * @param pActionEvent the action event triggered by the button click.
     * @author Mohammad Tarin Wahidi
     */
    public void onSignOutButtonClick(ActionEvent pActionEvent) {
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
