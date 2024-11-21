package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Represents a Client, which is a type of {@link User}.
 * In addition to the user properties, a Client has a subscription date.
 * Implements {@link Serializable} for object serialization.
 */
public class Client extends User implements Serializable {

    /** The subscription date for the client. */
    private DateFormat aClientSubscriptionDate;

    /**
     * Default constructor that initializes the client with default values.
     * Sets the subscription date format to "yyyy/MM/dd".
     */
    public Client() {
        super(); // Calls the default constructor of User
        aClientSubscriptionDate = new SimpleDateFormat("yyyy/MM/dd"); // To include time, use "yyyy/MM/dd HH:mm:ss".
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
                  String pUser_Type, DateFormat pClientSubscriptionDate) {
        super(pUser_ID, pUser_Name, pUser_Email, pUser_Password, pUser_Type);
        aClientSubscriptionDate = pClientSubscriptionDate;
    }

    /**
     * Gets the client's subscription date.
     *
     * @return the subscription date of the client.
     */
    public DateFormat getaClientSubscriptionDate() {
        return aClientSubscriptionDate;
    }

    /**
     * Sets the client's subscription date.
     *
     * @param aClientSubscriptionDate the new subscription date for the client.
     */
    public void setaClientSubscriptionDate(DateFormat aClientSubscriptionDate) {
        this.aClientSubscriptionDate = aClientSubscriptionDate;
    }

    /**
     * Returns a string representation of the client, including user details and the subscription date.
     * The format is: "UserDetails,SubscriptionDate".
     *
     * @return a string representation of the client.
     */
    @Override
    public String toString() {
        return super.toString() + "," + aClientSubscriptionDate;
    }
}
