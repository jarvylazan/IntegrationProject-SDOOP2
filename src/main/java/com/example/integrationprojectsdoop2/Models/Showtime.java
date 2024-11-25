package com.example.integrationprojectsdoop2.Models;

/**
 * Represents a Showtime with a unique identifier and a specific time.
 *
 * The unique identifier is auto-generated using a static counter. The time is validated
 * to ensure it follows the format HH:mm (e.g., 12:34).
 *
 * @author Mohammad Tarin Wahidi
 */
public class Showtime {

    /** Counter for generating unique showtimes IDs, incremented with each instance. */
    private static int aShowtimeIDCounter = 1;

    /** Unique identifier for the showtime, auto-generated. */
    private String aShowtime_ID;

    /** Specific time for the showtime, validated to follow the format HH:mm. */
    private String aShowtime_Time;

    /**
     * Default constructor for creating a Showtime instance without initializing fields.
     */
    public Showtime() {}

    /**
     * Parameterized constructor for creating a Showtime instance with a specified time.
     * <p>
     * The showtime ID is auto-generated, and the provided time is validated to follow
     * the HH:mm format.
     *
     * @param pShowtime_Time the time of the showtime (must follow the format HH:mm).
     * @throws IllegalArgumentException if the showtime time does not match the format HH:mm.
     */
    public Showtime(String pShowtime_Time) {
        this.aShowtime_ID = setaShowtimeID();
        setaShowtimeTime(pShowtime_Time); // Calls setter for validation.
    }

    /**
     * Gets the unique identifier of the showtime.
     *
     * @return the showtime ID.
     */
    public String getaShowtimeID() {
        return this.aShowtime_ID;
    }

    /**
     * Generates and sets the unique identifier for the showtime.
     * <p>
     * The ID is prefixed with 'T' followed by an incrementing counter.
     *
     * @return the generated unique showtime ID.
     */
    private static synchronized String setaShowtimeID() {
        return "T" + aShowtimeIDCounter++;
    }

    /**
     * Gets the time of the showtime.
     *
     * @return the showtime time in the format HH:mm.
     */
    public String getaShowtimeTime() {
        return this.aShowtime_Time;
    }

    /**
     * Sets the time of the showtime. The time must follow the format HH:mm (e.g., 12:34).
     *
     * @param pShowtime_Time the showtime time to set.
     * @throws IllegalArgumentException if the showtime time does not match the format HH:mm.
     */
    public void setaShowtimeTime(String pShowtime_Time) {
        // Regular expression to validate the format HH:mm
        String timePattern = "^\\d{2}:\\d{2}$";

        if (pShowtime_Time == null || !pShowtime_Time.matches(timePattern)) {
            throw new IllegalArgumentException("A showtime must follow the format HH:mm (e.g., 12:34).");
        }

        this.aShowtime_Time = pShowtime_Time;
    }
}