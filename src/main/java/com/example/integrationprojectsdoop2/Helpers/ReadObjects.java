package com.example.integrationprojectsdoop2.Helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ReadObjects {
    private String aFileName; // Not sure for this.
    private List<Object> aObjectsList;

    public ReadObjects(String pFileName, List<Object> pObjectsList) {
        this.aFileName = pFileName;
        this.aObjectsList = pObjectsList;
    }
    public void Read(List<Object> pObjectsList) {
        System.out.println("Reading objects...");

        try(FileInputStream fi = new FileInputStream(aFileName)) {

            ObjectInputStream os = new ObjectInputStream(fi);

            //Person person1 = (Person) os.readObject();
            //Person person2 = (Person) os.readObject();

            os.close();

            System.out.println();
            System.out.println();

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



