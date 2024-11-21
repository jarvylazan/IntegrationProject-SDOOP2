package com.example.integrationprojectsdoop2.Helpers;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class WriteObjects {

    private String aFileName; // Not sure for this.
    private List<Object> aObjectsList;

    public WriteObjects(String pFileName, List<Object> pObjectsList) {
        this.aFileName = pFileName;
        this.aObjectsList = pObjectsList;
    }

    public void Write(List<Object> pObjectsList) {
        System.out.println("Writing objects...");

        //Person mike = new Person(543, "Mike");
        //Person sue = new Person(123, "Sue");

        System.out.println(aFileName);
        System.out.println(aObjectsList);

        try(FileOutputStream fs = new FileOutputStream(aFileName)) {

            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(pObjectsList);

            os.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getaFileName(){
        return aFileName;
    }

    public List<Object> getaObjectsList() {
        return aObjectsList;
    }
}