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

/**
 * Controller for managing the addition and modification of showtimes.
 * Allows adding, editing, and saving showtimes, with validation and sorting functionality.
 *
 * @author Jarvy Lazan
 */
public class ManagerShowtimeAddModifyController implements ModifyController<Showtime> {

    @FXML
    public TextField TimeTextField;

    private Showtime pCurrentShowtime; // The showtime being modified or added

    /**
     * Initializes the data for the given showtime, or clears the form if the showtime is null.
     *
     * @param pShowtime the showtime to populate in the form, or null to clear the form.
     * @author Jarvy Lazan
     */
    public void initializeData(Showtime pShowtime) {
        if (pShowtime != null) {
            this.pCurrentShowtime = pShowtime;
            TimeTextField.setText(pShowtime.getShowtimeTime());
        } else {
            this.pCurrentShowtime = null;
            TimeTextField.clear();
        }
    }

    /**
     * Handles the save button click. Validates the entered showtime, checks for duplicates,
     * updates or creates a showtime, sorts the list of showtimes, and saves the list to a file.
     *
     * @param pActionEvent the event triggered by clicking the save button.
     * @throws IOException           if an error occurs while reading or writing to the file.
     * @throws IllegalArgumentException if the entered time format is invalid.
     * @throws ClassNotFoundException if deserialization fails during reading objects from the file.
     * @author Jarvy Lazan
     */
    public void onSaveButtonClick(ActionEvent pActionEvent) {
        try {
            // Read the list of existing showtimes
            ReadObjects reader = new ReadObjects("showtimes.ser");
            List<Object> rawObjects = reader.read();
            List<Showtime> showtimeList = rawObjects.stream()
                    .filter(Showtime.class::isInstance)
                    .map(Showtime.class::cast)
                    .collect(Collectors.toList());

            // Get the entered showtime time
            String enteredTime = TimeTextField.getText().trim();

            // Validate the entered time
            if (enteredTime.isEmpty()) {
                AlertHelper errorAlert = new AlertHelper("Showtime cannot be empty.");
                errorAlert.executeErrorAlert();
                return;
            }

            // Check for duplicates
            boolean duplicateExists = showtimeList.stream()
                    .anyMatch(showtime -> normalizeTime(showtime.getShowtimeTime()).equals(normalizeTime(enteredTime)) &&
                            (pCurrentShowtime == null || !showtime.getShowtimeID().equals(pCurrentShowtime.getShowtimeID())));

            if (duplicateExists) {
                AlertHelper errorAlert = new AlertHelper("A showtime with the same time already exists.");
                errorAlert.executeErrorAlert();
                return;
            }

            boolean isNewShowtime = (pCurrentShowtime == null);

            if (isNewShowtime) {
                // If this is a new showtime, create and add it to the list
                pCurrentShowtime = new Showtime();
                showtimeList.add(pCurrentShowtime);
            }

            // Update the details of the current showtime
            String normalizedTime = normalizeTime(enteredTime);
            pCurrentShowtime.setShowtimeTime(normalizedTime);


            if (!isNewShowtime) {
                // Find the existing showtime and replace it
                for (int i = 0; i < showtimeList.size(); i++) {
                    if (showtimeList.get(i).getShowtimeID().equals(pCurrentShowtime.getShowtimeID())) {
                        showtimeList.set(i, pCurrentShowtime);
                        break;
                    }
                }
            }

            // Sort the showtime list by time (earliest to latest)
            showtimeList.sort((s1, s2) -> {
                try {
                    String normalizedTime1 = normalizeTime(s1.getShowtimeTime());
                    String normalizedTime2 = normalizeTime(s2.getShowtimeTime());
                    return java.time.LocalTime.parse(normalizedTime1)
                            .compareTo(java.time.LocalTime.parse(normalizedTime2));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid time format: " + e.getMessage());
                }
            });




            // Write the sorted list back to the file
            WriteObjects writer = new WriteObjects("showtimes.ser");
            writer.write(showtimeList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            // Show success message
            AlertHelper successAlert = new AlertHelper(isNewShowtime ? "New showtime added successfully!" : "Showtime updated successfully!");
            successAlert.executeSuccessAlert();

            // Navigate back to the management view
            onBackButtonClick(pActionEvent);

        } catch (IOException | IllegalArgumentException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving showtime: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    /**
     * Normalizes a time string to HH:mm format.
     *
     * @param pTime the input time string in H:mm or HH:mm format.
     * @return the normalized time string in HH:mm format.
     * @author Jarvy Lazan
     */
    private static String normalizeTime(String pTime) {
        String[] parts = pTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Handles the back button click. Navigates back to the management view.
     *
     * @param pActionEvent the event triggered by clicking the back button.
     * @throws IOException if an error occurs while loading the management view FXML file.
     * @author Jarvy Lazan
     */
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
