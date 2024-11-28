package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Client, which is a type of {@link User}.
 * A Client has a subscription date and a unique client ID.
 * Implements {@link Serializable} for object serialization.
 * @author Samuel
 */
public class Client extends User {

    /** The serialID for the files. */
    @Serial
    private static final long serialVersionUID = -5727091206595037865L;


    /** Counter for generating unique Client IDs. */
    private static int aClientIDCounter = 1;

    /** Unique ID for each Client. */
    private final String aClientID;

    /** The subscription date for the client. */
    private final LocalDate aClientSubscriptionDate;

    /** Date formatter for displaying the subscription date. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * Default constructor that initializes the client with default values.
     * The client is assigned a unique ID, and the subscription date is set to the current date.
     * @author Samuel
     */
    public Client() {
        super(); // Calls the default constructor of User
        this.aClientID = generateClientID();
        this.aClientSubscriptionDate = LocalDate.now();
    }

    /**
     * Constructs a new Client with the specified user details and subscription date.
     *
     * @param pUser_Name              the name of the user.
     * @param pUser_Email             the email address of the user.
     * @param pUser_Password          the password of the user.
     * @throws IllegalArgumentException if the subscription date is null.
     * @author Samuel
     */
    public Client(String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.aClientID = generateClientID();
        this.aClientSubscriptionDate =  LocalDate.now();
    }

    /**
     * Generates a unique Client ID by incrementing the counter.
     *
     * @return the generated Client ID in the format "C<number>".
     * @author Samuel
     */
    private static synchronized String generateClientID() {
        return "C" + aClientIDCounter++;
    }

    /**
     * Gets the unique Client ID.
     *
     * @return the unique Client ID.
     * @author Samuel
     */
    public String getClientID() {
        return aClientID;
    }

    /**
     * Gets the client's subscription date.
     *
     * @return the subscription date of the client.
     * @author Samuel
     */
    public LocalDate getaClientSubscriptionDate() {
        return aClientSubscriptionDate;
    }

    /**
     * Gets the subscription date as a formatted string.
     *
     * @return the subscription date formatted as "yyyy/MM/dd".
     * @author Samuel
     */
    public String getFormattedSubscriptionDate() {
        return aClientSubscriptionDate.format(DATE_FORMATTER);
    }
}
