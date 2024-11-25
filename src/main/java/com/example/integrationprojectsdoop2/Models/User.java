package com.example.integrationprojectsdoop2.Models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an abstract User with common properties and behaviors.
 * This class serves as a base class for different types of users (e.g., Manager, Client)
 * and provides shared attributes such as name, email, and password.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * User user = new Manager("John Doe", "john.doe@example.com", "password123");
 * }
 * </pre>
 * Implements {@link Serializable} for object serialization.
 * @author Samuel
 */
public abstract class User implements Serializable {

    /** The serialID for the files. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The name of the user (private for encapsulation). */
    private String aUser_Name;

    /** The email address of the user (private for encapsulation). */
    private String aUser_Email;

    /** The password of the user (private for encapsulation). */
    private String aUser_Password;

    /**
     * Default constructor that initializes all user fields to empty strings.
     * Typically used when creating a subclass instance where the user details are set later.
     * @author Samuel
     */
    public User() {
        this.aUser_Name = "";
        this.aUser_Email = "";
        this.aUser_Password = "";
    }

    /**
     * Constructs a new User with the specified details.
     * This constructor allows for setting the user's name, email, and password upon creation.
     *
     * @param pUser_Name     the name of the user. Must not be {@code null} or empty.
     * @param pUser_Email    the email address of the user. Must not be {@code null} or empty.
     * @param pUser_Password the password of the user. Must not be {@code null} or empty.
     * @throws IllegalArgumentException if any of the parameters are {@code null} or empty.
     * @author Samuel
     */
    public User(String pUser_Name, String pUser_Email, String pUser_Password) {
        setaUser_Name(pUser_Name);
        setaUser_Email(pUser_Email);
        setaUser_Password(pUser_Password);
    }

    /**
     * Gets the user's name.
     *
     * @return the user's name.
     * @author Samuel
     */
    public String getaUser_Name() {
        return aUser_Name;
    }

    /**
     * Sets the user's name.
     *
     * @param pUser_Name the new name to set for the user. Cannot be {@code null} or empty.
     * @throws IllegalArgumentException if the provided name is {@code null} or empty.
     * @author Samuel
     */
    public void setaUser_Name(String pUser_Name) {
        if (pUser_Name == null || pUser_Name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        this.aUser_Name = pUser_Name;
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email address.
     * @author Samuel
     */
    public String getaUser_Email() {
        return aUser_Email;
    }

    /**
     * Sets the user's email address.
     *
     * @param pUser_Email the new email to set for the user. Cannot be {@code null} or empty.
     * @throws IllegalArgumentException if the provided email is {@code null} or empty.
     * @author Samuel
     */
    public void setaUser_Email(String pUser_Email) {
        if (pUser_Email == null || pUser_Email.isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }
        this.aUser_Email = pUser_Email;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password.
     * @author Samuel
     */
    public String getaUser_Password() {
        return aUser_Password;
    }

    /**
     * Sets the user's password.
     *
     * @param pUser_Password the new password to set for the user. Cannot be {@code null} or empty.
     * @throws IllegalArgumentException if the provided password is {@code null} or empty.
     * @author Samuel
     */
    public void setaUser_Password(String pUser_Password) {
        if (pUser_Password == null || pUser_Password.isEmpty()) {
            throw new IllegalArgumentException("User password cannot be null or empty.");
        }
        this.aUser_Password = pUser_Password;
    }
}
