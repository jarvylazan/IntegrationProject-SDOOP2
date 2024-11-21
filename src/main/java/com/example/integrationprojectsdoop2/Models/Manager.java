package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;

/**
 * Represents a Manager, which is a type of {@link User}.
 * Inherits all properties and behaviors of the {@link User} class.
 * Implements {@link Serializable} for object serialization.
 */
public class Manager extends User implements Serializable {

    /**
     * Default constructor that initializes a Manager with default user values.
     * Calls the default constructor of the {@link User} class.
     */
    public Manager() {
        super();
    }
}
