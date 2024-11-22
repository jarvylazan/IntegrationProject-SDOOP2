package com.example.integrationprojectsdoop2.Helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to read serialized objects from a file.
 */
public class ReadObjects {

    /** The name of the file to read objects from. */
    private String aFileName;

    /**
     * Constructs a new instance of {@code ReadObjects} with the specified file name.
     *
     * @param pFileName the name of the file to read objects from.
     */
    public ReadObjects(String pFileName) {
        this.aFileName = pFileName;
    }

    /**
     * Reads all objects from the specified file and returns them as a list.
     *
     * @return a list of deserialized objects from the file, or an empty list if the file is empty.
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
