package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Screenroom;
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

public class ManagerScreenroomAddModifyController implements ModifyController<Screenroom>{

    @FXML
    private TextField NameTextField;

    private Screenroom currentScreenroom;

    public void initializeData(Screenroom screenroom) {
        if (screenroom != null) {
            this.currentScreenroom = screenroom;
            NameTextField.setText(screenroom.getScreenroom_Name());
        } else {
            this.currentScreenroom = null;
            NameTextField.clear();
        }
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            // Read the list of existing showtimes
            ReadObjects reader = new ReadObjects("screenrooms.ser");
            List<Object> rawObjects = reader.read();
            List<Screenroom> screenroomList = rawObjects.stream()
                    .filter(Screenroom.class::isInstance)
                    .map(Screenroom.class::cast)
                    .collect(Collectors.toList());

            boolean isNewScreenroom = (currentScreenroom == null);

            if (isNewScreenroom) {
                // If this is a new showtime, create and add it to the list
                currentScreenroom = new Screenroom();
                screenroomList.add(currentScreenroom);
            }

            // Update the details of the current showtime
            currentScreenroom.setScreenroom_Name(NameTextField.getText().trim());

            if (!isNewScreenroom) {
                // Find the existing showtime and replace it
                for (int i = 0; i < screenroomList.size(); i++) {
                    if (screenroomList.get(i).getAScreenroom_ID().equals(currentScreenroom.getAScreenroom_ID())) {
                        screenroomList.set(i, currentScreenroom);
                        break;
                    }
                }
            }

            // Write the updated list back to the file
            WriteObjects writer = new WriteObjects("screenrooms.ser");
            writer.write(screenroomList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            // Show success message
            AlertHelper successAlert = new AlertHelper(isNewScreenroom ? "New showtime added successfully!" : "Showtime updated successfully!");
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
            // Load view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Screening Rooms","screenrooms.ser","manager-screen-room-add-modify-view.fxml");

            // Create a new scene for the view
            Scene newScene = new Scene(managementView);

            // Set the new scene to the current stage
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            System.err.println("Error loading the Login-View.fxml: " + e.getMessage());
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
