package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Movie with attributes such as ID, title, genre, and synopsis.
 * Implements {@link Serializable} for object serialization.
 * Each Movie is assigned a unique Movie ID.
 * Provides validation for input values to ensure data integrity.
 *
 * @author Jarvy Lazan
 */
public class Movie implements Serializable, ShowComponent {

    @Serial
    private static final long serialVersionUID = 69L;

    /** Counter for generating unique Movie IDs. */
    private static int movieIDCounter = 1;

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
     *
     * @param pMovie_Title   the title of the movie (cannot be null or empty).
     * @param pMovie_Genre   the genre of the movie (cannot be null or empty).
     * @param pMovie_Synopsis the synopsis of the movie (cannot be null or empty).
     * @throws IllegalArgumentException if any parameter is null or empty.
     * @author Jarvy Lazan
     */
    public Movie(String pMovie_Title, String pMovie_Genre, String pMovie_Synopsis) {
        this.aMovie_ID = generateMovieID();
        this.setAMovie_Title(pMovie_Title);
        this.setAMovie_Genre(pMovie_Genre);
        this.setAMovie_Synopsis(pMovie_Synopsis);
    }

    /**
     * Generates a unique Movie ID by incrementing the counter.
     *
     * @return the generated Movie ID in the format "MOV<number>".
     * @author Jarvy Lazan
     */
    private static synchronized String generateMovieID() {
        return "MOV" + movieIDCounter++;
    }

    /**
     * Gets the unique Movie ID.
     *
     * @return the Movie ID.
     * @author Jarvy Lazan
     */
    public String getAMovie_ID() {
        return this.aMovie_ID;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the movie title.
     * @author Jarvy Lazan
     */
    public String getAMovie_Title() {
        return this.aMovie_Title;
    }

    /**
     * Sets the title of the movie. Cannot be null or empty.
     *
     * @param pMovie_Title the movie title to set.
     * @throws IllegalArgumentException if the title is null or empty.
     * @author Jarvy Lazan
     */
    public void setAMovie_Title(String pMovie_Title) {
        if (pMovie_Title == null || pMovie_Title.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Title cannot be null or empty.");
        }
        this.aMovie_Title = pMovie_Title;
    }

    /**
     * Gets the genre of the movie.
     *
     * @return the movie genre.
     * @throws IllegalArgumentException if the genre is null or empty.
     * @author Jarvy Lazan
     */
    public String getAMovie_Genre() {
        return this.aMovie_Genre;
    }

    /**
     * Sets the genre of the movie. Cannot be null or empty.
     *
     * @param pMovie_Genre the movie genre to set.
     * @throws IllegalArgumentException if the genre is null or empty.
     * @author Jarvy Lazan
     */
    public void setAMovie_Genre(String pMovie_Genre) {
        if (pMovie_Genre == null || pMovie_Genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Genre cannot be null or empty.");
        }
        this.aMovie_Genre = pMovie_Genre;
    }

    /**
     * Gets the synopsis of the movie.
     *
     * @return the movie synopsis.
     * @throws IllegalArgumentException if the synopsis is null or empty.
     * @author Jarvy Lazan
     */
    public String getAMovie_Synopsis() {
        return this.aMovie_Synopsis;
    }

    /**
     * Sets the synopsis of the movie. Cannot be null or empty.
     *
     * @param pMovie_Synopsis the movie synopsis to set.
     * @throws IllegalArgumentException if the synopsis is null or empty.
     * @author Jarvy Lazan
     */
    public void setAMovie_Synopsis(String pMovie_Synopsis) {
        if (pMovie_Synopsis == null || pMovie_Synopsis.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Synopsis cannot be null or empty.");
        }
        this.aMovie_Synopsis = pMovie_Synopsis;
    }

    @Override
    public String getDisplayName() {
        return aMovie_Title;
    }
}
