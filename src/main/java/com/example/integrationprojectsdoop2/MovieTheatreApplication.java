package com.example.integrationprojectsdoop2;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieTheatreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource("Login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 513);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {




        List<Object> objects = new ArrayList<>();
        objects.add("Sample String");
        objects.add(123); // Integer
        objects.add(45.67); // Double

        String fileName = "test1.ser";

        // Instantiate the WriteObjects utility and write the objects
        WriteObjects writer = new WriteObjects(fileName);
        writer.write(objects);



        ReadObjects reader = new ReadObjects(fileName);
        try {
            List<Object> objectz = reader.read();
            objects.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        launch();
    }
}