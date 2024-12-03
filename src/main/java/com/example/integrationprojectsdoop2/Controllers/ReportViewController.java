package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportViewController{

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
    private List<Show> showList = new ArrayList<>();
    private List<ETicket> eTicketsList = new ArrayList<>(); // Holds Movie objects for sorting

    /**
     * Initialize method (if additional setup is needed, it can go here).
     *
     * @author Jarvy Lazan
     */
    public void initialize() {
        reportComboBox.getItems().addAll("Alphabetical (A-Z)", "Alphabetical (Z-A)", "Movie Sold", "Showtimes");

        List<Object> objects = readObjectsFromFile("shows.ser");
        showList = objects.stream()
                .filter(Show.class::isInstance)
                .map(Show.class::cast)
                .collect(Collectors.toList());

        // Add a listener to the ComboBox to sort the ListView dynamically
        reportComboBox.setOnAction(event -> {
            String selectedOption = reportComboBox.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "Alphabetical (A-Z)" -> sortData(true); // Sort A-Z
                    case "Alphabetical (Z-A)" -> sortData(false); // Sort Z-A
                    case "Movie Sold" -> sortByMovieSold(eTicketsList); // Sort by sales
                    case "Showtimes" -> sortByShowtimes(); // Sort by sales
                }
            }
        });
    }

    private void sortByShowtimes() {
        showList.sort(Comparator.comparing(
                Show::getShowDate,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));

        ObservableList<String> sortedShowList = FXCollections.observableArrayList(
                showList.stream()
                        .map(show -> String.format("%s | Tickets Sold: %d\n",
                                show.getMovie().getAMovie_Title() + " at " + show.getShowtime().getaShowtimeTime() + " in "+ show.getScreenroom().getScreenroom_Name() ,
                                calculateTicketsSoldForShow(show)))
                        .collect(Collectors.toList())
        );

        reportListView.setItems(sortedShowList);
    }

    // Method to calculate tickets sold
    private int calculateTicketsSoldForShow(Show show) {
        // Example logic: Iterate over a global list of tickets and count matches
        return (int) eTicketsList.stream()
                .filter(ticket -> ticket.getaShowID().equals(show.getaShowID()))
                .count();
    }





    /**
     * Counts the number of times each movie was bought based on ETickets and the associated shows.
     * Sorts the movies by the number of tickets sold in descending order.
     *
     * @param pEticketList the list of ETickets to process.
     * @author Samuel
     */
    private void sortByMovieSold(List<ETicket> pEticketList) {
        // Map to store movie name and its corresponding ticket count
        Map<String, Integer> movieSales = new HashMap<>();

        // Count tickets for each movie
        for (ETicket eticket : pEticketList) {
            String showID = eticket.getaShowID();
            Optional<Show> matchingShow = showList.stream()
                    .filter(show -> show.getaShowID().equals(showID))
                    .findFirst();

            if (matchingShow.isPresent()) {
                String movieName = matchingShow.get().getMovie().getAMovie_Title();
                movieSales.put(movieName, movieSales.getOrDefault(movieName, 0) + 1);
            }
        }

        // Sort movies by the number of tickets sold in descending order
        List<Map.Entry<String, Integer>> sortedMovies = movieSales.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        // Print or process the sorted result
        ObservableList<String> reportData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : sortedMovies) {
            reportData.add("Movie: " + entry.getKey() + ", Tickets Sold: " + entry.getValue());
        }

        reportListView.setItems(reportData);
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

        eTicketsList = objects.stream()
                .filter(ETicket.class::isInstance)
                .map(ETicket.class::cast)
                .collect(Collectors.toList());

        // Populate the ListView with both Users and Movies
        populateListView(userList, movieList, eTicketsList);
    }

    /**
     * Populates the ListView with User and Movie objects.
     *
     * @param users  the list of User objects to display.
     * @param movies the list of Movie objects to display.
     * @author Jarvy Lazan
     */
    private void populateListView(List<User> users, List<Movie> movies, List<ETicket> eTickets) {
        ObservableList<String> displayList = FXCollections.observableArrayList();

        // Add Users to the display list
        for (User user : users) {
            displayList.add("User: " + user.toString()); // Customize `toString()` for meaningful display
        }

        // Add Movies to the display list
        for (Movie movie : movies) {
            displayList.add("Movie: " + movie.toString()); // Customize `toString()` for meaningful display
        }
        for (ETicket eTicket : eTickets) {
            displayList.add("Show: " + eTicket.toString());
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
        populateListView(userList, movieList, eTicketsList);
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
     *
     * @author Jarvy Lazan
     */
    public void onBackButtonClick() {
        Stage stage = (Stage) salesReportView.getScene().getWindow();
        stage.close();
    }
}
