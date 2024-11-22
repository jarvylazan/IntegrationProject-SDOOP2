package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;

/**
 * Represents a Manager, which is a type of {@link User}.
 * Inherits all properties and behaviors of the {@link User} class.
 * Implements {@link Serializable} for object serialization.
 */
public class Manager extends User implements Serializable {

    private static int managerIDCounter = 1; // Counter specific to Manager
    private final String managerID;         // Unique ID for each Manager

    /**
     * Constructs a Manager with the specified user details.
     *
     * @param pUser_ID       the unique identifier for the user.
     * @param pUser_Name     the name of the user.
     * @param pUser_Email    the email address of the user.
     * @param pUser_Password the password of the user.
     */
    public Manager(String pUser_ID, String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.managerID = generateManagerID();
    }

    private static synchronized String generateManagerID() {
        return "M" + managerIDCounter++;
    }

    public String getManagerID() {
        return managerID;
    }

}
