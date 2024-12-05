package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Screenroom;
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

/**
 * Controller for adding and modifying Screenrooms in the application.
 * Provides functionality to create, edit, validate, and save Screenroom data.
 *
 * @author Jarvy Lazan
 */
public class ManagerScreenroomAddModifyController implements ModifyController<Screenroom> {

    @FXML
    private TextField NameTextField;

    private Screenroom aCurrentScreenroom;

    /**
     * Initializes the form with the provided Screenroom's data.
     * Clears the form if no Screenroom is provided.
     *
     * @param pScreenroom the Screenroom to populate in the form, or null to clear the form.
     * @author Jarvy Lazan
     */
    public void initializeData(Screenroom pScreenroom) {
        if (pScreenroom != null) {
            this.aCurrentScreenroom = pScreenroom;
            NameTextField.setText(pScreenroom.getScreenroom_Name());
        } else {
            this.aCurrentScreenroom = null;
            NameTextField.clear();
        }
    }

    /**
     * Handles the save button click event. Validates inputs, checks for duplicates,
     * updates the Screenroom list, sorts it alphabetically, and saves it back to the file.
     *
     * @param pActionEvent the event triggered by clicking the save button.
     * @throws IOException              if an error occurs during file operations.
     * @throws IllegalArgumentException if input validation fails.
     * @throws ClassNotFoundException   if the deserialization fails.
     * @author Jarvy Lazan
     */
    public void onSaveButtonClick(ActionEvent pActionEvent) {
        try {
            // Read the list of existing screenrooms
            ReadObjects reader = new ReadObjects("screenrooms.ser");
            List<Object> rawObjects = reader.read();
            List<Screenroom> screenroomList = rawObjects.stream()
                    .filter(Screenroom.class::isInstance)
                    .map(Screenroom.class::cast)
                    .collect(Collectors.toList());

            boolean isNewScreenroom = (aCurrentScreenroom == null);

            String enteredName = NameTextField.getText().trim();

            // Check if a screenroom with the same name already exists
            boolean isDuplicate = screenroomList.stream()
                    .anyMatch(screenroom -> screenroom.getScreenroom_Name().equalsIgnoreCase(enteredName) &&
                            (isNewScreenroom || !screenroom.getScreenroom_ID().equals(aCurrentScreenroom.getScreenroom_ID())));

            if (isDuplicate) {
                AlertHelper errorAlert = new AlertHelper("A screenroom with the same name already exists. Please use a unique name.");
                errorAlert.executeErrorAlert();
                return;
            }

            if (isNewScreenroom) {
                // If this is a new screenroom, create and add it to the list
                aCurrentScreenroom = new Screenroom();
                screenroomList.add(aCurrentScreenroom);
            }

            // Update the details of the current screenroom
            aCurrentScreenroom.setScreenroom_Name(enteredName);

            if (!isNewScreenroom) {
                // Find the existing screenroom and replace it
                for (int i = 0; i < screenroomList.size(); i++) {
                    if (screenroomList.get(i).getScreenroom_ID().equals(aCurrentScreenroom.getScreenroom_ID())) {
                        screenroomList.set(i, aCurrentScreenroom);
                        break;
                    }
                }
            }

            // Sort the screenroom list by name (case-insensitive, ignoring leading/trailing spaces)
            screenroomList.sort((sr1, sr2) -> {
                String name1 = sr1.getScreenroom_Name() != null ? sr1.getScreenroom_Name().trim().toLowerCase() : "";
                String name2 = sr2.getScreenroom_Name() != null ? sr2.getScreenroom_Name().trim().toLowerCase() : "";
                return name1.compareTo(name2);
            });

            // Write the sorted list back to the file
            WriteObjects writer = new WriteObjects("screenrooms.ser");
            writer.write(screenroomList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            // Show success message
            AlertHelper successAlert = new AlertHelper(isNewScreenroom ? "New screenroom added successfully!" : "Screenroom updated successfully!");
            successAlert.executeSuccessAlert();

            // Navigate back to the management view
            onBackButtonClick(pActionEvent);

        } catch (IOException | IllegalArgumentException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving screenroom: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    /**
     * Handles the back button click event. Navigates back to the management view.
     *
     * @param pActionEvent the event triggered by clicking the back button.
     * @author Jarvy Lazan
     */
    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Screening Rooms", "screenrooms.ser", "manager-screen-room-add-modify-view.fxml");

            // Create a new scene for the view
            Scene newScene = new Scene(managementView);

            // Set the new scene to the current stage
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            System.err.println("Error loading the management-view.fxml: " + e.getMessage());
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
