package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.Show;
import com.example.integrationprojectsdoop2.MovieTheatreApplication;
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
import java.util.*;

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

        // Update the top label with the user's name
        updateWelcomeLabel();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pSerializedFileName))) {
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

        // Populate ListView with today's shows
        updateMovieListView(LocalDate.now());

        // Add a listener to DatePicker for dynamically updating the ListView
        this.movieDatePicker.valueProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                updateMovieListView(newValue);
            }
        });
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
    public void initialize() {
        // Set Date Picker value to today's date.
        this.movieDatePicker.setValue(LocalDate.now());
    }


    /**
     * Updates the ListView to display movie titles for the selected date.
     *
     * @param selectedDate The date to filter shows.
     */
    private void updateMovieListView(LocalDate selectedDate) {
        ObservableList<String> movieTitles = FXCollections.observableArrayList();

        Set<String> uniqueMovieTitles = new HashSet<>(); // For unique movie titles
        boolean hasMoviesForDate = false;

        for (Show show : this.aShowsList) {
            if (show.getShowDate() != null && show.getShowDate().equals(selectedDate)) {
                // Filter by date and add unique movie titles
                if (uniqueMovieTitles.add(show.getMovie().getAMovie_Title())) {
                    movieTitles.add(show.getMovie().getAMovie_Title());
                }
                hasMoviesForDate = true; // At least one movie is available
            }
        }

        if (!hasMoviesForDate) {
            movieTitles.add("There are no movies available for this date.");
        }

        this.movieListView.setItems(movieTitles);
    }

    @FXML
    protected void onSeeShowOptionsButtonClick(ActionEvent pEvent) {
        // Get the selected movie title from the ListView
        String selectedMovieTitle = (String) this.movieListView.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = this.movieDatePicker.getValue();

        // Check if a movie title and a valid date are selected
        if (selectedMovieTitle == null) {
            AlertHelper alert = new AlertHelper("Please select a movie title from the list.");
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
            AlertHelper alert = new AlertHelper("No shows available for the selected movie and date.");
            alert.executeWarningAlert();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/movie-shows-view.fxml")));
            Parent root = fxmlLoader.load();
            MovieShowsController controller = fxmlLoader.getController();
            controller.setMovieShowsView(filteredShows.getFirst(), aLoggedClient, filteredShows);

            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((javafx.scene.Node) pEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Movie Shows");
            currentStage.setScene(scene);
            currentStage.show();
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
