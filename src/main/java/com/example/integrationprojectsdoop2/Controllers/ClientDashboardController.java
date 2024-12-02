package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.Show;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private Client aLoggedClient;
    private List<Show> aShowsList;
    @FXML
    private Label welcomeLabel;

    @FXML
    private DatePicker movieDatePicker;

    @FXML
    private ListView movieListView;


    public void setClientDashboardView(String pSerializedFileName, Client pClient) {
        this.aShowsList = showsReader(pSerializedFileName);
        this.aLoggedClient = pClient;

        initialize();

        String filePath = "shows.ser"; // Replace with your file's path

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object deserializedObject = ois.readObject();

            // If the file contains a list
            if (deserializedObject instanceof List<?> list) {
                list.forEach(System.out::println);
            } else {
                System.out.println("Deserialized Object: " + deserializedObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    private void initialize() {
        // Set Date Picker value to today's date.
        this.movieDatePicker.setValue(LocalDate.now());

        // Update the top label with the user's name
        updateWelcomeLabel();

        // Populate ListView with today's shows
        updateMovieListView(LocalDate.now());

        // Add a listener to DatePicker for dynamically updating the ListView
        this.movieDatePicker.valueProperty().addListener((_, _, newValue) -> {
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

        for (Show show : this.aShowsList) {
            if (show.getShowDate() != null && show.getShowDate().equals(selectedDate)) { // Filter by date
                movieTitles.add(show.getMovie().getAMovie_Title());
            }
        }

        // Show a message if no movies are available for the selected date
        if (movieTitles.isEmpty()) {
            AlertHelper alert = new AlertHelper("No movies available for that date.");
            alert.executeWarningAlert();
        }

        this.movieListView.setItems(movieTitles);
    }

    @FXML
    protected void onSeeShowOptionsButtonClick(ActionEvent event) {
        // Get the selected movie title from the ListView
        String selectedMovieTitle = (String) this.movieListView.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = this.movieDatePicker.getValue();

        // Check if a movie title and a valid date are selected
        if (selectedMovieTitle == null) {
            AlertHelper alert = new AlertHelper("Please select a movie title from the list. ");
            alert.executeWarningAlert();
            return;
        }

        if (selectedDate == null) {
            AlertHelper alert = new AlertHelper("Please select a date.");
            alert.executeWarningAlert();
            return;
        }

        // Filter shows based on the selected movie title and date
        List<Show> filteredShows = this.aShowsList.stream()
                .filter(show -> show.getMovie().getAMovie_Title().equals(selectedMovieTitle)
                        && show.getShowDate().equals(selectedDate))
                .toList();

        // If no shows match, inform the user
        if (filteredShows.isEmpty()) {
            System.out.println("No shows available for the selected movie and date.");
            return;
        }

        try {
            // Load the new view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-shows-view.fxml"));
            Parent showsRoot = loader.load();

            // Pass data to the new controller
            //MovieShowsViewController controller = loader.getController();
            //controller.setMovieShowsView(filteredShows);

            // Set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(showsRoot));
            stage.setTitle("Movie Shows");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    public void updateWelcomeLabel() {
        this.welcomeLabel.setText("Welcome, " + this.aLoggedClient.getaUser_Name() + "!");
    }
}
