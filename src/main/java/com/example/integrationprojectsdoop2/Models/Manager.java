package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Manager, which is a type of {@link User}.
 * Inherits all properties and behaviors of the {@link User} class.
 * Implements {@link Serializable} for object serialization.
 * Each Manager is assigned a unique manager ID upon creation.
 * The class is part of a user management system for the application.
 *
 * @author Samuel Mireault
 * @version 1.0
 */
public class Manager extends User {

    /** The serial version UID for serialization. */
    @Serial
    private static final long serialVersionUID = 1824568323534453788L;

    /** List of all managers managed by the {@link UserManager}. */
    private final List<User> aManagersList = UserManager.getaInstance().getaManagersList();

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
     * @author Samuel Mireault
     */
    public Manager(String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.aManagerID = generateManagerID();
    }

    /**
     * Generates a unique Manager ID by incrementing the ID counter.
     * The format of the ID is "M<number>", where the number is generated sequentially.
     *
     * @return the generated Manager ID.
     * @author Samuel Mireault
     */
    private synchronized String generateManagerID() {
        return "M" + aManagerIDCounter++;
    }

    /**
     * Gets the unique Manager ID assigned to this Manager.
     *
     * @return the Manager ID as a string in the format "M<number>".
     * @author Samuel Mireault
     */
    public String getManagerID() {
        return aManagerID;
    }

    /**
     * Determines the last increment value for generating unique Manager IDs.
     * If the manager's list is empty or uninitialized, the increment starts at 1.
     *
     * @return the next increment value for generating unique Manager IDs.
     * @throws ClassCastException if the list contains objects that are not of type {@link Manager}.
     * @author Samuel Mireault
     */
    private int lastIncrement() {
        if (this.aManagersList == null || this.aManagersList.isEmpty()) {
            return 1;  // Start at 1 if the list is empty or uninitialized
        }
        Client lastClient = (Client) this.aManagersList.getLast();  // Get the last client
        int lastIncrement = Integer.parseInt(lastClient.getClientID().substring(1));
        return lastIncrement + 1;
    }
}
