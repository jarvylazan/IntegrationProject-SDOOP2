package com.example.integrationprojectsdoop2.Helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to handle deserialization of objects from a specified file.
 * This class reads serialized objects from a file and returns them as a list.
 * Useful for retrieving previously saved objects.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * ReadObjects reader = new ReadObjects("datafile.ser");
 * try {
 *     List<Object> objects = reader.read();
 *     // Process the objects
 * } catch (IOException | ClassNotFoundException e) {
 *     e.printStackTrace();
 * }
 * }
 * </pre>
 */
public class ReadObjects {

    /** The name of the file from which objects are read. */
    private String aFileName;

    /**
     * Constructs a new instance of {@code ReadObjects} with the specified file name.
     *
     * @param pFileName the name of the file to read objects from.
     *                  Must not be {@code null} or empty.
     */
    public ReadObjects(String pFileName) {
        if (pFileName == null || pFileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        this.aFileName = pFileName;
    }

    /**
     * Reads all serialized objects from the specified file and returns them as a list.
     * The file must contain objects serialized using {@link ObjectOutputStream}.
     * <p>
     * If the file is empty or contains no objects, an empty list is returned.
     *
     * @return a list of deserialized objects from the file.
     * @throws FileNotFoundException  if the specified file does not exist.
     * @throws IOException            if an I/O error occurs while reading the file.
     * @throws ClassNotFoundException if a class of a serialized object cannot be found.
     */
    public List<Object> read() throws IOException, ClassNotFoundException {
        List<Object> objectsList = new ArrayList<>();

        System.out.println("Reading objects from file: " + aFileName);

        try (FileInputStream fi = new FileInputStream(aFileName);
             ObjectInputStream os = new ObjectInputStream(fi)) {

            while (true) {
                try {
                    Object obj = os.readObject();
                    objectsList.add(obj);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + aFileName);
            throw e;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects: " + e.getMessage());
            throw e;
        }

        System.out.println("Successfully read " + objectsList.size() + " objects.");
        return objectsList;
    }

    /**
     * Gets the name of the file being read.
     *
     * @return the file name.
     */
    public String getaFileName() {
        return aFileName;
    }
}
