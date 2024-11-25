package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Client, which is a type of {@link User}.
 * A Client has a subscription date and a unique client ID.
 * Implements {@link Serializable} for object serialization.
 */
public class Client extends User {

    /** Counter for generating unique Client IDs. */
    private static int clientIDCounter = 1;

    /** Unique ID for each Client. */
    private final String clientID;

    /** The subscription date for the client. */
    private LocalDate aClientSubscriptionDate;

    /** Date formatter for displaying the subscription date. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * Default constructor that initializes the client with default values.
     * The client is assigned a unique ID, and the subscription date is set to the current date.
     */
    public Client() {
        super(); // Calls the default constructor of User
        this.clientID = generateClientID();
        this.aClientSubscriptionDate = LocalDate.now();
    }

    /**
     * Constructs a new Client with the specified user details and subscription date.
     *
     * @param pUser_Name              the name of the user.
     * @param pUser_Email             the email address of the user.
     * @param pUser_Password          the password of the user.
     * @throws IllegalArgumentException if the subscription date is null.
     */
    public Client(String pUser_Name, String pUser_Email, String pUser_Password) {
        super(pUser_Name, pUser_Email, pUser_Password);
        this.clientID = generateClientID();
        this.aClientSubscriptionDate =  LocalDate.now();
    }

    /**
     * Generates a unique Client ID by incrementing the counter.
     *
     * @return the generated Client ID in the format "C<number>".
     */
    private static synchronized String generateClientID() {
        return "C" + clientIDCounter++;
    }

    /**
     * Gets the unique Client ID.
     *
     * @return the unique Client ID.
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Gets the client's subscription date.
     *
     * @return the subscription date of the client.
     */
    public LocalDate getaClientSubscriptionDate() {
        return aClientSubscriptionDate;
    }

    /**
     * Sets the client's subscription date.
     *
     * @param pClientSubscriptionDate the new subscription date for the client.
     * @throws IllegalArgumentException if the subscription date is null or in the future.
     */
    public void setaClientSubscriptionDate(LocalDate pClientSubscriptionDate) {
        if (pClientSubscriptionDate == null) {
            throw new IllegalArgumentException("Subscription date cannot be null.");
        }
        if (pClientSubscriptionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Subscription date cannot be in the future.");
        }
        this.aClientSubscriptionDate = pClientSubscriptionDate;
    }

    /**
     * Gets the subscription date as a formatted string.
     *
     * @return the subscription date formatted as "yyyy/MM/dd".
     */
    public String getFormattedSubscriptionDate() {
        return aClientSubscriptionDate.format(DATE_FORMATTER);
    }
}
