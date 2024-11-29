package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.MovieTheatreApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagementDashboardController {

    @FXML
    public AnchorPane editMovieView;

    public void onShowsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/management-view.fxml")));
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
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/management-view.fxml")));
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
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/management-view.fxml")));
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

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sales Report");
        stage.setScene(scene);
        stage.show();
    }

    public void onMoviesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("/com/example/integrationprojectsdoop2/management-view.fxml")));
        Parent root = fxmlLoader.load();
        ManagementViewController controller = fxmlLoader.getController();
        controller.setManagementView("Movies","movies.ser","manager-edit-movie-view.fxml");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

    public void onClientListButtonClick() {
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) editMovieView.getScene().getWindow();
        stage.close();
    }
}
