package com.example.integrationprojectsdoop2.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReportViewController {

    @FXML
    public Label headerName;

    @FXML
    public AnchorPane salesReportView;

    public void setHeaderName(String pText) {
        headerName.setText(pText);
    }


    public void onBackButtonClick() {
        Stage stage = (Stage) salesReportView.getScene().getWindow();
        stage.close();
    }
}
