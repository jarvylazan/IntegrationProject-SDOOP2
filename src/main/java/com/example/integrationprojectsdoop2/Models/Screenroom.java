package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Screenroom with attributes such as ID and name.
 * Implements {@link Serializable} for object serialization.
 * Each Screenroom is assigned a unique Screenroom ID.
 * Provides validation for input values to ensure data integrity.
 *
 * @author Jarvy Lazan
 */
public class Screenroom implements Serializable, ShowComponent {
    @Serial
    private static final long serialVersionUID = 34L;

    /** Counter for generating unique Screenroom IDs. */
    private static int screenroomIDCounter = 1;

    /** Unique ID for each Screenroom. */
    private final String aScreenroom_ID;

    /** Name of the Screenroom. */
    private String aScreenroom_Name;

    /**
     * Empty Constructor for the Screenroom class.
     * Automatically generates a unique Screenroom ID.
     * Other attributes can be set using setter methods
     *
     * @author Jarvy Lazan
     */
    public Screenroom() {
        this.aScreenroom_ID = generateScreenroomID();
    }

    /**
     * Constructs a Screenroom with the specified name.
     *
     * @param pScreenroom_Name the name of the screenroom (cannot be null or empty).
     * @throws IllegalArgumentException if the name is null or empty.
     * @author Jarvy Lazan
     */
    public Screenroom(String pScreenroom_Name) {
        this.aScreenroom_ID = generateScreenroomID();
        this.setScreenroom_Name(pScreenroom_Name);
    }

    /**
     * Generates a unique Screenroom ID by incrementing the counter.
     *
     * @return the generated Screenroom ID in the format "R<number>".
     * @author Jarvy Lazan
     */
    private static synchronized String generateScreenroomID() {
        return "R" + screenroomIDCounter++;
    }

    /**
     * Gets the unique Screenroom ID.
     *
     * @return the Screenroom ID.
     * @author Jarvy Lazan
     */
    public String getAScreenroom_ID() {
        return this.aScreenroom_ID;
    }

    /**
     * Gets the name of the Screenroom.
     *
     * @return the Screenroom name.
     * @author Jarvy Lazan
     */
    public String getScreenroom_Name() {
        return this.aScreenroom_Name;
    }

    /**
     * Sets the name of the Screenroom. Cannot be null or empty.
     *
     * @param pScreenroom_Name the screenroom name to set.
     * @throws IllegalArgumentException if the name is null or empty.
     * @author Jarvy Lazan
     */
    public void setScreenroom_Name(String pScreenroom_Name) {
        if (pScreenroom_Name == null || pScreenroom_Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Screenroom Name cannot be null or empty.");
        }
        this.aScreenroom_Name = pScreenroom_Name;
    }

    @Override
    public String getDisplayName() {
        return aScreenroom_Name;
    }

    @Override
    public String toString() {
        return "Screenroom: " + aScreenroom_Name;
    }

    /**
     * Resets the Screenroom ID counter based on the highest existing ID.
     *
     * @param existingScreenrooms a list of existing Screenroom objects.
     */
    public static void resetScreenroomIDCounter(List<Screenroom> existingScreenrooms) {
        if (existingScreenrooms != null && !existingScreenrooms.isEmpty()) {
            screenroomIDCounter = existingScreenrooms.stream()
                    .mapToInt(screenroom -> Integer.parseInt(screenroom.getAScreenroom_ID().substring(1)))
                    .max().orElse(0) + 1; // Find the max ID and set counter to max + 1
        }
    }

    /**
     * Ensures the ID counter is correct after deserialization.
     *
     * @return the deserialized object.
     */
    @Serial
    private Object readResolve() {
        updateScreenroomIDCounter();
        return this;
    }

    /**
     * Updates the Screenroom ID counter based on the current instance ID.
     */
    private void updateScreenroomIDCounter() {
        int currentID = Integer.parseInt(this.aScreenroom_ID.substring(1));
        if (currentID >= screenroomIDCounter) { // Only update if the current ID is greater than the counter
            screenroomIDCounter = currentID + 1;
        }
    }
}
