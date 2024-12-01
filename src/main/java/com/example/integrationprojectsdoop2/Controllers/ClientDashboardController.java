package com.example.integrationprojectsdoop2.Controllers;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private DatePicker movieDatePicker;

    @FXML
    private ListView movieListView;

    @FXML
    private Button seeShowOptionsButton;

    @FXML
    private Button signOutButton;

    private final List<Show> aShowsList;

    private final List<User> aClientsList;

    public ClientDashboardController() {
        this.aClientsList = UserManager.getInstance().getaClientsList();
        aShowsList = showsReader("shows.ser");
    }

    private void initialize() {
        ObservableList<String> movieTitles = FXCollections.observableArrayList();

        for (Show show : aShowsList) {
            movieTitles.add(show.getMovie().getAMovie_Title());
        }

        this.movieListView.setItems(movieTitles);
    }

    private List<Show> showsReader(String pFilename) {
        List<Show> shows = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read();

            // Safely cast raw objects to User instances
            shows = rawObjects.stream()
                    .filter(Show.class::isInstance)
                    .map(Show.class::cast)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace(); // Log exceptions for debugging
        }

        return new ArrayList<>(shows);
    }

    private void updateWelcomeLabel() {

    }
    @FXML
    protected void onSignOutButtonClick(ActionEvent event) {
        try {
            // Load the Login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent loginRoot = loader.load();

            // Set the new scene for the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
            // Optionally display an error dialog or message to the user
        }
    }
}
