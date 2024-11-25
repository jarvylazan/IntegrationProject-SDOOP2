package com.example.integrationprojectsdoop2.Helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * A utility class for serializing and writing objects to a specified file.
 * This class enables saving objects into a file for later retrieval, making use of serialization.
 * Objects are written from a provided list and can be deserialized using {@link ReadObjects}.
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * WriteObjects writer = new WriteObjects("datafile.ser");
 * List<Object> objectsToWrite = ...; // Obtain a list of objects
 * writer.write(objectsToWrite);
 * }
 * </pre>
 *
 * @author Samuel
 */
public class WriteObjects {

    /**
     * The name of the file to which objects will be written.
     */
    private final String aFileName;

    /**
     * Constructs a new {@code WriteObjects} instance with the specified file name.
     * The file will be used to store the serialized objects.
     *
     * @param pFileName the name of the file where objects will be written.
     *                  Must not be {@code null} or empty.
     * @throws IllegalArgumentException if the provided file name is {@code null} or empty.
     * @author Samuel
     */
    public WriteObjects(String pFileName) {
        if (pFileName == null || pFileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        this.aFileName = pFileName;
    }

    /**
     * Serializes the provided list of objects and writes them to the specified file.
     * Each object in the list is serialized and stored individually.
     * The written objects can later be retrieved using the {@link ReadObjects} class.
     *
     * @param pObjectsList the list of objects to serialize and write to the file.
     *                     Must not be {@code null}.
     * @throws IllegalArgumentException if {@code pObjectsList} is {@code null}.
     * @throws IOException              if an I/O error occurs while writing the objects to the file.
     * @author Samuel
     */
    public void write(List<Object> pObjectsList) throws IOException {
        if (pObjectsList == null) {
            throw new IllegalArgumentException("Object list cannot be null.");
        }

        System.out.println("Writing objects to file: " + aFileName);

        try (FileOutputStream fs = new FileOutputStream(aFileName);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {

            for (Object o : pObjectsList) {
                os.writeObject(o); // Serialize and write each object
            }

            System.out.println("Successfully wrote objects to " + aFileName);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + aFileName);
            throw e; // Re-throw to propagate the error to the caller
        } catch (IOException e) {
            System.err.println("Error writing objects: " + e.getMessage());
            throw e; // Re-throw to propagate the error to the caller
        }
    }
}
