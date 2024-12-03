package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManagerShowAddModifyController implements ModifyController<Show> {

    @FXML
    private ComboBox<String> MovieComboBox;

    @FXML
    private ComboBox<String> ShowtimeComboBox;

    @FXML
    private ComboBox<String> ScreenroomComboBox;

    @FXML
    private DatePicker ShowDatePicker;

    private Show currentShow; // The show being edited or created

    @FXML
    public void initialize() {
        try {
            loadMovies();
            loadShowtimes();
            loadScreenrooms();
        } catch (IOException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Failed to load data: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    private void loadMovies() throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("movies.ser");
        List<Object> movies = reader.read();
        MovieComboBox.getItems().addAll(
                movies.stream()
                        .filter(Movie.class::isInstance)
                        .map(Movie.class::cast)
                        .map(Movie::getAMovie_Title)
                        .collect(Collectors.toList())
        );
    }

    private void loadShowtimes() throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("showtimes.ser");
        List<Object> showtimes = reader.read();
        ShowtimeComboBox.getItems().addAll(
                showtimes.stream()
                        .filter(Showtime.class::isInstance)
                        .map(Showtime.class::cast)
                        .map(Showtime::getaShowtimeTime)
                        .collect(Collectors.toList())
        );
    }

    private void loadScreenrooms() throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("screenrooms.ser");
        List<Object> screenrooms = reader.read();
        ScreenroomComboBox.getItems().addAll(
                screenrooms.stream()
                        .filter(Screenroom.class::isInstance)
                        .map(Screenroom.class::cast)
                        .map(Screenroom::getScreenroom_Name)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void initializeData(Show show) {
        if (show != null) {
            this.currentShow = show;
            MovieComboBox.setValue(show.getMovie().getAMovie_Title());
            ShowtimeComboBox.setValue(show.getShowtime().getaShowtimeTime());
            ScreenroomComboBox.setValue(show.getScreenroom().getScreenroom_Name());
            ShowDatePicker.setValue(show.getShowDate());
        }
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            // Ensure the file exists or create it with an empty list
            String filePath = "shows.ser";
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("File not found: " + filePath + ". Creating a new file...");
                WriteObjects writer = new WriteObjects(filePath);
                writer.write(new ArrayList<>()); // Write an empty list to the new file
            }

            // Read the existing list of shows from "shows.ser"
            ReadObjects reader = new ReadObjects(filePath);
            List<Object> rawObjects = reader.read();
            List<Show> showList = rawObjects.stream()
                    .filter(Show.class::isInstance)
                    .map(Show.class::cast)
                    .collect(Collectors.toList());

            boolean isNewShow = (currentShow == null);

            if (isNewShow) {
                // If it's a new show, create an instance and add it to the list
                currentShow = new Show();
                showList.add(currentShow);
            }

            // Validate ComboBox selections
            String selectedMovieTitle = MovieComboBox.getValue();
            String selectedShowtimeTime = ShowtimeComboBox.getValue();
            String selectedScreenroomName = ScreenroomComboBox.getValue();
            LocalDate selectedDate = ShowDatePicker.getValue();

            if (selectedMovieTitle == null || selectedShowtimeTime == null || selectedScreenroomName == null || selectedDate == null) {
                AlertHelper errorAlert = new AlertHelper("All fields, including the date, must be selected.");
                errorAlert.executeErrorAlert();
                return;
            }

            if (selectedDate.isBefore(LocalDate.now())) {
                AlertHelper errorAlert = new AlertHelper("The selected date cannot be in the past.");
                errorAlert.executeErrorAlert();
                return;
            }

            // Fetch the selected Movie, Showtime, and Screenroom
            Movie selectedMovie = getMovieByTitle(selectedMovieTitle);
            Showtime selectedShowtime = getShowtimeByTime(selectedShowtimeTime);
            Screenroom selectedScreenroom = getScreenroomByName(selectedScreenroomName);

            if (selectedMovie == null || selectedShowtime == null || selectedScreenroom == null) {
                AlertHelper errorAlert = new AlertHelper("Unable to find the selected items in the database.");
                errorAlert.executeErrorAlert();
                return;
            }

            // Update the show's details
            currentShow.setMovie(selectedMovie);
            currentShow.setShowtime(selectedShowtime);
            currentShow.setScreenroom(selectedScreenroom);
            currentShow.setShowDate(selectedDate);

            if (!isNewShow) {
                // Find the show with the same ID and replace it
                for (int i = 0; i < showList.size(); i++) {
                    if (showList.get(i).getaShowID().equals(currentShow.getaShowID())) {
                        showList.set(i, currentShow); // Replace the show with updated details
                        break;
                    }
                }
            }

            // Write the updated list back to the serialized file
            WriteObjects writer = new WriteObjects(filePath);
            writer.write(showList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            // Show a success message
            AlertHelper successAlert = new AlertHelper(isNewShow ? "New show added successfully!" : "Show updated successfully!");
            successAlert.executeSuccessAlert();

            // Close the current window
            onBackButtonClick(actionEvent);

        } catch (IOException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving show: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }


    private Movie getMovieByTitle(String title) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("movies.ser");
        List<Object> movies = reader.read();
        return movies.stream()
                .filter(Movie.class::isInstance)
                .map(Movie.class::cast)
                .filter(movie -> movie.getAMovie_Title().equals(title))
                .findFirst()
                .orElse(null);
    }

    private Showtime getShowtimeByTime(String time) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("showtimes.ser");
        List<Object> showtimes = reader.read();
        return showtimes.stream()
                .filter(Showtime.class::isInstance)
                .map(Showtime.class::cast)
                .filter(showtime -> showtime.getaShowtimeTime().equals(time))
                .findFirst()
                .orElse(null);
    }

    private Screenroom getScreenroomByName(String name) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("screenrooms.ser");
        List<Object> screenrooms = reader.read();
        return screenrooms.stream()
                .filter(Screenroom.class::isInstance)
                .map(Screenroom.class::cast)
                .filter(screenroom -> screenroom.getScreenroom_Name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            // Load view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Shows","shows.ser","manager-show-add-modify-view.fxml");

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
