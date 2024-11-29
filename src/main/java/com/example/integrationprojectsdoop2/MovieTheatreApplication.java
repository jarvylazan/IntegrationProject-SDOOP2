package com.example.integrationprojectsdoop2;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieTheatreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 513);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        Show manager = new Show(new Movie("Jaws","Suspense","Dont watch alone in at Sea"),
                new Screenroom("Mocho Grander"),
                new Showtime("14:30"),
                new ETicket("S1","C1"));

        // Write manager to manager.ser
        try {
            WriteObjects managerWriter = new WriteObjects("shows.ser");
            List<Object> managers = new ArrayList<>();
            managers.add(manager);
            managerWriter.write(managers);
            System.out.println("Manager data written successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }



        launch();
    }
}