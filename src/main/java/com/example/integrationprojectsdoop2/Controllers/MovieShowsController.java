package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Models.Show;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class MovieShowsController {

    private Show aShow;

    @FXML
    private ListView showListView;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Button backButton;

    @FXML
    private Label movieTitleAndDateLabel;

    @FXML
    public void initialize() {

    }

    public void setMovieShowsView(Show pShow) {
        this.aShow = pShow;

        updateMovieTitleAndDateLabel();
    }

    private void updateMovieTitleAndDateLabel() {
        movieTitleAndDateLabel.setText(aShow.getMovie().getAMovie_Title() + ", " + aShow.getShowDate());
    }

    public void onBuyTicketButtonClick() {
    }

    public void onBackButtonClick() {
    }
}
