package com.example.integrationprojectsdoop2;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.Client;
import com.example.integrationprojectsdoop2.Models.Manager;
import com.example.integrationprojectsdoop2.Models.User;
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

        User manager = new Manager("m","m@m.com", "m");
        User client = new Client("c","c@c.com", "c");

        // Write manager to manager.ser
        try {
            WriteObjects managerWriter = new WriteObjects("manager.ser");
            List<Object> managers = new ArrayList<>();
            managers.add(manager);
            managerWriter.write(managers);
            System.out.println("Manager data written successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write client to client.ser
        try {
            WriteObjects clientWriter = new WriteObjects("client.ser");
            List<Object> clients = new ArrayList<>();
            clients.add(client);
            clientWriter.write(clients);
            System.out.println("Client data written successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    




        launch();
    }
}