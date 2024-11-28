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
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Shows");
        stage.setScene(scene);
        stage.show();
    }
    public void onScreeningRoomsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Screening Rooms");
        stage.setScene(scene);
        stage.show();
    }

    public void onShowtimesButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

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
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource(("management-view.fxml")));
        Parent root = fxmlLoader.load();

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
