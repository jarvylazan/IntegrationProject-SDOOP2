package com.example.integrationprojectsdoop2.Models;

import java.time.LocalDate;

public class Show {

    private static int aShowIDCounter = 1;

    private String aShow_ID;
    private LocalDate aShow_Date;
    private String aClient_Name;
    private String aMovie_Title;
    private String aScreenroom_Name;
    private String aShowtime_Time;

    public Show() {}

    public Show(LocalDate pShow_Date, String pClient_Name, String pMovie_Title, String pScreenroom_Name, String pShowtime_Time) {
        this.aShow_ID = setaShowID();
        this.aShow_Date = pShow_Date;
        this.aClient_Name = pClient_Name;
        this.aMovie_Title = pMovie_Title;
        this.aScreenroom_Name = pScreenroom_Name;
        this.aShowtime_Time = pShowtime_Time;
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