package com.example.integrationprojectsdoop2.Models;

/**
 * Represents a Screenroom with attributes such as ID and name.
 * Provides validation for attribute values to ensure data integrity.
 *
 * @author Jarvy Lazan
 */
public class Screenroom {
    private int aScreenroom_ID;
    private String aScreenroom_Name;

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
     * @param pScreenroom_ID   the unique identifier of the screenroom (must be positive).
     * @param pScreenroom_Name the name of the screenroom (cannot be null or empty).
     * @throws IllegalArgumentException if any parameter is invalid.
     * @author Jarvy Lazan
     */
    public Screenroom(int pScreenroom_ID, String pScreenroom_Name) {
        setAScreenroom_ID(pScreenroom_ID);
        setAScreenroom_Name(pScreenroom_Name);
    }

    /**
     * Gets the unique identifier of the screenroom.
     *
     * @return the screenroom ID.
     * @author Jarvy Lazan
     */
    public int getAScreenroom_ID() {
        return aScreenroom_ID;
    }

    /**
     * Sets the unique identifier of the screenroom. Must be positive.
     *
     * @param pScreenroom_ID the screenroom ID to set.
     * @throws IllegalArgumentException if the screenroom ID is not positive.
     * @author Jarvy Lazan
     */
    public void setAScreenroom_ID(int pScreenroom_ID) {
        if (pScreenroom_ID <= 0) {
            throw new IllegalArgumentException("Screenroom ID must be a positive integer.");
        }
        this.aScreenroom_ID = pScreenroom_ID;
    }

    /**
     * Gets the name of the screenroom.
     *
     * @return the screenroom name.
     * @throws IllegalArgumentException if the screenroom name is null or empty.
     * @author Jarvy Lazan
     */
    public String getAScreenroom_Name() {
        return aScreenroom_Name;
    }

    /**
     * Sets the name of the screenroom. Cannot be null or empty.
     *
     * @param pScreenroom_Name the screenroom name to set.
     * @throws IllegalArgumentException if the screenroom name is null or empty.
     * @author Jarvy Lazan
     */
    public void setAScreenroom_Name(String pScreenroom_Name) {
        if (pScreenroom_Name == null || pScreenroom_Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Screenroom Name cannot be null or empty.");
        }
        this.aScreenroom_Name = pScreenroom_Name;
    }
}
