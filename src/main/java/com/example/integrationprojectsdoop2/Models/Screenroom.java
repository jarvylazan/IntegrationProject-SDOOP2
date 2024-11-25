package com.example.integrationprojectsdoop2.Models;

/**
 * Represents a Screenroom with attributes such as ID and name.
 * Provides validation for attribute values to ensure data integrity.
 *
 * @author Jarvy Lazan
 */
public class Screenroom {
    private int screenroom_ID;
    private String screenroom_Name;

    /**
     * Default constructor for creating a Screenroom instance without initializing fields.
     *
     * @author Jarvy Lazan
     */
    public Screenroom() {
    }

    /**
     * Parameterized constructor for creating a Screenroom instance with specified values.
     * Uses setters for validation.
     *
     * @param screenroom_ID   the unique identifier of the screenroom (must be positive).
     * @param screenroom_Name the name of the screenroom (cannot be null or empty).
     * @throws IllegalArgumentException if any parameter is invalid.
     * @author Jarvy Lazan
     */
    public Screenroom(int screenroom_ID, String screenroom_Name) {
        setScreenroom_ID(screenroom_ID);
        setScreenroom_Name(screenroom_Name);
    }

    /**
     * Gets the unique identifier of the screenroom.
     *
     * @return the screenroom ID.
     * @author Jarvy Lazan
     */
    public int getScreenroom_ID() {
        return screenroom_ID;
    }

    /**
     * Sets the unique identifier of the screenroom. Must be positive.
     *
     * @param screenroom_ID the screenroom ID to set.
     * @throws IllegalArgumentException if the screenroom ID is not positive.
     * @author Jarvy Lazan
     */
    public void setScreenroom_ID(int screenroom_ID) {
        if (screenroom_ID <= 0) {
            throw new IllegalArgumentException("Screenroom ID must be a positive integer.");
        }
        this.screenroom_ID = screenroom_ID;
    }

    /**
     * Gets the name of the screenroom.
     *
     * @return the screenroom name.
     * @throws IllegalArgumentException if the screenroom name is null or empty.
     * @author Jarvy Lazan
     */
    public String getScreenroom_Name() {
        return screenroom_Name;
    }

    /**
     * Sets the name of the screenroom. Cannot be null or empty.
     *
     * @param screenroom_Name the screenroom name to set.
     * @throws IllegalArgumentException if the screenroom name is null or empty.
     * @author Jarvy Lazan
     */
    public void setScreenroom_Name(String screenroom_Name) {
        if (screenroom_Name == null || screenroom_Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Screenroom Name cannot be null or empty.");
        }
        this.screenroom_Name = screenroom_Name;
    }
}
