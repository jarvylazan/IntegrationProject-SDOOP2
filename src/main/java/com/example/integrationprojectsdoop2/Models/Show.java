package com.example.integrationprojectsdoop2.Models;

import java.time.LocalDate;

/**
 * Represents a Showtime with a unique identifier and a specific time.
 *
 * The unique identifier is auto-generated using a static counter. The time is validated
 * to ensure it follows the format HH:mm (e.g., 12:34).
 *
 * @author Mohammad Tarin Wahidi
 */
public class Show {

    private static int aShowIDCounter = 1;

    private String aShow_ID;
    private LocalDate aShow_Date;
    private String aClient_ID;
    private String aMovie_ID;
    private String aScreenroom_ID;
    private String aShowtime_ID;


    /**
     * Default constructor for creating a Showtime instance without initializing fields.
     */
    public Show() {}

    public Show(LocalDate pShow_Date, String pClient_ID, String pMovie_ID, String pScreenroom_ID, String pShowtime_ID) {
        this.aShow_ID = setaShowID();
        this.aShow_Date = pShow_Date;
        this.aClient_ID = pClient_ID;
        this.aMovie_ID = pMovie_ID;
        this.aScreenroom_ID = pScreenroom_ID;
        this.aShowtime_ID = pShowtime_ID;
    }


    public String getaShowID() {
        return this.aShow_ID;
    }


    private static synchronized String setaShowID() {
        return "S" + aShowIDCounter++;
    }

    public LocalDate getaShowDate() {
        return this.aShow_Date;
    }

    public void setaShowDate(LocalDate pShow_Date) {
        if (pShow_Date == null) {
            throw new IllegalArgumentException("Show date cannot be null.");
        }

        if (pShow_Date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Show date cannot be in the past.");
        }

        this.aShow_Date = pShow_Date;
    }
}