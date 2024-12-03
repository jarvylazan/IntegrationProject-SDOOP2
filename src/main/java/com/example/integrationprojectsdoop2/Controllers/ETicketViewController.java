package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ETicket;
import com.example.integrationprojectsdoop2.Models.Showtime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

public class ETicketViewController {

    private ETicket aETicket;

    @FXML
    private Label eTicketLabel;

    @FXML
    public void initialize() {
        this.eTicketLabel.setText("");
    }

    public void setETicketView(ETicket pTicket) {
        this.aETicket = pTicket;

        

        updateETicketLabel();
    }

    private void updateETicketLabel() {

    }

    private void updateETicketList() {
        try {
            // Read the list of existing showtimes
            ReadObjects reader = new ReadObjects("showtimes.ser");
            List<Object> rawObjects = reader.read();
            List<ETicket> eTicketList = rawObjects.stream()
                    .filter(ETicket.class::isInstance)
                    .map(ETicket.class::cast)
                    .collect(Collectors.toList());

            eTicketList.add(this.aETicket);

            // Write the updated list back to the file
            WriteObjects writer = new WriteObjects("etickets.ser");
            writer.write(eTicketList.stream().map(s -> (Object) s).collect(Collectors.toList()));

        } catch (IOException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving showtime: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent pEvent) {

    }
}
