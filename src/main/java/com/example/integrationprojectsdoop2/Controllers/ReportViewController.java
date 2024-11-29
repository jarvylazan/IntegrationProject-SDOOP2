package com.example.integrationprojectsdoop2.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportViewController {

    @FXML
    public Label headerName;

    public void setHeaderName(String pText) {
        headerName.setText(pText);
    }
}
