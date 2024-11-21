package com.example.integrationprojectsdoop2.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User with various properties such as ID, name, email, password, and type.
 * This class also maintains a list of users.
 * Implements {@link Serializable} for object serialization.
 */
public class User implements Serializable {

    /** The unique identifier for the user. */
    private static String aUser_ID;

    /** The name of the user. */
    private static String aUser_Name;

    /** The email address of the user. */
    private static String aUser_Email;

    /** The password of the user. */
    private static String aUser_Password;

    /** The type of the user (e.g., admin, guest, etc.). */
    private static String aUser_Type;

    /** A list of users associated with this instance. */
    private List<User> aUser_List = new ArrayList<>();

    /**
     * Default constructor that initializes all user fields to empty strings.
     */
    public User() {
        aUser_ID = "";
        aUser_Name = "";
        aUser_Email = "";
        aUser_Password = "";
        aUser_Type = "";
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param pUser_ID       the unique identifier for the user.
     * @param pUser_Name     the name of the user.
     * @param pUser_Email    the email address of the user.
     * @param pUser_Password the password of the user.
     * @param pUser_Type     the type of the user.
     */
    public User(String pUser_ID, String pUser_Name, String pUser_Email, String pUser_Password, String pUser_Type) {
        aUser_ID = pUser_ID;
        aUser_Name = pUser_Name;
        aUser_Email = pUser_Email;
        aUser_Password = pUser_Password;
        aUser_Type = pUser_Type;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID.
     */
    public String getaUser_ID() {
        return aUser_ID;
    }

    /**
     * Sets the user ID.
     *
     * @param pUser_ID the new user ID.
     */
    public void setaUser_ID(String pUser_ID) {
        this.aUser_ID = pUser_ID;
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

    /**
     * Gets the user's type.
     *
     * @return the user's type.
     */
    public String getaUser_Type() {
        return aUser_Type;
    }

    /**
     * Sets the user's type.
     *
     * @param pUser_Type the new user's type.
     */
    public void setaUser_Type(String pUser_Type) {
        this.aUser_Type = pUser_Type;
    }

    /**
     * Gets the list of users associated with this instance.
     *
     * @return the list of users.
     */
    public List<User> getaUser_List() {
        return aUser_List;
    }

    /**
     * Sets the list of users associated with this instance.
     *
     * @param pUser_List the new list of users.
     */
    public void setaUser_List(List<User> pUser_List) {
        this.aUser_List = pUser_List;
    }

    /**
     * Returns a string representation of the user.
     * The format is: "ID,Name,Email,Password,Type".
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return getaUser_ID() + "," + getaUser_Name() + "," + getaUser_Email() + "," + getaUser_Password() + "," + getaUser_Type();
    }
}

