package com.example.integrationprojectsdoop2.Models;

/**
 * Interface representing a component that can provide a display name.
 * This is used, for example, in UI components such as a ListView.
 *
 * @author Samuel
 */
public interface ShowComponent {

    /**
     * Retrieves the display name of the component.
     * This is typically used to provide a user-friendly name for UI elements like ListView items.
     *
     * @return the display name of the component as a {@link String}.
     * @author Samuel Mireault
     */
    String getDisplayName();
}
