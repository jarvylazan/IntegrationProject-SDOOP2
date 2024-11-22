package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Client, which is a type of {@link User}.
 * In addition to the user properties, a Client has a subscription date.
 * Implements {@link Serializable} for object serialization.
 */
public class Client extends User implements Serializable {

    private static int clientIDCounter = 1; // Counter specific to Client
    private final String clientID;         // Unique ID for each Client

    /** The subscription date for the client. */
    private LocalDate aClientSubscriptionDate;

    /** Date formatter for displaying the subscription date. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * Default constructor that initializes the client with default values.
     * Sets the subscription date format to "yyyy/MM/dd".
     */
    public Client() {
        super(); // Calls the default constructor of User
        this.clientID = generateClientID();
        aClientSubscriptionDate = LocalDate.now(); // To include time, use "yyyy/MM/dd HH:mm:ss".
    }

    /**
     * Constructs a new Client with the specified user details and subscription date.
     *
     * @param pUser_ID                the unique identifier for the user.
     * @param pUser_Name              the name of the user.
     * @param pUser_Email             the email address of the user.
     * @param pUser_Password          the password of the user.
     * @param pUser_Type              the type of the user.
     * @param pClientSubscriptionDate the subscription date for the client.
     */
    public Client(String pUser_ID, String pUser_Name, String pUser_Email, String pUser_Password,
                  String pUser_Type, LocalDate pClientSubscriptionDate) {
        super(pUser_ID, pUser_Name, pUser_Email);
        this.clientID = generateClientID();
        aClientSubscriptionDate = pClientSubscriptionDate;
    }

    private static synchronized String generateClientID() {
        return "C" + clientIDCounter++;
    }

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
     * @param aClientSubscriptionDate the new subscription date for the client.
     */
    public void setaClientSubscriptionDate(LocalDate aClientSubscriptionDate) {
        this.aClientSubscriptionDate = aClientSubscriptionDate;
    }

}
