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

public class ManagementDashboardController {

    @FXML
    public AnchorPane editMovieView;

    public void onShowsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Shows","shows.ser","manager-show-add-modify-view.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Shows");
        stage.setScene(scene);
        stage.show();
    }
    public void onScreeningRoomsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Screening Rooms","screenrooms.ser","manager-screen-room-add-modify-view.fxml");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Screening Rooms");
        stage.setScene(scene);
        stage.show();
    }

    public void onShowtimesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Showtimes","showtimes.ser","manager-showtime-add-modify-view.fxml");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Showtimes");
        stage.setScene(scene);
        stage.show();
    }

    public void onReportsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("sales-report-view.fxml")));
        Parent root = fxmlLoader.load();


        ReportViewController controller = fxmlLoader.getController();
        controller.setHeaderName("TJS Sales Report");
        controller.setManagementView("movies.ser");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sales Report");
        stage.setScene(scene);
        stage.show();
    }

    public void onMoviesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Movies","movies.ser","manager-edit-movie-view.fxml");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

    public void onClientListButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("sales-report-view.fxml")));
        Parent root = fxmlLoader.load();

        ReportViewController controller = fxmlLoader.getController();
        controller.setHeaderName("TJS User Report");
        controller.setManagementView("clients.ser");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sales Report");
        stage.setScene(scene);
        stage.show();
    }

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
