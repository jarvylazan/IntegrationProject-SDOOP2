package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents a Client, which is a type of {@link User}.
 * A Client has a subscription date and a unique client ID.
 * Implements {@link Serializable} for object serialization.
 * This class is designed to manage clients of the application.
 * Each client is assigned a unique ID and a subscription date upon creation.
 *
 * @author Samuel Mireault
 * @version 1.0
 */
public class Client extends User {

    /** The serial version UID for serialization. */
    @Serial
    private static final long serialVersionUID = -5727091206595037865L;

    /** The list of all clients managed by the {@link UserManager}. */
    private final List<User> aClientsList = UserManager.getaInstance().getaClientsList();

    /** Counter for generating unique Client IDs. */
    private int aClientIDCounter = lastIncrement();

    /** Unique ID for each Client. */
    private final String aClientID;

    /** The subscription date for the client. */
    private final LocalDate aClientSubscriptionDate;

    /** Date formatter for displaying the subscription date. */
    private static final DateTimeFormatter aDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * Default constructor that initializes the client with default values.
     * The client is assigned a unique ID, and the subscription date is set to the current date.
     * The client is automatically added to the global client list upon creation.
     *
     * @author Samuel Mireault
     */
    public Client() {
        super(); // Calls the default constructor of User
        this.aClientSubscriptionDate = LocalDate.now();
        this.aClientID = generateClientID();
    }

    /**
     * Constructs a new Client with the specified user details.
     * The client is assigned a unique ID, and the subscription date is set to the current date.
     *
     * @param pUser_Name    the name of the user.
     * @param pUser_Email   the email address of the user.
     * @param pUser_Password the password of the user.
     * @throws IllegalArgumentException if any of the parameters are invalid or null.
     * @author Samuel Mireault
     */
    public Client(String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.aClientSubscriptionDate = LocalDate.now();
        this.aClientID = generateClientID();
    }

    /**
     * Generates a unique Client ID by incrementing the ID counter.
     *
     * @return the generated Client ID in the format "C<number>".
     * @author Samuel Mireault
     */
    private synchronized String generateClientID() {
        return "C" + aClientIDCounter++;
    }

    /**
     * Gets the unique Client ID.
     *
     * @return the unique Client ID as a string in the format "C<number>".
     * @author Samuel Mireault
     */
    public String getClientID() {
        return aClientID;
    }

    /**
     * Gets the subscription date of the client.
     *
     * @return the subscription date of the client as a {@link LocalDate}.
     * @author Samuel Mireault
     */
    public LocalDate getClientSubscriptionDate() {
        return aClientSubscriptionDate;
    }

    /**
     * Gets the subscription date as a formatted string.
     * The format used is "yyyy/MM/dd".
     *
     * @return the subscription date formatted as "yyyy/MM/dd".
     * @author Samuel Mireault
     */
    public String getFormattedSubscriptionDate() {
        return aClientSubscriptionDate.format(aDATE_FORMATTER);
    }

    /**
     * Determines the last increment value for generating unique Client IDs.
     * If the client list is empty or uninitialized, the increment starts at 1.
     *
     * @return the next increment value for generating unique Client IDs.
     * @throws ClassCastException if the list contains objects that are not of type {@link Client}.
     * @author Samuel Mireault
     */
    private int lastIncrement() {
        if (this.aClientsList == null || this.aClientsList.isEmpty()) {
            return 1;  // Start at 1 if the list is empty or uninitialized
        }
        Client lastClient = (Client) this.aClientsList.getLast();  // Get the last client
        int lastIncrement = Integer.parseInt(lastClient.getClientID().substring(1));
        return lastIncrement + 1;
    }
}
