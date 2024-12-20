package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Showtime with a unique identifier and a specific time.
 * Implements {@link Serializable} for object serialization.
 * Each Showtime has a unique ID automatically generated.
 * The time is validated to ensure it follows the format HH:mm (e.g., 12:34).
 * <p>
 * Implements the {@link ShowComponent} interface for display functionality in the system.
 * This class also ensures proper handling of ID counters during serialization and deserialization.
 *
 * @author Mohammad Tarin Wahidi
 * @version 1.0
 */
public class Showtime implements Serializable, ShowComponent {

    @Serial
    private static final long serialVersionUID = 68L;

    /** Counter for generating unique showtime IDs, incremented with each instance. */
    private static int aShowtimeIDCounter = 1;

    /** Unique identifier for the showtime, auto-generated. */
    private final String aShowtime_ID;

    /** Specific time for the showtime, validated to follow the format HH:mm. */
    private String aShowtime_Time;

    /**
     * Default constructor for creating a Showtime instance without initializing fields.
     * Automatically generates a unique Showtime ID.
     *
     * @author Mohammad Tarin Wahidi
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
     * @author Mohammad Tarin Wahidi
     */
    public Showtime(String pShowtime_Time) {
        this.aShowtime_ID = generateShowtimeID();
        setShowtimeTime(pShowtime_Time); // Calls setter for validation.
    }

    /**
     * Generates a unique Showtime ID by incrementing the counter.
     *
     * @return the generated Showtime ID in the format "T<number>".
     * @author Mohammad Tarin Wahidi
     */
    private static synchronized String generateShowtimeID() {
        return "T" + (aShowtimeIDCounter++);
    }

    /**
     * Gets the unique identifier of the showtime.
     *
     * @return the Showtime ID.
     * @author Mohammad Tarin Wahidi
     */
    public String getShowtimeID() {
        return this.aShowtime_ID;
    }

    /**
     * Gets the time of the showtime.
     *
     * @return the showtime time in the format HH:mm.
     * @author Mohammad Tarin Wahidi
     */
    public String getShowtimeTime() {
        return this.aShowtime_Time;
    }

    /**
     * Sets the time of the showtime. The time must follow the format HH:mm (e.g., 12:34).
     *
     * @param pShowtime_Time the showtime time to set.
     * @throws IllegalArgumentException if the showtime time does not match the format HH:mm.
     * @author Mohammad Tarin Wahidi & Jarvy Lazan
     */
    public void setShowtimeTime(String pShowtime_Time) {
        String normalizedTime = normalizeTime(pShowtime_Time); // Normalize to HH:mm
        String timePattern = "^(?:[01]?\\d|2[0-3]):[0-5]\\d$";

        if (normalizedTime == null || !normalizedTime.matches(timePattern)) {
            throw new IllegalArgumentException("A showtime must follow the format H:mm or HH:mm (e.g., 9:30, 12:45) and be within valid time bounds.");
        }

        this.aShowtime_Time = normalizedTime;
    }

    /**
     * Normalizes a given time string into a standard HH:mm format.
     *
     * This method takes a time string in the format "H:mm" or "HH:mm",
     * splits it into hours and minutes, and ensures that the output is
     * consistently formatted as "HH:mm" with leading zeros if necessary.
     *
     * @param pTime the input time string to normalize, in the format "H:mm" or "HH:mm".
     * @return the normalized time string in "HH:mm" format.
     * @throws NumberFormatException if the time string contains invalid numeric values.
     * @throws ArrayIndexOutOfBoundsException if the time string is improperly formatted.
     */
    private static String normalizeTime(String pTime) {
        String[] parts = pTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return String.format("%02d:%02d", hour, minute);
    }


    /**
     * Provides the display name of the Showtime.
     * Implements the {@link ShowComponent#getDisplayName()} method.
     *
     * @return the time of the Showtime as the display name.
     * @author Mohammad Tarin Wahidi
     */
    @Override
    public String getDisplayName() {
        return aShowtime_Time;
    }

    /**
     * Returns a string representation of the Showtime object.
     *
     * @return a string containing the Showtime time.
     * @author Mohammad Tarin Wahidi
     */
    @Override
    public String toString() {
        return aShowtime_Time;
    }

    /**
     * Resets the Showtime ID counter based on the highest existing ID.
     * Ensures the counter starts from the next ID after the highest existing ID.
     *
     * @param pExistingShowtimes a list of existing Showtime objects.
     * @author Jarvy Lazan
     */
    public static void resetShowtimeIDCounter(List<Showtime> pExistingShowtimes) {
        if (pExistingShowtimes != null && !pExistingShowtimes.isEmpty()) {
            aShowtimeIDCounter = pExistingShowtimes.stream()
                    .mapToInt(showtime -> Integer.parseInt(showtime.getShowtimeID().substring(1)))
                    .max().orElse(0) + 1; // Find the max ID and set counter to max + 1
        }
    }

    /**
     * Ensures the ID counter is correct after deserialization.
     * Updates the counter to reflect the highest ID across deserialized objects.
     *
     * @return the deserialized Showtime object.
     * @author Jarvy Lazan
     */
    @Serial
    private Object readResolve() {
        updateShowtimeIDCounter();
        return this;
    }

    /**
     * Updates the Showtime ID counter based on the current instance ID.
     * Ensures the counter is at least one higher than the current Showtime's ID.
     *
     * @throws NumberFormatException if the Showtime ID is improperly formatted.
     * @author Jarvy Lazan
     */
    private void updateShowtimeIDCounter() {
        int currentID = Integer.parseInt(this.aShowtime_ID.substring(1));
        if (currentID >= aShowtimeIDCounter) { // Only update if the current ID is greater than the counter
            aShowtimeIDCounter = currentID + 1;
        }
    }
}
