package com.example.integrationprojectsdoop2.Models;

/**
 * Represents a Movie with attributes such as ID, title, genre, and synopsis.
 * Provides validation for attribute values to ensure data integrity.
 */
public class Movie {
    private int movie_ID;
    private String movie_Title;
    private String movie_Genre;
    private String movie_Synopsis;

    /**
     * Default constructor for creating a Movie instance without initializing fields.
     */
    public Movie() {
    }

    /**
     * Parameterized constructor for creating a Movie instance with specified values.
     * Uses setters for validation.
     *
     * @param movie_ID      the unique identifier of the movie (must be positive).
     * @param movie_Title   the title of the movie (cannot be null or empty).
     * @param movie_Genre   the genre of the movie (cannot be null or empty).
     * @param movie_Synopsis the synopsis of the movie (cannot be null or empty).
     */
    public Movie(int movie_ID, String movie_Title, String movie_Genre, String movie_Synopsis) {
        setMovie_ID(movie_ID);
        setMovie_Title(movie_Title);
        setMovie_Genre(movie_Genre);
        setMovie_Synopsis(movie_Synopsis);
    }

    /**
     * Gets the unique identifier of the movie.
     *
     * @return the movie ID.
     */
    public int getMovie_ID() {
        return movie_ID;
    }

    /**
     * Sets the unique identifier of the movie. Must be positive.
     *
     * @param movie_ID the movie ID to set.
     * @throws IllegalArgumentException if the movie ID is not positive.
     */
    public void setMovie_ID(int movie_ID) {
        if (movie_ID <= 0) {
            throw new IllegalArgumentException("Movie ID must be a positive integer.");
        }
        this.movie_ID = movie_ID;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the movie title.
     */
    public String getMovie_Title() {
        return movie_Title;
    }

    /**
     * Sets the title of the movie. Cannot be null or empty.
     *
     * @param movie_Title the movie title to set.
     * @throws IllegalArgumentException if the title is null or empty.
     */
    public void setMovie_Title(String movie_Title) {
        if (movie_Title == null || movie_Title.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Title cannot be null or empty.");
        }
        this.movie_Title = movie_Title;
    }

    /**
     * Gets the genre of the movie.
     *
     * @return the movie genre.
     */
    public String getMovie_Genre() {
        return movie_Genre;
    }

    /**
     * Sets the genre of the movie. Cannot be null or empty.
     *
     * @param movie_Genre the movie genre to set.
     * @throws IllegalArgumentException if the genre is null or empty.
     */
    public void setMovie_Genre(String movie_Genre) {
        if (movie_Genre == null || movie_Genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Genre cannot be null or empty.");
        }
        this.movie_Genre = movie_Genre;
    }

    /**
     * Gets the synopsis of the movie.
     *
     * @return the movie synopsis.
     */
    public String getMovie_Synopsis() {
        return movie_Synopsis;
    }

    /**
     * Sets the synopsis of the movie. Cannot be null or empty.
     *
     * @param movie_Synopsis the movie synopsis to set.
     * @throws IllegalArgumentException if the synopsis is null or empty.
     */
    public void setMovie_Synopsis(String movie_Synopsis) {
        if (movie_Synopsis == null || movie_Synopsis.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie Synopsis cannot be null or empty.");
        }
        this.movie_Synopsis = movie_Synopsis;
    }
}
