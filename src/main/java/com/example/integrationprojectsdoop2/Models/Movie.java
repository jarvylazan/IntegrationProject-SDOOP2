package com.example.integrationprojectsdoop2.Models;

/**
 * Represents a Movie with attributes such as ID, title, genre, and synopsis.
 * Provides validation for attribute values to ensure data integrity.
 *
 * @author Jarvy Lazan
 */
public class Movie {
    private int aMovie_ID;
    private String aMovie_Title;
    private String aMovie_Genre;
    private String aMovie_Synopsis;

    /**
     * Default constructor for creating a Movie instance without initializing fields.
     *
     * @author Jarvy Lazan
     */
    public Movie() {
    }

    /**
     * Parameterized constructor for creating a Movie instance with specified values.
     * Uses setters for validation.
     *
     * @param pMovie_ID      the unique identifier of the movie (must be positive).
     * @param pMovie_Title   the title of the movie (cannot be null or empty).
     * @param pMovie_Genre   the genre of the movie (cannot be null or empty).
     * @param pMovie_Synopsis the synopsis of the movie (cannot be null or empty).
     * @throws IllegalArgumentException if any parameter is invalid.
     * @author Jarvy Lazan
     */
    public Movie(int pMovie_ID, String pMovie_Title, String pMovie_Genre, String pMovie_Synopsis) {
        setAMovie_ID(pMovie_ID);
        setAMovie_Title(pMovie_Title);
        setAMovie_Genre(pMovie_Genre);
        setAMovie_Synopsis(pMovie_Synopsis);
    }

    /**
     * Gets the unique identifier of the movie.
     *
     * @return the movie ID.
     * @author Jarvy Lazan
     */
    public int getAMovie_ID() {
        return aMovie_ID;
    }

    /**
     * Sets the unique identifier of the movie. Must be positive.
     *
     * @param pMovie_ID the movie ID to set.
     * @throws IllegalArgumentException if the movie ID is not positive.
     * @author Jarvy Lazan
     */
    public void setAMovie_ID(int pMovie_ID) {
        if (pMovie_ID <= 0) {
            throw new IllegalArgumentException("Movie ID must be a positive integer.");
        }
        this.aMovie_ID = pMovie_ID;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the movie title.
     * @author Jarvy Lazan
     */
    public String getAMovie_Title() {
        return aMovie_Title;
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
     * @author Jarvy Lazan
     */
    public String getAMovie_Genre() {
        return aMovie_Genre;
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
     * @author Jarvy Lazan
     */
    public String getAMovie_Synopsis() {
        return aMovie_Synopsis;
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
}
