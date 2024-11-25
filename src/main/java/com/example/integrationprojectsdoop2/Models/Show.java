package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;
import java.time.LocalDate;

public class Show implements Serializable{

    private static int aShowIDCounter = 1;
    private final String aShowID;
    private Movie aMovie;
    private Screenroom aScreenroom;
    private Showtime aShowtime;
    private ETicket aTicket;

    public Show() {
        this.aShowID = generateShowID();
    }

    public Show(Movie pMovie, Screenroom pScreenroom, Showtime pShowtime, ETicket pTicket) {
        this.aShowID = generateShowID();
        setMovie(pMovie);
        setScreenroom(pScreenroom);
        setShowtime(pShowtime);
        setETicket(pTicket);
    }

    private static synchronized String generateShowID() {
        return "S" + aShowIDCounter++;
    }

    private Movie getMovie() {
        return aMovie;
    }

    private void setMovie(Movie pMovie) {
        if (pMovie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        aMovie = pMovie;
    }

    private Screenroom getScreenroom() {
        return aScreenroom;
    }

    private void setScreenroom(Screenroom pScreenroom) {
        if (pScreenroom == null) {
            throw new IllegalArgumentException("Screenroom cannot be null.");
        }
        aScreenroom = pScreenroom;
    }

    private Showtime getShowtime() {
        return aShowtime;
    }

    private void setShowtime(Showtime pShowtime) {
        if (pShowtime == null) {
            throw new IllegalArgumentException("Showtime cannot be null.");
        }
        aShowtime = pShowtime;
    }

    private ETicket getTicket() {
        return aTicket;
    }

    private void setETicket(ETicket pTicket) {
        if (pTicket == null) {
            throw new IllegalArgumentException("ETicket cannot be null.");
        }
        aTicket = pTicket;
    }
}
