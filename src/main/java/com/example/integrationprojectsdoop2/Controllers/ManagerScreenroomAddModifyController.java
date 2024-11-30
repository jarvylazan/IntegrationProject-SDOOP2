package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Movie;
import com.example.integrationprojectsdoop2.Models.Screenroom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerScreenroomAddModifyController implements ModifyController<Screenroom>{

    @FXML
    public TextField NameTextField;


    @Override
    public void initializeData(Screenroom screenroom) {
        NameTextField.setText(screenroom.getScreenroom_Name());
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
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