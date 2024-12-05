package com.example.integrationprojectsdoop2.Models;

/**
 * Interface for controllers that modify data of type {@link ShowComponent}.
 * This interface ensures that implementing controllers can initialize their data with a specific object.
 *
 * @param <T> the type of data to be initialized, extending {@link ShowComponent}.
 * @author Samuel Mireault
 */
public interface ModifyController<T extends ShowComponent> {

    /**
     * Initializes the controller with the provided data.
     * This method is typically called to pass data into the controller for modification or display.
     *
     * @param pData the data object to be initialized, of type {@code T}.
     */
    void initializeData(T pData);
}
