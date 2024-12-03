package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.User;
import com.example.integrationprojectsdoop2.Models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportViewController {

    @FXML
    public Label headerName;

    @FXML
    public AnchorPane salesReportView;

    @FXML
    public ListView<String> reportListView;

    @FXML
    public ComboBox<String> reportComboBox;

    private List<User> userList = new ArrayList<>(); // Holds User objects for sorting
    private List<Movie> movieList = new ArrayList<>(); // Holds Movie objects for sorting

    /**
     * Initialize method (if additional setup is needed, it can go here).
     * @author Jarvy Lazan
     */
    public void initialize() {
        reportComboBox.getItems().addAll("Alphabetical (A-Z)", "Alphabetical (Z-A)", "Most Sold");

        // Add a listener to the ComboBox to sort the ListView dynamically
        reportComboBox.setOnAction(event -> {
            String selectedOption = reportComboBox.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "Alphabetical (A-Z)" -> sortData(true); // Sort A-Z
                    case "Alphabetical (Z-A)" -> sortData(false); // Sort Z-A
//                    case "Most Sold" -> sortBySales(); // Sort by sales
                }
            }
        });
    }

    /**
     * Sets the view to display data from a given serialized file.
     *
     * @param pFilename the name of the serialized file (e.g., "clients.ser").
     * @author Jarvy Lazan
     */
    public void setManagementView(String pFilename) {
        // Read all objects from the file dynamically
        List<Object> objects = readObjectsFromFile(pFilename);

        // Separate User and Movie objects
        userList = objects.stream()
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .collect(Collectors.toList());

        movieList = objects.stream()
                .filter(Movie.class::isInstance)
                .map(Movie.class::cast)
                .collect(Collectors.toList());

        // Populate the ListView with both Users and Movies
        populateListView(userList, movieList);
    }

    /**
     * Populates the ListView with User and Movie objects.
     *
     * @param users  the list of User objects to display.
     * @param movies the list of Movie objects to display.
     * @author Jarvy Lazan
     */
    private void populateListView(List<User> users, List<Movie> movies) {
        ObservableList<String> displayList = FXCollections.observableArrayList();

        // Add Users to the display list
        for (User user : users) {
            displayList.add("User: " + user.toString()); // Customize `toString()` for meaningful display
        }

        // Add Movies to the display list
        for (Movie movie : movies) {
            displayList.add("Movie: " + movie.toString()); // Customize `toString()` for meaningful display
        }

        reportListView.setItems(displayList);
    }

    /**
     * Sorts both users and movies alphabetically.
     *
     * @param ascending true for A-Z sorting, false for Z-A sorting.
     * @author Jarvy Lazan
     */
    private void sortData(boolean ascending) {
        Comparator<User> userComparator = Comparator.comparing(User::getaUser_Name, String.CASE_INSENSITIVE_ORDER);
        Comparator<Movie> movieComparator = Comparator.comparing(Movie::getAMovie_Title, String.CASE_INSENSITIVE_ORDER);

        if (!ascending) {
            userComparator = userComparator.reversed();
            movieComparator = movieComparator.reversed();
        }

        // Sort both lists
        userList.sort(userComparator);
        movieList.sort(movieComparator);

        // Refresh the ListView
        populateListView(userList, movieList);
    }

//    /**
//     * Sorts movies by sales in descending order.
//     */
//    private void sortBySales() {
//        // Idea from sam: count the number tickets with id of that movie
//        Comparator<Movie> salesComparator = Comparator.comparing(Movie::getSales).reversed();
//
//        // Sort movies by sales
//        movieList.sort(salesComparator);
//
//        // Refresh the ListView with updated movie list and existing user list
//        populateListView(userList, movieList);
//    }

    /**
     * Reads all objects from the given serialized file.
     *
     * @param pFilename the name of the file to read objects from.
     * @return a list of all objects read from the file.
     * @author Jarvy Lazan
     */
    private List<Object> readObjectsFromFile(String pFilename) {
        List<Object> objects = new ArrayList<>();

        try {
            // Use the ReadObjects helper to read objects from the file
            ReadObjects reader = new ReadObjects(pFilename);
            objects = reader.read(); // Deserialize all objects
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects from file: " + e.getMessage());
        }

        return objects;
    }

    /**
     * Sets the header text for the view.
     *
     * @param pText the text to set as the header.
     * @author Jarvy Lazan
     */
    public void setHeaderName(String pText) {
        headerName.setText(pText);
    }

    /**
     * Closes the current window when the back button is clicked.
     * @author Jarvy Lazan
     */
    public void onBackButtonClick() {
        Stage stage = (Stage) salesReportView.getScene().getWindow();
        stage.close();
    }
}
