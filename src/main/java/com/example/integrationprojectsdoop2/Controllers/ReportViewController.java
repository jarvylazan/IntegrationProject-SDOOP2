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

/**
 * Controller for managing and displaying reports in the application.
 * This controller handles operations such as sorting and filtering reports
 * for users, movies, shows, and e-tickets.
 *
 * @author Jarvy Lazan & Samuel Mireault
 * @version 1.0
 */
public class ReportViewController {

    /** Label for displaying the report's header name. */
    @FXML
    public Label headerName;

    /** The main view container for the sales report. */
    @FXML
    public AnchorPane salesReportView;

    /** The ListView for displaying report data. */
    @FXML
    public ListView<String> reportListView;

    /** The ComboBox for selecting sorting and filtering options for the report. */
    @FXML
    public ComboBox<String> reportComboBox;

    /** List of users for report generation and sorting. */
    private List<User> userList = new ArrayList<>();

    /** List of movies for report generation and sorting. */
    private List<Movie> movieList = new ArrayList<>();

    /** List of shows for report generation and sorting. */
    private List<Show> showList = new ArrayList<>();

    /** List of e-tickets for report generation and sorting. */
    private List<ETicket> eTicketsList = new ArrayList<>();

    /**
     * Initializes the report view by setting up the ComboBox listeners and loading show data.
     * This method is automatically called after the FXML file is loaded.
     *
     * @author Jarvy Lazan & Samuel Mireault
     */
    public void initialize() {
        List<Object> objects = readObjectsFromFile("shows.ser");
        showList = objects.stream()
                .filter(Show.class::isInstance)
                .map(Show.class::cast)
                .collect(Collectors.toList());

        // Add listener to ComboBox to sort the ListView dynamically
        reportComboBox.setOnAction(event -> {
            String selectedOption = reportComboBox.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "Movies Sold" -> sortByMovieSold(eTicketsList); // Chronological sorting
                    case "Show by Tickets Sold" -> sortByShow(true); // Sorting by tickets sold
                }
            }
        });
    }

    /**
     * Sorts shows based on the selected criteria and updates the ListView.
     * Options include chronological sorting and sorting by ticket sales.
     * This method updates the ComboBox to support "Tickets Sold" sorting.
     *
     * @param byTicketsSold a boolean indicating whether to sort by ticket sales (true)
     *                      or by chronological order (false).
     * @author Jarvy Lazan
     */
    private void sortByShow(boolean byTicketsSold) {
        if (byTicketsSold) {
            // Sort by number of tickets sold in descending order
            showList.sort(Comparator.comparingInt(this::calculateTicketsSoldForShow).reversed());
        } else {
            // Sort by chronological order of the shows
            showList.sort(Comparator.comparing(
                    Show::getShowDate,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        }

        ObservableList<String> sortedShowList = FXCollections.observableArrayList(
                showList.stream()
                        .map(show -> String.format("%s | Tickets Sold: %d\n",
                                show.getMovie().getMovie_Title() + " at " + show.getShowtime().getShowtimeTime() + " in " + show.getScreenroom().getScreenroom_Name(),
                                calculateTicketsSoldForShow(show)))
                        .collect(Collectors.toList())
        );

        reportListView.setItems(sortedShowList);
    }

    /**
     * Calculates the number of tickets sold for a given show.
     *
     * @param show the show for which ticket sales are calculated.
     * @return the number of tickets sold for the given show.
     * @author Samuel Mireault
     */
    private int calculateTicketsSoldForShow(Show show) {
        return (int) eTicketsList.stream()
                .filter(ticket -> ticket.getShow().getShowID().equals(show.getShowID()))
                .count();
    }

    /**
     * Sorts movies by the number of tickets sold in descending order.
     *
     * @param pEticketList the list of e-tickets to process for sales data.
     * @author Samuel Mireault
     */
    private void sortByMovieSold(List<ETicket> pEticketList) {
        Map<String, Integer> movieSales = new HashMap<>();

        for (ETicket eticket : pEticketList) {
            String showID = eticket.getShow().getShowID();
            Optional<Show> matchingShow = showList.stream()
                    .filter(show -> show.getShowID().equals(showID))
                    .findFirst();

            matchingShow.ifPresent(show -> {
                String movieName = show.getMovie().getMovie_Title();
                movieSales.put(movieName, movieSales.getOrDefault(movieName, 0) + 1);
            });
        }

        List<Map.Entry<String, Integer>> sortedMovies = movieSales.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> reportData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : sortedMovies) {
            reportData.add("Movie: " + entry.getKey() + ", Tickets Sold: " + entry.getValue());
        }

        reportListView.setItems(reportData);
    }

    /**
     * Configures the report view to display data from a specific serialized file.
     *
     * @param pFilename the name of the serialized file (e.g., "clients.ser").
     * @author Jarvy Lazan & Samuel Mireault
     */
    public void setManagementView(String pFilename) {
        List<Object> objects = readObjectsFromFile(pFilename);

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

        populateListView(userList, movieList, eTicketsList);

        if (!userList.isEmpty()) {
            reportComboBox.getItems().addAll("Alphabetical (A-Z)", "Alphabetical (Z-A)");
            sortData(true);
        } else {
//            reportComboBox.getItems().addAll("Movie Sold", "Show");
            reportComboBox.getItems().addAll("Movies Sold", "Show by Tickets Sold");
            sortByMovieSold(eTicketsList);
        }
    }

    /**
     * Populates the ListView with user, movie, and e-ticket data.
     *
     * @param users  the list of user objects to display.
     * @param movies the list of movie objects to display.
     * @param eTickets the list of e-ticket objects to display.
     * @author Jarvy Lazan & Samuel Mireault
     */
    private void populateListView(List<User> users, List<Movie> movies, List<ETicket> eTickets) {
        ObservableList<String> displayList = FXCollections.observableArrayList();

        for (User user : users) {
            displayList.add("User: " + user.toString());
        }

        for (Movie movie : movies) {
            displayList.add("Movie: " + movie.toString());
        }

        for (ETicket eTicket : eTickets) {
            displayList.add("Show: " + eTicket.toString());
        }

        reportListView.setItems(displayList);
    }

    /**
     * Sorts users and movies alphabetically based on the selected order.
     *
     * @param ascending true for ascending order, false for descending order.
     * @author Jarvy Lazan & Samuel Mireault
     */
    private void sortData(boolean ascending) {
        Comparator<User> userComparator = Comparator.comparing(User::getUser_Name, String.CASE_INSENSITIVE_ORDER);
        Comparator<Movie> movieComparator = Comparator.comparing(Movie::getMovie_Title, String.CASE_INSENSITIVE_ORDER);

        if (!ascending) {
            userComparator = userComparator.reversed();
            movieComparator = movieComparator.reversed();
        }

        userList.sort(userComparator);
        movieList.sort(movieComparator);

        populateListView(userList, movieList, eTicketsList);
    }

    /**
     * Reads objects from a serialized file.
     *
     * @param pFilename the name of the file to read from.
     * @return a list of objects read from the file.
     * @author Jarvy Lazan
     */
    private List<Object> readObjectsFromFile(String pFilename) {
        List<Object> objects = new ArrayList<>();
        try {
            ReadObjects reader = new ReadObjects(pFilename);
            objects = reader.read();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects from file: " + e.getMessage());
        }
        return objects;
    }

    /**
     * Sets the header text for the report view.
     *
     * @param pText the text to set as the header.
     * @author Jarvy Lazan
     */
    public void setHeaderName(String pText) {
        headerName.setText(pText);
    }

    /**
     * Closes the current report view.
     *
     * @author Jarvy Lazan
     */
    public void onBackButtonClick() {
        Stage stage = (Stage) salesReportView.getScene().getWindow();
        stage.close();
    }
}
