package com.example.integrationprojectsdoop2.Models;

/**
 * Represents an abstract User with common properties and behaviors.
 * Subclasses must define specific user types.
 */
public abstract class User {

    /** The name of the user. */
    private String aUser_Name;

    /** The email address of the user. */
    private String aUser_Email;

    /** The password of the user. */
    private String aUser_Password;

    /**
     * Default constructor that initializes all user fields to empty strings.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param pUser_Name     the name of the user.
     * @param pUser_Email    the email address of the user.
     * @param pUser_Password the password of the user.
     */
    public User(String pUser_Name, String pUser_Email, String pUser_Password) {
        aUser_Name = pUser_Name;
        aUser_Email = pUser_Email;
        aUser_Password = pUser_Password;
    }

    /**
     * Gets the user's name.
     *
     * @return the user's name.
     */
    public String getaUser_Name() {
        return aUser_Name;
    }

    /**
     * Sets the user's name.
     *
     * @param pUser_Name the new user's name.
     */
    public void setaUser_Name(String pUser_Name) {
        this.aUser_Name = pUser_Name;
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email address.
     */
    public String getaUser_Email() {
        return aUser_Email;
    }

    /**
     * Sets the user's email address.
     *
     * @param pUser_Email the new user's email address.
     */
    public void setaUser_Email(String pUser_Email) {
        this.aUser_Email = pUser_Email;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password.
     */
    public String getaUser_Password() {
        return aUser_Password;
    }

    /**
     * Sets the user's password.
     *
     * @param pUser_Password the new user's password.
     */
    public void setaUser_Password(String pUser_Password) {
        this.aUser_Password = pUser_Password;
    }
}

