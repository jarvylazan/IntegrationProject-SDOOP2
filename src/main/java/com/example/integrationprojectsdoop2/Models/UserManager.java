package com.example.integrationprojectsdoop2.Models;

import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;

    private final List<User> aManagersList = usersReader("managers.ser");
    private final List<User> aClientsList = usersReader("clients.ser");

    // Private constructor to prevent instantiation
    private UserManager() {}

    // Get the single instance of UserManager
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Getters for the lists
    public List<User> getaManagersList() {
        return aManagersList;
    }

    public List<User> getaClientsList() {
        return aClientsList;
    }

    // Methods to add and remove users
    public void addManager(User pManager) throws IOException {
        aManagersList.add(pManager);
        usersWriter("managers.ser",aManagersList);
    }

    public void removeManager(User pManager) throws IOException {
        aManagersList.remove(pManager);
        usersWriter("managers.ser",aManagersList);
    }

    public void addClient(User pClient) throws IOException {
        aClientsList.add(pClient);
        usersWriter("clients.ser",aClientsList);
    }

    public void removeClient(User pClient) throws IOException {
        aClientsList.remove(pClient);
        usersWriter("clients.ser",aClientsList);
    }

    private List<User> usersReader(String pFilename) {
        List<User> users = new ArrayList<>(); // Start with an immutable empty list
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read(); // Read as Object list

            // Safely cast Object list to User list
            users = rawObjects.stream()
                    .filter(User.class::isInstance) // Ensure objects are of type User
                    .map(User.class::cast)          // Cast objects to User
                    .toList();                      // Collect as List<User>
        } catch (Exception e) {
            e.printStackTrace(); // Add logging to debug issues
        }

        return new ArrayList<>(users);
    }

    private void usersWriter(String filename, List<User> pUsers) throws IOException {
        WriteObjects writeObjects = new WriteObjects(filename);
        List<Object> objectList = new ArrayList<>(pUsers);

        // Write the Object list to the file
        writeObjects.write(objectList);
    }
}
