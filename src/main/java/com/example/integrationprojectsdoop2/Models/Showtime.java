package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Showtime with a unique identifier and a specific time.
 *
 * Each Showtime has a unique ID automatically generated. The time is validated
 * to ensure it follows the format HH:mm (e.g., 12:34).
 *
 * @author Mohammad Tarin Wahidi
 */
public class Showtime implements Serializable, ShowComponent {

    @Serial
    private static final long serialVersionUID = 68L;

    /** Counter for generating unique showtime IDs, incremented with each instance. */
    private static int showtimeIDCounter = 1;

    /** Unique identifier for the showtime, auto-generated. */
    private final String aShowtime_ID;

    /** Specific time for the showtime, validated to follow the format HH:mm. */
    private String aShowtime_Time;

    /**
     * Default constructor for creating a Showtime instance without initializing fields.
     * Automatically generates a unique Showtime ID.
     */
    public Showtime() {
        this.aShowtime_ID = generateShowtimeID();
    }

    /**
     * Parameterized constructor for creating a Showtime instance with a specified time.
     * The Showtime ID is auto-generated, and the provided time is validated to follow
     * the HH:mm format.
     *
     * @param pShowtime_Time the time of the showtime (must follow the format HH:mm).
     * @throws IllegalArgumentException if the showtime time does not match the format HH:mm.
     */
    public Showtime(String pShowtime_Time) {
        this.aShowtime_ID = generateShowtimeID();
        setaShowtimeTime(pShowtime_Time); // Calls setter for validation.
    }

    /**
     * Generates a unique Showtime ID by incrementing the counter.
     *
     * @return the generated Showtime ID in the format "T<number>".
     */
    private static synchronized String generateShowtimeID() {
        return "T" + (showtimeIDCounter++);
    }

    /**
     * Gets the unique identifier of the showtime.
     *
     * @return the Showtime ID.
     */
    public String getaShowtimeID() {
        return this.aShowtime_ID;
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

    @Override
    public String getDisplayName() {
        return aShowtime_Time;
    }

    @Override
    public String toString() {
        return "Showtime: " + aShowtime_Time;
    }

    /**
     * Resets the Showtime ID counter based on the highest existing ID.
     *
     * @param existingShowtimes a list of existing Showtime objects.
     */
    public static void resetShowtimeIDCounter(List<Showtime> existingShowtimes) {
        if (existingShowtimes != null && !existingShowtimes.isEmpty()) {
            showtimeIDCounter = existingShowtimes.stream()
                    .mapToInt(showtime -> Integer.parseInt(showtime.getaShowtimeID().substring(1)))
                    .max().orElse(0) + 1; // Find the max ID and set counter to max + 1
        }
    }

    // Ensure the ID counter is correct after deserialization
    @Serial
    private Object readResolve() {
        updateShowtimeIDCounter();
        return this;
    }

    private void updateShowtimeIDCounter() {
        int currentID = Integer.parseInt(this.aShowtime_ID.substring(1));
        if (currentID >= showtimeIDCounter) { // Only update if the current ID is greater than the counter
            showtimeIDCounter = currentID + 1;
        }
    }
}
