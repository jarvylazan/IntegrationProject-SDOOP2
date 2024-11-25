package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;

/**
 * Represents a Manager, which is a type of {@link User}.
 * Inherits all properties and behaviors of the {@link User} class.
 * Implements {@link Serializable} for object serialization.
 * Each Manager is assigned a unique manager ID.
 * @author Samuel
 */
public class Manager extends User {

    /** Counter for generating unique Manager IDs. */
    private static int managerIDCounter = 1;

    /** Unique ID for each Manager. */
    private final String managerID;

    /**
     * Constructs a Manager with the specified user details.
     *
     * @param pUser_Name     the name of the user.
     * @param pUser_Email    the email address of the user.
     * @param pUser_Password the password of the user.
     * @throws IllegalArgumentException if the email is invalid or any parameter is null/empty.
     * @author Samuel
     */
    public Manager(String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.managerID = generateManagerID();
    }

    /**
     * Generates a unique Manager ID by incrementing the counter.
     *
     * @return the generated Manager ID in the format "M<number>".
     * @author Samuel
     */
    private static synchronized String generateManagerID() {
        return "M" + managerIDCounter++;
    }

    /**
     * Gets the unique Manager ID.
     *
     * @return the Manager ID.
     * @author Samuel
     */
    public String getManagerID() {
        return managerID;
    }
}
