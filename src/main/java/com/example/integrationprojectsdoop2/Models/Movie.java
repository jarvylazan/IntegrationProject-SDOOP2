package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Movie with attributes such as ID, title, genre, and synopsis.
 * Implements {@link Serializable} for object serialization.
 * Each Movie is assigned a unique Movie ID and includes validation for input values
 * to ensure data integrity.
 * The class also provides functionality for resetting and updating the Movie ID counter
 * during deserialization.
 *
 * Implements the {@link ShowComponent} interface for display functionality in the system.
 *
 * @author Jarvy Lazan
 * @version 1.0
 */
public class Movie implements Serializable, ShowComponent {

    /** Serialized file links to the serialVersionUID */
    @Serial
    private static final long serialVersionUID = 69L;

    /** Counter for generating unique Movie IDs. */
    private static int aMovieIDCounter = 1;

    /** Unique ID for each Movie. */
    private final String aMovie_ID;

    /** Title of the Movie. */
    private String aMovie_Title;

    /** Genre of the Movie. */
    private String aMovie_Genre;

    /** Synopsis of the Movie. */
    private String aMovie_Synopsis;

    /**
     * Default constructor for the Movie class.
     * Automatically generates a unique Movie ID.
     * Other attributes (title, genre, synopsis) can be set using setter methods.
     *
     * @author Jarvy Lazan
     */
    public Movie() {
        this.aMovie_ID = generateMovieID();
    }

    /**
     * Constructs a Movie with the specified title, genre, and synopsis.
     * Validates each parameter to ensure non-null and non-empty values.
     *
     * @param pMovie_Title    the title of the movie (cannot be null or empty).
     * @param pMovie_Genre    the genre of the movie (cannot be null or empty).
     * @param pMovie_Synopsis the synopsis of the movie (cannot be null or empty).
     * @throws IllegalArgumentException if any parameter is null or empty.
     * @author Jarvy Lazan
     */
    public Movie(String pMovie_Title, String pMovie_Genre, String pMovie_Synopsis) {
        this.aMovie_ID = generateMovieID();
        this.setMovie_Title(pMovie_Title);
        this.setMovie_Genre(pMovie_Genre);
        this.setMovie_Synopsis(pMovie_Synopsis);
    }

    /**
     * Generates a unique Movie ID by incrementing the counter.
     *
     * @return the generated Movie ID in the format "MOV<number>".
     * @author Jarvy Lazan
     */
    private synchronized static String generateMovieID() {
        return "MOV" + aMovieIDCounter++;
    }

    /**
     * Gets the unique Movie ID.
     *
     * @return the Movie ID as a string.
     * @author Jarvy Lazan
     */
    public String getMovie_ID() {
        return this.aMovie_ID;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the title of the movie as a string.
     * @author Jarvy Lazan
     */
    public String getMovie_Title() {
        return this.aMovie_Title;
    }

    /**
     * Sets the title of the movie. Ensures the title is non-null and non-empty.
     *
     * @param pMovie_Title the title of the movie.
     * @throws IllegalArgumentException if the title is null or empty.
     * @author Jarvy Lazan
     */
    public void setMovie_Title(String pMovie_Title) {
        if (pMovie_Title == null || pMovie_Title.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Title cannot be null or empty.");
        }
        this.aMovie_Title = pMovie_Title;
    }

    /**
     * Gets the genre of the movie.
     *
     * @return the genre of the movie as a string.
     * @author Jarvy Lazan
     */
    public String getMovie_Genre() {
        return this.aMovie_Genre;
    }

    /**
     * Sets the genre of the movie. Ensures the genre is non-null and non-empty.
     *
     * @param pMovie_Genre the genre of the movie.
     * @throws IllegalArgumentException if the genre is null or empty.
     * @author Jarvy Lazan
     */
    public void setMovie_Genre(String pMovie_Genre) {
        if (pMovie_Genre == null || pMovie_Genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Genre cannot be null or empty.");
        }
        this.aMovie_Genre = pMovie_Genre;
    }

    /**
     * Gets the synopsis of the movie.
     *
     * @return the synopsis of the movie as a string.
     * @author Jarvy Lazan
     */
    public String getMovie_Synopsis() {
        return this.aMovie_Synopsis;
    }

    /**
     * Sets the synopsis of the movie. Ensures the synopsis is non-null and non-empty.
     *
     * @param pMovie_Synopsis the synopsis of the movie.
     * @throws IllegalArgumentException if the synopsis is null or empty.
     * @author Jarvy Lazan
     */
    public void setMovie_Synopsis(String pMovie_Synopsis) {
        if (pMovie_Synopsis == null || pMovie_Synopsis.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Synopsis cannot be null or empty.");
        }
        this.aMovie_Synopsis = pMovie_Synopsis;
    }

    /**
     * Gets the display name of the movie.
     * Implements the {@link ShowComponent#getDisplayName()} method.
     *
     * @return the title of the movie.
     * @author Jarvy Lazan
     */
    @Override
    public String getDisplayName() {
        return aMovie_Title;
    }

    /**
     * Converts the movie details to a readable string representation.
     *
     * @return the string representation of the movie's details.
     * @author Jarvy Lazan
     */
    @Override
    public String toString() {
        return "Title: \t" + aMovie_Title +
                "\nGenre:\t" + aMovie_Genre +
                "\nSynopsis:\t" + aMovie_Synopsis;
    }

    /**
     * Resets the Movie ID counter based on existing movies.
     * Sets the counter to the highest existing ID + 1.
     *
     * @param pExistingMovies the list of existing movies.
     * @author Jarvy Lazan
     */
    public static void resetMovieIDCounter(List<Movie> pExistingMovies) {
        if (pExistingMovies != null && !pExistingMovies.isEmpty()) {
            aMovieIDCounter = pExistingMovies.stream()
                    .mapToInt(movie -> Integer.parseInt(movie.getMovie_ID().substring(3)))
                    .max().orElse(0) + 1; // Find the max ID and set counter to max + 1
        }
    }

    /**
     * Ensures the Movie ID counter is updated after deserialization.
     *
     * @return the current instance of {@code Movie}.
     * @author Jarvy Lazan
     */
    @Serial
    private Object readResolve() {
        updateMovieIDCounter(); // Ensure the ID counter is correct after deserialization
        return this;
    }

    /**
     * Updates the Movie ID counter based on the current movie's ID.
     * Ensures the counter is at least one higher than the current movie's ID.
     *
     * @throws NumberFormatException if the Movie ID is improperly formatted.
     * @author Jarvy Lazan
     */
    private void updateMovieIDCounter() {
        int currentID = Integer.parseInt(this.aMovie_ID.substring(3));
        if (currentID >= aMovieIDCounter) { // Only update if the current ID is greater than the counter
            aMovieIDCounter = currentID + 1;
        }
    }
}
