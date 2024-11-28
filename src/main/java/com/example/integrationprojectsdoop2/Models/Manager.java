package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Manager, which is a type of {@link User}.
 * Inherits all properties and behaviors of the {@link User} class.
 * Implements {@link Serializable} for object serialization.
 * Each Manager is assigned a unique manager ID.
 * @author Samuel
 */
public class Manager extends User {

    /** The serialID for the files. */
    @Serial
    private static final long serialVersionUID = 1824568323534453788L;

    private final List<User> aManagersList  = UserManager.getInstance().getaManagersList();

    /** Counter for generating unique Manager IDs. */
    private int aManagerIDCounter = lastIncrement();

    /** Unique ID for each Manager. */
    private final String aManagerID;

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
        this.aManagerID = generateManagerID();
    }

    /**
     * Generates a unique Manager ID by incrementing the counter.
     *
     * @return the generated Manager ID in the format "M<number>".
     * @author Samuel
     */
    private synchronized String generateManagerID() {
        return "M" + aManagerIDCounter++;
    }

    /**
     * Gets the unique Manager ID.
     *
     * @return the Manager ID.
     * @author Samuel
     */
    public String getManagerID() {
        return aManagerID;
    }

    private int lastIncrement() {
        if (this.aManagersList == null || this.aManagersList.isEmpty()) {
            return 1;  // Start at 1 if the list is empty or uninitialized
        }
        Client lastClient = (Client) this.aManagersList.getLast();  // Get the last client
        int lastIncrement = Integer.parseInt(lastClient.getClientID().substring(1));
        return lastIncrement + 1;
    }
}
