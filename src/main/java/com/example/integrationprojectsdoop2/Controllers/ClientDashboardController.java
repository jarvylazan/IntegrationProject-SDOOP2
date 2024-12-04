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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Controller for the client dashboard view.
 *
 * This controller manages the interaction between the client and the application,
 * including viewing movies by date, navigating between views, and signing out.
 *
 * @author Mohammad Tarin Wahidi
 */
public class ClientDashboardController {
    /**
     * The file path to the login view FXML file.
     */
    private static final String LOGIN_VIEW_PATH = "/com/example/integrationprojectsdoop2/Login-View.fxml";

    /**
     * The file path to the movie shows view FXML file.
     */
    private static final String MOVIE_SHOWS_VIEW_PATH = "/com/example/integrationprojectsdoop2/movie-shows-view.fxml";



    /** The logged-in client. */
    private Client aLoggedClient;

    /** The list of shows loaded from the serialized file. */
    private List<Show> aShowsList;

    /** Label to display the welcome message. */
    @FXML
    private Label welcomeLabel;

    /** DatePicker for selecting a movie date. */
    @FXML
    private DatePicker movieDatePicker;

    /** ListView to display movie titles and details. */
    @FXML
    private ListView<String> movieListView;

    /**
     * Sets up the client dashboard view with the provided serialized file and client data.
     *
     * @param pSerializedFileName The name of the file containing serialized show data.
     * @param pClient             The logged-in client.
     */
    public void setClientDashboardView(String pSerializedFileName, Client pClient) {
        this.aShowsList = showsReader(pSerializedFileName);
        this.aLoggedClient = pClient;

        updateWelcomeLabel();
        updateMovieListView(LocalDate.now());

        this.movieDatePicker.valueProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                updateMovieListView(newValue);
            }
        });
    }

    /**
     * Reads the list of shows from the given serialized file.
     *
     * @param pFilename The filename of the serialized shows data.
     * @return A list of shows read from the file.
     */
    private List<Show> showsReader(String pFilename) {
        List<Show> shows = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read();

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

    /**
     * Initializes the controller. Sets the default date in the DatePicker to today's date.
     */
    @FXML
    public void initialize() {
        this.movieDatePicker.setValue(LocalDate.now());
    }

    /**
     * Updates the ListView to display movie titles and details for the selected date.
     *
     * @param selectedDate The selected date to filter movies.
     */
    private void updateMovieListView(LocalDate selectedDate) {
        ObservableList<String> movieTitles = FXCollections.observableArrayList();
        Set<String> uniqueMovieTitles = new HashSet<>();
        boolean hasMoviesForDate = false;

        for (Show show : this.aShowsList) {
            if (show.getShowDate() != null && show.getShowDate().equals(selectedDate)) {
                if (uniqueMovieTitles.add(show.getMovie().getAMovie_Title())) {
                    movieTitles.add("Title : " + show.getMovie().getAMovie_Title() +
                            "\nGenre : " + show.getMovie().getAMovie_Genre() +
                            "\nSynopsis : " + show.getMovie().getAMovie_Synopsis());
                }
                hasMoviesForDate = true;
            }
        }

        if (!hasMoviesForDate) {
            movieTitles.add("There are no movies available for this date.");
        }

        this.movieListView.setItems(movieTitles);
    }

    /**
     * Handles the "See Show Options" button click event.
     * Navigates to the view displaying available shows for the selected movie and date.
     *
     * @param pEvent The action event triggered by the button click.
     */
    @FXML
    protected void onSeeShowOptionsButtonClick(ActionEvent pEvent) {
        String selectedItem = this.movieListView.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = this.movieDatePicker.getValue();

        if (selectedItem == null) {
            new AlertHelper("Please select a movie from the list.").executeWarningAlert();
            return;
        }

        // Extract the title from the selected item's first line
        String selectedMovieTitle = selectedItem.split("\n")[0].replace("Title : ", "").trim();

        // Filter shows based on the extracted title and selected date
        List<Show> filteredShows = this.aShowsList.stream()
                .filter(show -> show.getMovie().getAMovie_Title().equals(selectedMovieTitle)
                        && show.getShowDate().equals(selectedDate))
                .toList();


        if (filteredShows.isEmpty()) {
            new AlertHelper("No shows available for the selected movie and date.").executeWarningAlert();
            return;
        }

        // Check if the selected date is in the past
        if (selectedDate.isBefore(LocalDate.now())) {
            new AlertHelper("The selected show date is in the past. Please select a valid date.").executeWarningAlert();
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(MOVIE_SHOWS_VIEW_PATH));
            Parent root = fxmlLoader.load();

            MovieShowsController controller = fxmlLoader.getController();
            controller.setMovieShowsView(filteredShows.get(0), aLoggedClient, filteredShows);

            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Movie Shows");
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Sign Out" button click event.
     * Navigates the user back to the login view.
     *
     * @param pActionEvent The action event triggered by the button click.
     */
    public void onSignOutButtonClick(ActionEvent pActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_VIEW_PATH));
            Parent loginView = loader.load();

            Scene newScene = new Scene(loginView);
            newScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

            Stage currentStage = (Stage) ((Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Log in");
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (Exception e) {
            System.err.println("Error loading the Login-View.fxml: " + e.getMessage());
            new AlertHelper(e.getMessage()).executeErrorAlert();
        }
    }

    /**
     * Updates the welcome label with the logged-in client's name.
     */
    public void updateWelcomeLabel() {
        this.welcomeLabel.setText("Welcome, " + this.aLoggedClient.getaUser_Name() + "!");
    }
}
