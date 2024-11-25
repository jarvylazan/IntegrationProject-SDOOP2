package com.example.integrationprojectsdoop2.Models;

import java.time.LocalDateTime;

/**
 * ETicket Class
 *
 * This class represents an e-ticket for a show, including unique ticket IDs,
 * associated show details, client information, and purchase date/time.
 * The ticket ID is dynamically generated to ensure uniqueness within each year.
 *
 * Author: Mohammad Tarin Wahidi
 * Created on: November 24th, 2024
 */
public class ETicket {

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
    private final String aTicketID;

    /**
     * The ID of the show associated with the ticket.
     */
    private final String aShowID;

    /**
     * The date and time of the ticket purchase.
     */
    private LocalDateTime aPurchaseDateTime;

    /**
     * The ID of the client who purchased the ticket.
     */
    private final String aClientID;

    /**
     * Constructs a new ETicket with the given show ID and client ID.
     *
     * @param pShowID  the ID of the show associated with the ticket.
     * @param pClientID the ID of the client purchasing the ticket.
     */
    public ETicket(String pShowID, String pClientID) {
        this.aShowID = pShowID;
        this.aPurchaseDateTime = LocalDateTime.now();
        this.aClientID = pClientID;
        this.aTicketID = setaTicketID();
    }

    /**
     * Generates a unique ticket ID based on the current year and a counter.
     * Resets the counter if the year changes.
     *
     * @return a unique ticket ID in the format "YYYYNNNNNNNNNN".
     */
    private static synchronized String setaTicketID() {
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
    public String getaTicketID() {
        return this.aTicketID;
    }

    /**
     * Retrieves the ID of the show associated with the ticket.
     *
     * @return the show ID.
     */
    public String getaShowID() {
        return this.aShowID;
    }

    /**
     * Retrieves the date and time when the ticket was purchased.
     *
     * @return the purchase date and time.
     */
    public LocalDateTime getaPurchaseDateTime() {
        return this.aPurchaseDateTime;
    }

    /**
     * Retrieves the ID of the client who purchased the ticket.
     *
     * @return the client ID.
     */
    public String getaClientID() {
        return this.aClientID;
    }
}