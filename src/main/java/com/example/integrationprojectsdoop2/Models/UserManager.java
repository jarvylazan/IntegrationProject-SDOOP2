package com.example.integrationprojectsdoop2.Models;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages {@link User} instances, including both Managers and Clients.
 * Provides singleton access to user management operations, such as adding, removing,
 * and retrieving users, while ensuring persistence via serialization.
 *
 * <p>Handles user data storage and retrieval using helper classes {@link ReadObjects}
 * and {@link WriteObjects} for file I/O operations.</p>
 *
 * @author Samuel
 */
public class UserManager {

    /** The single instance of UserManager. */
    private static UserManager instance;

    /** List of managers, initialized from the serialized file. */
    private final List<User> aManagersList;

    /** List of clients, initialized from the serialized file. */
    private final List<User> aClientsList;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes user lists by reading from serialized files.
     *
     * @author Samuel
     */
    private UserManager() {
        aManagersList = usersReader("managers.ser");
        aClientsList = usersReader("clients.ser");
    }

    /**
     * Returns the single instance of {@code UserManager}.
     * Creates a new instance if it doesn't exist.
     *
     * @return the singleton instance of {@code UserManager}.
     *
     * @author Samuel
     */
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Retrieves the list of managers.
     *
     * @return an unmodifiable view of the list of managers.
     *
     * @author Samuel
     */
    public List<User> getaManagersList() {
        return Collections.unmodifiableList(aManagersList);
    }

    /**
     * Retrieves the list of clients.
     *
     * @return an unmodifiable view of the list of clients.
     *
     * @author Samuel
     */
    public List<User> getaClientsList() {
        return Collections.unmodifiableList(aClientsList);
    }

    /**
     * Adds a manager to the list and updates the serialized file.
     *
     * @param pManager the manager to add.
     * @throws IOException if an error occurs during file writing.
     *
     * @author Samuel
     */
    public void addManager(User pManager) throws IOException {
        aManagersList.add(pManager);
        usersWriter("managers.ser", aManagersList);
    }

    /**
     * Removes a manager from the list and updates the serialized file.
     *
     * @param pManager the manager to remove.
     * @throws IOException if an error occurs during file writing.
     *
     * @author Samuel
     */
    public void removeManager(User pManager) throws IOException {
        aManagersList.remove(pManager);
        usersWriter("managers.ser", aManagersList);
    }

    /**
     * Adds a client to the list and updates the serialized file.
     *
     * @param pClient the client to add.
     * @throws IOException if an error occurs during file writing.
     *
     * @author Samuel
     */
    public void addClient(User pClient) throws IOException {
        aClientsList.add(pClient);
        usersWriter("clients.ser", aClientsList);
    }

    /**
     * Removes a client from the list and updates the serialized file.
     *
     * @param pClient the client to remove.
     * @throws IOException if an error occurs during file writing.
     *
     * @author Samuel
     */
    public void removeClient(User pClient) throws IOException {
        aClientsList.remove(pClient);
        usersWriter("clients.ser", aClientsList);
    }

    /**
     * Reads a list of users from a serialized file.
     *
     * @param pFilename the name of the file to read from.
     * @return a list of users read from the file.
     *
     * @author Samuel
     */
    private List<User> usersReader(String pFilename) {
        List<User> users = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read();

            // Safely cast raw objects to User instances
            users = rawObjects.stream()
                    .filter(User.class::isInstance)
                    .map(User.class::cast)
                    .toList();
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
        }

        return new ArrayList<>(users);
    }

    /**
     * Writes a list of users to a serialized file.
     *
     * @param pFilename the name of the file to write to.
     * @param pUsers   the list of users to serialize and save.
     * @throws IOException if an error occurs during file writing.
     *
     * @author Samuel
     */
    private void usersWriter(String pFilename, List<User> pUsers) throws IOException {
        WriteObjects writeObjects = new WriteObjects(pFilename);
        List<Object> objectList = new ArrayList<>(pUsers);

        // Write the Object list to the file
        writeObjects.write(objectList);
    }
}
