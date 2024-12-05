package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a show in a cinema, including details such as the movie being shown,
 * the screen room, the showtime, and the e-ticket information.
 * Each show has a unique ID that is automatically generated.
 * This class implements {@link Serializable} to allow object serialization.
 *
 * @author Jarvy Lazan
 */
public class Show implements Serializable, ShowComponent {

    @Serial
    private static final long serialVersionUID = 11L;
    /**
     * Counter for generating unique Show IDs.
     */
    private static int aShowIDCounter = 1;

    /**
     * Unique identifier for this Show.
     */
    private final String aShowID;

    /**
     * The movie being shown in this Show.
     */
    private Movie aMovie;

    /**
     * The screen room where the Show takes place.
     */
    private Screenroom aScreenroom;

    /**
     * The time the Show is scheduled to start.
     */
    private Showtime aShowtime;

    /**
     * The e-ticket information for the Show.
     */
    private ETicket aTicket;

    /**
     * The date of the Show.
     */
    private LocalDate aShowDate;

    /**
     * Default constructor for the Show class.
     * Automatically generates a unique Show ID.
     *
     * @author Jarvy Lazan
     */
    public Show() {
        this.aShowID = generateShowID();
    }

    /**
     * Parameterized constructor for the Show class.
     *
     * @param pMovie The movie being shown.
     * @param pScreenroom The screen room where the movie is shown.
     * @param pShowtime The scheduled start time for the show.
     * @param pTicket The e-ticket associated with the show.
     * @param pShowDate The date of the show.
     * @throws IllegalArgumentException if any parameter is null.
     * @author Jarvy Lazan
     */
    public Show(Movie pMovie, Screenroom pScreenroom, Showtime pShowtime, ETicket pTicket, LocalDate pShowDate) {
        this.aShowID = generateShowID();
        this.setMovie(pMovie);
        this.setScreenroom(pScreenroom);
        this.setShowtime(pShowtime);
        this.setETicket(pTicket);
        this.setShowDate(pShowDate);
    }

    public Show(Movie pMovie, Screenroom pScreenroom, Showtime pShowtime, LocalDate pShowDate) {
        this.aShowID = generateShowID();
        this.setMovie(pMovie);
        this.setScreenroom(pScreenroom);
        this.setShowtime(pShowtime);
        this.setShowDate(pShowDate);
    }

    /**
     * Generates a unique ID for each Show.
     *
     * @return A unique Show ID in the format "S<number>".
     * @author Jarvy Lazan
     */
    private static synchronized String generateShowID() {
        return "S" + aShowIDCounter++;
    }

    public String getShowID() {
        return aShowID;
    }

    /**
     * Gets the movie being shown.
     *
     * @return The movie for this Show.
     * @author Jarvy Lazan
     */
    public Movie getMovie() {
        return aMovie;
    }

    /**
     * Sets the movie for this Show.
     *
     * @param pMovie The movie to set.
     * @throws IllegalArgumentException if the movie is null.
     * @author Jarvy Lazan
     */
    public void setMovie(Movie pMovie) {
        if (pMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        aMovie = pMovie;
    }

    /**
     * Gets the screen room where the Show is held.
     *
     * @return The screen room for this Show.
     * @author Jarvy Lazan
     */
    public Screenroom getScreenroom() {
        return aScreenroom;
    }

    /**
     * Sets the screen room for this Show.
     *
     * @param pScreenroom The screen room to set.
     * @throws IllegalArgumentException if the screen room is null.
     * @author Jarvy Lazan
     */
    public void setScreenroom(Screenroom pScreenroom) {
        if (pScreenroom == null) {
            throw new IllegalArgumentException("Screenroom cannot be null.");
        }
        aScreenroom = pScreenroom;
    }

    /**
     * Gets the scheduled showtime.
     *
     * @return The showtime for this Show.
     * @author Jarvy Lazan
     */
    public Showtime getShowtime() {
        return aShowtime;
    }

    /**
     * Sets the showtime for this Show.
     *
     * @param pShowtime The showtime to set.
     * @throws IllegalArgumentException if the showtime is null.
     * @author Jarvy Lazan
     */
    public void setShowtime(Showtime pShowtime) {
        if (pShowtime == null) {
            throw new IllegalArgumentException("Showtime cannot be null.");
        }
        aShowtime = pShowtime;
    }

    /**
     * Gets the e-ticket information.
     *
     * @return The e-ticket for this Show.
     * @author Jarvy Lazan
     */
    public ETicket getTicket() {
        return aTicket;
    }

    /**
     * Sets the e-ticket information for this Show.
     *
     * @param pTicket The e-ticket to set.
     * @throws IllegalArgumentException if the e-ticket is null.
     * @author Jarvy Lazan
     */
    private void setETicket(ETicket pTicket) {
        if (pTicket == null) {
            throw new IllegalArgumentException("ETicket cannot be null.");
        }
        aTicket = pTicket;
    }

    /**
     * Gets the date of the Show.
     *
     * @return The date of the show as a LocalDate.
     */
    public LocalDate getShowDate() {
        return this.aShowDate;
    }

    /**
     * Sets the date of the Show.
     *
     * @param pShowDate The date to set for the show.
     * @throws IllegalArgumentException if the date is null or in the past.
     */
    public void setShowDate(LocalDate pShowDate) {
        if (pShowDate == null) {
            throw new IllegalArgumentException("Show date cannot be null.");
        }
        if (pShowDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Show date cannot be in the past.");
        }
        this.aShowDate = pShowDate;
    }

    @Override
    public String getDisplayName() {
        return "Show: " + aMovie.getMovie_Title() +
                "\n" + aShowtime.getShowtimeTime() +
                " "+ aShowDate + "\nRoom: "
                + aScreenroom.getScreenroom_Name();
    }

    public static void resetMovieIDCounter(List<Movie> existingMovies) {
        if (existingMovies != null && !existingMovies.isEmpty()) {
            aShowIDCounter = existingMovies.stream()
                    .mapToInt(movie -> Integer.parseInt(movie.getMovie_ID().substring(3)))
                    .max().orElse(0) + 1;  // Find the max ID and set counter to max + 1
        }
    }

    // Make sure to add these methods during the deserialization process
    @Serial
    private Object readResolve() {
        updateMovieIDCounter();  // Ensure the ID counter is correct after deserialization
        return this;
    }

    private void updateMovieIDCounter() {
        if (aShowID != null && aShowID.startsWith("S") && aShowID.length() > 1) {
            try {
                int currentID = Integer.parseInt(aShowID.substring(1)); // Extract number part
                if (currentID >= aShowIDCounter) {
                    aShowIDCounter = currentID + 1; // Update counter if needed
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid Show ID format: " + aShowID);
                // Optionally, log or handle invalid IDs
            }
        }
    }


    @Override
    public String toString() {
        return "The Show has:"+
                "\n\nMovie: \t"+ aMovie.getMovie_Title()+
                "\nStarts at:\t"+aShowtime.getShowtimeTime()+
                "\nOn Date:\t" + aShowDate +
                "\nIn Screenroom:\t"+ aScreenroom.getScreenroom_Name();
    }
}
