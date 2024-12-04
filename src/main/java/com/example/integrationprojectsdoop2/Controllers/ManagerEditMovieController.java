package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for editing and managing movies in the application.
 * Handles adding, updating, and saving movie information.
 *
 * @author Jarvy Lazan
 */
public class ManagerEditMovieController implements ModifyController<Movie> {

    @FXML
    private TextField TitleTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextArea synopsisTextArea;

    private Movie currentMovie; // The movie being edited

    /**
     * Initializes the data for the controller. If a movie is provided, its details are populated in the form.
     * If no movie is provided, the form is cleared.
     *
     * @param movie the movie to initialize the form with, or null for a blank form.
     * @author Jarvy Lazan
     */
    public void initializeData(Movie movie) {
        if (movie != null) {
            this.currentMovie = movie;
            TitleTextField.setText(movie.getAMovie_Title());
            genreTextField.setText(movie.getAMovie_Genre());
            synopsisTextArea.setText(movie.getAMovie_Synopsis());
        } else {
            this.currentMovie = null;
            TitleTextField.clear();
            genreTextField.clear();
            synopsisTextArea.clear();
        }
    }

    /**
     * Handles the save button click event. Validates inputs, checks for duplicates,
     * updates the movie list, sorts it by title, and saves it back to the file.
     *
     * @param actionEvent the event triggered by clicking the save button.
     * @throws IOException if an error occurs during file operations.
     * @throws ClassNotFoundException if the deserialization fails.
     * @throws IllegalArgumentException if input validation fails.
     * @author Jarvy Lazan
     */
    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            ReadObjects reader = new ReadObjects("movies.ser");
            List<Object> rawObjects = reader.read();
            List<Movie> movieList = rawObjects.stream()
                    .filter(Movie.class::isInstance)
                    .map(Movie.class::cast)
                    .collect(Collectors.toList());

            boolean isNewMovie = (currentMovie == null);

            // Get the entered movie title
            String enteredTitle = TitleTextField.getText().trim();

            // Validate for duplicate movie title
            boolean isDuplicate = movieList.stream()
                    .anyMatch(movie -> movie.getAMovie_Title().equalsIgnoreCase(enteredTitle) &&
                            (isNewMovie || !movie.getAMovie_ID().equals(currentMovie.getAMovie_ID())));

            if (isDuplicate) {
                AlertHelper errorAlert = new AlertHelper("A movie with the same title already exists. Please use a unique title.");
                errorAlert.executeErrorAlert();
                return;
            }

            if (isNewMovie) {
                // If it's a new movie, create an instance and add it to the list
                currentMovie = new Movie();
                movieList.add(currentMovie);
            }

            // Update the movie's details from the form
            currentMovie.setAMovie_Title(enteredTitle);
            currentMovie.setAMovie_Genre(genreTextField.getText().trim());
            currentMovie.setAMovie_Synopsis(synopsisTextArea.getText().trim());

            if (!isNewMovie) {
                // Find the movie with the same ID and replace it
                for (int i = 0; i < movieList.size(); i++) {
                    if (movieList.get(i).getAMovie_ID().equals(currentMovie.getAMovie_ID())) {
                        movieList.set(i, currentMovie);  // Replace the movie with updated details
                        break;
                    }
                }
            }

            // Sort the movie list by title (case-insensitive, ignoring leading/trailing spaces)
            movieList.sort((m1, m2) -> {
                String title1 = m1.getAMovie_Title() != null ? m1.getAMovie_Title().trim().toLowerCase() : "";
                String title2 = m2.getAMovie_Title() != null ? m2.getAMovie_Title().trim().toLowerCase() : "";
                return title1.compareTo(title2);
            });

            // Write the sorted list back to the serialized file
            WriteObjects writer = new WriteObjects("movies.ser");
            writer.write(movieList.stream().map(m -> (Object) m).collect(Collectors.toList()));

            // Show a success message
            AlertHelper successAlert = new AlertHelper(isNewMovie ? "New movie added successfully!" : "Movie updated successfully!");
            successAlert.executeSuccessAlert();

            // Close the current window
            onBackButtonClick(actionEvent);

        } catch (IOException | IllegalArgumentException | ClassNotFoundException e) {
            AlertHelper errorAlert = new AlertHelper("Error saving movie: " + e.getMessage());
            errorAlert.executeErrorAlert();
        }
    }

    /**
     * Handles the back button click event. Navigates back to the management view.
     *
     * @param actionEvent the event triggered by clicking the back button.
     * @throws IOException if an error occurs during navigation to the management view.
     * @author Jarvy Lazan
     */
    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
            // Load the management view FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/management-view.fxml"));
            Parent managementView = loader.load();
            ManagementViewController controller = loader.getController();
            controller.setManagementView("Movies", "movies.ser", "manager-edit-movie-view.fxml");

            // Set the new scene
            Scene newScene = new Scene(managementView);
            Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (Exception e) {
            // Handle any exceptions
            AlertHelper errorCatch = new AlertHelper(e.getMessage());
            errorCatch.executeErrorAlert();
        }
    }
}
