package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ETicket Class
 *
 * This class represents an e-ticket for a show, including unique ticket IDs,
 * associated show details, client information, and purchase date/time.
 * The ticket ID is dynamically generated to ensure uniqueness within each year.
 *
 * Author: Mohammad Tarin Wahidi
 */
public class ETicket implements Serializable {
    @Serial
    private static final long serialVersionUID = 80085L;

    /**
     * The last recorded year for which ticket IDs were generated.
     */
    private static String aLastYear = String.valueOf(LocalDateTime.now().getYear());

    /**
     * Counter for ticket IDs within the current year.
     */
    private static int aCounter = 0;

    /**
     * The unique ID of the ticket.
     */
    private String aTicketID;

    /**
     * The show associated with the ticket.
     */
    private Show aShow;

    /**
     * The date and time of the ticket purchase.
     */
    private LocalDateTime aPurchaseDateTime;

    /**
     * The client who purchased the ticket.
     */
    private Client aClient;

    /**
     * Constructs a new ETicket with the given show ID and client ID.
     *
     * @param pShow  the ID of the show associated with the ticket.
     * @param pClient the ID of the client purchasing the ticket.
     */
    public ETicket(Show pShow, Client pClient) {
        this.aShow = pShow;
        this.aPurchaseDateTime = LocalDateTime.now();
        this.aClient = pClient;
        this.aTicketID = setTicketID();
    }

    /**
     * Generates a unique ticket ID based on the current year and a counter.
     * Resets the counter if the year changes.
     *
     * @return a unique ticket ID in the format "YYYYNNNNNNNNNN".
     */
    private static synchronized String setTicketID() {
        String currentYear = String.valueOf(LocalDateTime.now().getYear());

        // Reset counter if the year changes
        if (!currentYear.equals(aLastYear)) {
            aLastYear = currentYear;
            aCounter = 0;
        }

        // Increment the counter
        aCounter++;

        // Format the ticket ID as Year + 10-digit counter (padded with leading zeros)
        return currentYear + String.format("%010d", aCounter);
    }

    /**
     * Retrieves the unique ticket ID.
     *
     * @return the ticket ID.
     */
    public String getTicketID() {
        return this.aTicketID;
    }

    /**
     * Retrieves the show associated with the ticket.
     *
     * @return the show.
     */
    public Show getShow() {
        return this.aShow;
    }

    /**
     * Retrieves the date and time when the ticket was purchased.
     *
     * @return the purchase date and time.
     */
    public LocalDateTime getPurchaseDateTime() {
        return this.aPurchaseDateTime;
    }

    /**
     * Retrieves the client who purchased the ticket.
     *
     * @return the client.
     */
    public Client getClient() {
        return this.aClient;
    }
}