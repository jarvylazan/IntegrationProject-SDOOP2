package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Showtime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerShowtimeAddModifyController implements ModifyController<Showtime> {

    @FXML
    public TextField TimeTextField;

    private Showtime currentShowtime; // The showtime being modified or added

    public void initializeData(Showtime showtime) {
        if (showtime != null) {
            this.currentShowtime = showtime;
            TimeTextField.setText(showtime.getaShowtimeTime());
        } else {
            this.currentShowtime = null;
            TimeTextField.clear();
        }
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            // Read the list of existing showtimes
            ReadObjects reader = new ReadObjects("showtimes.ser");
            List<Object> rawObjects = reader.read();
            List<Showtime> showtimeList = rawObjects.stream()
                    .filter(Showtime.class::isInstance)
                    .map(Showtime.class::cast)
                    .collect(Collectors.toList());

            boolean isNewShowtime = (currentShowtime == null);

            if (isNewShowtime) {
                // If this is a new showtime, create and add it to the list
                currentShowtime = new Showtime();
                showtimeList.add(currentShowtime);
            }

            // Update the details of the current showtime
            currentShowtime.setaShowtimeTime(TimeTextField.getText().trim());

            if (!isNewShowtime) {
                // Find the existing showtime and replace it
                for (int i = 0; i < showtimeList.size(); i++) {
                    if (showtimeList.get(i).getaShowtimeID().equals(currentShowtime.getaShowtimeID())) {
                        showtimeList.set(i, currentShowtime);
                        break;
                    }
                }
            }

            // Write the updated list back to the file
            WriteObjects writer = new WriteObjects("showtimes.ser");
            writer.write(showtimeList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            // Show success message
            AlertHelper successAlert = new AlertHelper(isNewShowtime ? "New showtime added successfully!" : "Showtime updated successfully!");
            successAlert.executeSuccessAlert();

            // Navigate back to the management view
            onBackButtonClick(actionEvent);

        } catch (IOException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving showtime: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load the management view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Showtimes", "showtimes.ser", "manager-showtime-add-modify-view.fxml");

            // Set the scene
            Scene newScene = new Scene(managementView);
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
