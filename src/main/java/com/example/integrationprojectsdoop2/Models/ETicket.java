package com.example.integrationprojectsdoop2.Models;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private static int aCounter = lastIncrement();

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
        this.aTicketID = setETicketID();
    }

    /**
     * Reads the last incremented counter from the stored tickets in etickets.ser.
     * Resets to 1 if no tickets exist or the file is empty.
     */
    private static int lastIncrement() {
        List<ETicket> eTicketList;
        try {
            ReadObjects reader = new ReadObjects("etickets.ser");
            eTicketList = reader.read().stream()
                    .filter(ETicket.class::isInstance)
                    .map(ETicket.class::cast)
                    .collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            eTicketList = Collections.emptyList(); // Fallback in case of errors
        }

        if (eTicketList.isEmpty()) {
            return 1; // Start from 1 if no tickets exist
        }

        // Get the last ticket and extract its counter
        ETicket lastTicket = eTicketList.getLast();
        String lastTicketID = lastTicket.getaTicketID(); // Assuming getTicketID() exists
        String lastYear = lastTicketID.substring(0, 4); // Extract the year from the ID

        // If the year has changed, reset the counter
        if (!lastYear.equals(aLastYear)) {
            return 1;
        }

        // Extract and increment the 10-digit counter
        String counterPart = lastTicketID.substring(4);
        return Integer.parseInt(counterPart) + 1;
    }

    /**
     * Generates the next ETicket ID in the format Year + 10-digit counter.
     *
     * @return a formatted ETicket ID.
     */
    private synchronized String setETicketID() {
        String currentYear = String.valueOf(LocalDateTime.now().getYear());

        // Reset counter if the year changes
        if (!currentYear.equals(aLastYear)) {
            aLastYear = currentYear;
            aCounter = 1;
        }

        // Format the ticket ID as Year + 10-digit counter (padded with leading zeros)
        return currentYear + String.format("%010d", aCounter++);
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
     * Retrieves the show associated with the ticket.
     *
     * @return the show.
     */
    public Show getaShow() {
        return this.aShow;
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
     * Retrieves the client who purchased the ticket.
     *
     * @return the client.
     */
    public Client getaClient() {
        return this.aClient;
    }
}