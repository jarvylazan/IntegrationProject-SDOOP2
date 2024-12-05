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

/**
 * Controller for managing the addition and modification of shows.
 * Provides functionality to load movies, showtimes, and screenrooms, and to validate and save shows.
 *
 * @author Jarvy Lazan
 */
public class ManagerShowAddModifyController implements ModifyController<Show> {

    @FXML
    private ComboBox<String> MovieComboBox;

    @FXML
    private ComboBox<String> ShowtimeComboBox;

    @FXML
    private ComboBox<String> ScreenroomComboBox;

    @FXML
    private DatePicker ShowDatePicker;

    private Show aCurrentShow;

    /**
     * Initializes the controller and loads movies, showtimes, and screenrooms into their respective ComboBoxes.
     *
     * @author Jarvy Lazan
     */
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

    /**
     * Loads movies into the MovieComboBox.
     *
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
    private void loadMovies() throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("movies.ser");
        List<Object> movies = reader.read();
        MovieComboBox.getItems().addAll(
                movies.stream()
                        .filter(Movie.class::isInstance)
                        .map(Movie.class::cast)
                        .map(Movie::getMovie_Title)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Loads showtimes into the ShowtimeComboBox.
     *
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
    private void loadShowtimes() throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("showtimes.ser");
        List<Object> showtimes = reader.read();
        ShowtimeComboBox.getItems().addAll(
                showtimes.stream()
                        .filter(Showtime.class::isInstance)
                        .map(Showtime.class::cast)
                        .map(Showtime::getShowtimeTime)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Loads screenrooms into the ScreenroomComboBox.
     *
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
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

    /**
     * Populates the form with the data of the provided show.
     *
     * @param pShow the show to populate in the form, or null to clear the form.
     * @author Jarvy Lazan
     */
    @Override
    public void initializeData(Show pShow) {
        if (pShow != null) {
            this.aCurrentShow = pShow;
            MovieComboBox.setValue(pShow.getMovie().getMovie_Title());
            ShowtimeComboBox.setValue(pShow.getShowtime().getShowtimeTime());
            ScreenroomComboBox.setValue(pShow.getScreenroom().getScreenroom_Name());
            ShowDatePicker.setValue(pShow.getShowDate());
        }
    }

