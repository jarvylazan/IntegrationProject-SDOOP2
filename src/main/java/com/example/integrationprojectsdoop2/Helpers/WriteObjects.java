package com.example.integrationprojectsdoop2.Helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * A utility class for serializing and writing objects to a file.
 */
public class WriteObjects {

    /** The name of the file to write serialized data to. */
    private String aFileName;

    /**
     * Constructs a new {@code WriteObjects} instance with the specified file name.
     *
     * @param pFileName the name of the file where objects will be written.
     */
    public WriteObjects(String pFileName) {
        this.aFileName = pFileName;
    }

    /**
     * Serializes and writes the list of objects to the specified file.
     *
     * @param pObjectsList the list of objects to serialize and write.
     */
    public void write(List<Object> pObjectsList) {
        System.out.println("Writing objects to file: " + aFileName);

        try (FileOutputStream fs = new FileOutputStream(aFileName);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {

            os.writeObject(pObjectsList); // Serialize and write the object list

            System.out.println("Successfully wrote objects to " + aFileName);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + aFileName);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error writing objects: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the file name where objects are written.
     *
     * @return the file name.
     */
    public String getaFileName() {
        return aFileName;
    }
}
