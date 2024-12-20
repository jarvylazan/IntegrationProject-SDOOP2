package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.MovieTheatreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the management dashboard view in the Movie Theatre Application.
 * Provides navigation to various management views such as Shows, Screening Rooms,
 * Showtimes, Movies, and Reports.
 *
 * @author Jarvy Lazan
 */
public class ManagementDashboardController {

    @FXML
    public AnchorPane editMovieView;

    /**
     * Opens the management view for managing shows.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onShowsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Shows", "shows.ser", "manager-show-add-modify-view.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Shows");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the management view for managing screening rooms.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onScreeningRoomsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Screening Rooms", "screenrooms.ser", "manager-screen-room-add-modify-view.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Screening Rooms");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the management view for managing showtimes.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onShowtimesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Showtimes", "showtimes.ser", "manager-showtime-add-modify-view.fxml");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Showtimes");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the sales report view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onReportsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("sales-report-view.fxml")));
        Parent root = fxmlLoader.load();

        ReportViewController controller = fxmlLoader.getController();
        controller.setHeaderName("TJS Sales Report");
        controller.setManagementView("etickets.ser");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sales Report");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the management view for managing movies.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onMoviesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Movies", "movies.ser", "manager-edit-movie-view.fxml");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the client list report view.
     *
     * @throws IOException if the FXML file cannot be loaded.
     * @author Jarvy Lazan
     */
    public void onClientListButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("sales-report-view.fxml")));
        Parent root = fxmlLoader.load();

        ReportViewController controller = fxmlLoader.getController();
        controller.setHeaderName("TJS User Report");
        controller.setManagementView("clients.ser");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Client list");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates back to the login view.
     *
     * @param pActionEvent the event triggered by clicking the back button.
     * @author Jarvy Lazan
     */
    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load the login view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/login-view.fxml"));
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