    /**
     * Handles the save button click. Validates input, checks for duplicates, updates or creates a show,
     * and saves the list of shows to a file.
     *
     * @param pActionEvent the event triggered by clicking the save button.
     * @author Jarvy Lazan
     */
    public void onSaveButtonClick(ActionEvent pActionEvent) {
        try {
            String filePath = "shows.ser";

            if (!Files.exists(Paths.get(filePath))) {
                WriteObjects writer = new WriteObjects(filePath);
                writer.write(new ArrayList<>());
            }

            ReadObjects reader = new ReadObjects(filePath);
            List<Object> rawObjects = reader.read();
            List<Show> showList = rawObjects.stream()
                    .filter(Show.class::isInstance)
                    .map(Show.class::cast)
                    .collect(Collectors.toList());

            boolean isNewShow = (aCurrentShow == null);

            String selectedMovieTitle = MovieComboBox.getValue();
            String selectedShowtimeTime = ShowtimeComboBox.getValue();
            String selectedScreenroomName = ScreenroomComboBox.getValue();
            LocalDate selectedDate = ShowDatePicker.getValue();

            if (selectedMovieTitle == null || selectedShowtimeTime == null || selectedScreenroomName == null || selectedDate == null) {
                new AlertHelper("All fields, including the date, must be selected.").executeErrorAlert();
                return;
            }

            if (selectedDate.isBefore(LocalDate.now())) {
                new AlertHelper("The selected date cannot be in the past.").executeErrorAlert();
                return;
            }

            Movie selectedMovie = getMovieByTitle(selectedMovieTitle);
            Showtime selectedShowtime = getShowtimeByTime(selectedShowtimeTime);
            Screenroom selectedScreenroom = getScreenroomByName(selectedScreenroomName);

            if (selectedMovie == null || selectedShowtime == null || selectedScreenroom == null) {
                new AlertHelper("Unable to find the selected items in the database.").executeErrorAlert();
                return;
            }

            boolean duplicateExists = showList.stream().anyMatch(show ->
                    Objects.equals(show.getMovie().getMovie_Title(), selectedMovieTitle) &&
                            Objects.equals(show.getShowtime().getShowtimeTime(), selectedShowtimeTime) &&
                            Objects.equals(show.getScreenroom().getScreenroom_Name(), selectedScreenroomName) &&
                            Objects.equals(show.getShowDate(), selectedDate)
            );

            if (duplicateExists) {
                new AlertHelper("A show with the same details already exists.").executeErrorAlert();
                return;
            }

            if (isNewShow) {
                aCurrentShow = new Show();
                showList.add(aCurrentShow);
            }

            aCurrentShow.setMovie(selectedMovie);
            aCurrentShow.setShowtime(selectedShowtime);
            aCurrentShow.setScreenroom(selectedScreenroom);
            aCurrentShow.setShowDate(selectedDate);

            if (!isNewShow) {
                for (int i = 0; i < showList.size(); i++) {
                    if (showList.get(i).getShowID().equals(aCurrentShow.getShowID())) {
                        showList.set(i, aCurrentShow);
                        break;
                    }
                }
            }

            // Save to file
            WriteObjects writer = new WriteObjects(filePath);
            writer.write(showList.stream().map(s -> (Object) s).collect(Collectors.toList()));

            new AlertHelper(isNewShow ? "New show added successfully!" : "Show updated successfully!").executeSuccessAlert();
            onBackButtonClick(pActionEvent);

        } catch (IOException | ClassNotFoundException e) {
            new AlertHelper("Error saving show: " + e.getMessage()).executeErrorAlert();
        }
    }

    /**
     * Retrieves a movie by its title.
     *
     * @param pTitle the title of the movie.
     * @return the matching Movie object, or null if not found.
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
    private Movie getMovieByTitle(String pTitle) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("movies.ser");
        return reader.read().stream()
                .filter(Movie.class::isInstance)
                .map(Movie.class::cast)
                .filter(movie -> movie.getMovie_Title().equals(pTitle))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a showtime by its time.
     *
     * @param pTime the time of the showtime.
     * @return the matching Showtime object, or null if not found.
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
    private Showtime getShowtimeByTime(String pTime) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("showtimes.ser");
        return reader.read().stream()
                .filter(Showtime.class::isInstance)
                .map(Showtime.class::cast)
                .filter(showtime -> showtime.getShowtimeTime().equals(pTime))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a screenroom by its name.
     *
     * @param pName the name of the screenroom.
     * @return the matching Screenroom object, or null if not found.
     * @throws IOException           if an error occurs while reading the file.
     * @throws ClassNotFoundException if deserialization fails.
     * @author Jarvy Lazan
     */
    private Screenroom getScreenroomByName(String pName) throws IOException, ClassNotFoundException {
        ReadObjects reader = new ReadObjects("screenrooms.ser");
        return reader.read().stream()
                .filter(Screenroom.class::isInstance)
                .map(Screenroom.class::cast)
                .filter(screenroom -> screenroom.getScreenroom_Name().equals(pName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Handles the back button click. Navigates back to the management view.
     *
     * @param pActionEvent the event triggered by clicking the back button.
     * @author Jarvy Lazan
     */
    public void onBackButtonClick(ActionEvent pActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Shows", "shows.ser", "manager-show-add-modify-view.fxml");

            Scene newScene = new Scene(managementView);
            Stage currentStage = (Stage) ((javafx.scene.Node) pActionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            new AlertHelper("Error loading the management view: " + e.getMessage()).executeErrorAlert();
        }
    }
}
