package com.example.integrationprojectsdoop2.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManagementDashboardController {

    @FXML
    public AnchorPane editMovieView;

    public void onShowsButtonClick(ActionEvent actionEvent) {

    }

    public void onReportsButtonClick(ActionEvent actionEvent) {
    }

    public void onScreeningRoomsButtonClick(ActionEvent actionEvent) {
    }

    public void onMoviesButtonClick(ActionEvent actionEvent) {
    }

    public void onShowtimesButtonClick(ActionEvent actionEvent) {
    }

    public void onClientListButtonClick(ActionEvent actionEvent) {
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) editMovieView.getScene().getWindow();
        stage.close();
    }
}
