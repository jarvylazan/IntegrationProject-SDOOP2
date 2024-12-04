package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.Movie;
import com.example.integrationprojectsdoop2.Models.Show;
import com.example.integrationprojectsdoop2.Models.ShowComponent;
import com.example.integrationprojectsdoop2.Models.Showtime;
import com.example.integrationprojectsdoop2.Models.Screenroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for managing the view and operations related to the management of show components.
 * This class provides functionalities to add, modify, delete, and display details of components.
 *
 * @author Samuel
 * @since 1.0
 */
public class ManagementViewController {

    /** Observable list containing all managed {@link ShowComponent} instances. */
    private ObservableList<ShowComponent> aManagementList;

    /** The name of the view used for adding and modifying components. */
    private String aAddNModifyViewName;

    /** The filename used for saving and loading the management list. */
    private String aFileName;

    @FXML
    private Label managementTitleViewLabel;

    @FXML
    private Label displayManagerLabel;

    @FXML
    private ListView<String> managementListView;

    private static final Map<Class<?>, String> VIEW_MAP = Map.of(
            Movie.class, "/com/example/integrationprojectsdoop2/manager-edit-movie-view.fxml",
            Show.class, "/com/example/integrationprojectsdoop2/manager-show-add-modify-view.fxml",
            Showtime.class, "/com/example/integrationprojectsdoop2/manager-showtime-add-modify-view.fxml",
            Screenroom.class, "/com/example/integrationprojectsdoop2/manager-screen-room-add-modify-view.fxml"
    );


    /**
     * Initializes the controller after the FXML file is loaded.
     * Sets up a listener for item selection in the ListView and initializes the display label.
     */
    @FXML
    public void initialize() {
        managementListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onListViewItemSelect()
        );
        displayManagerLabel.setText("Select an item from the list to see more details.");
    }

    /**
     * Sets up the management view with a title, filename for loading data, and a view name for adding/modifying items.
     *
     * @param pTitle            the title of the management view.
     * @param pFilename         the filename used for loading and saving data.
     * @param pAddNModifyViewName the name of the view used for adding and modifying components.
     */
    public void setManagementView(String pTitle, String pFilename, String pAddNModifyViewName) {
        this.aFileName = pFilename;
        this.aAddNModifyViewName = pAddNModifyViewName;
        this.aManagementList = loadManagementListFrom(pFilename);
        managementTitleViewLabel.setText(pTitle);

        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (ShowComponent component : aManagementList) {
            displayList.add(component.getDisplayName());
        }
        managementListView.setItems(displayList);
    }

    /**
     * Handles the action of adding a new item by navigating to the add view.
     *
     * @param actionEvent the action event triggered by clicking the add button.
     */
    public void onAddClickButton(ActionEvent actionEvent) {
        navigateToAdd(aAddNModifyViewName);
    }

    /**
     * Handles the action of deleting the selected item.
     * Shows a confirmation dialog before proceeding with the deletion.
     *
     * @param actionEvent the action event triggered by clicking the delete button.
     * @throws IOException if an error occurs while saving the updated list to the file.
     */
    public void onDeleteClickButton(ActionEvent actionEvent) throws IOException {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (showConfirmationDialog("Confirm Deletion",
                    "Are you sure you want to delete this item?",
                    "This action cannot be undone.")) {
                deleteItem(selectedIndex);
            } else {
                System.out.println("Deletion canceled by the user.");
            }
        } else {
            new AlertHelper("No item selected for deletion.").executeErrorAlert();
        }
    }

    /**
     * Handles the action of going back to the previous stage by closing the current window.
     *
     * @param actionEvent the action event triggered by clicking the back button.
     */
    public void onBackButton(ActionEvent actionEvent) {
        Stage stage = (Stage) this.managementTitleViewLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of modifying the selected item by navigating to the modify view.
     *
     * @param actionEvent the action event triggered by clicking the modify button.
     */
    public void onModifyButton(ActionEvent actionEvent) {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ShowComponent selectedItem = aManagementList.get(selectedIndex);
            navigateToModifyView(selectedItem);
        } else {
            new AlertHelper("No item selected for modification.").executeErrorAlert();
        }
    }

    /**
     * Navigates to the view for adding a new component.
     *
     * @param viewName the name of the view to navigate to.
     */
    private void navigateToAdd(String viewName) {
        try {
            FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + viewName));
            Parent addView = addLoader.load();
            managementTitleViewLabel.getScene().setRoot(addView);
        } catch (IOException e) {
            new AlertHelper(e.getMessage()).executeErrorAlert();
        }
    }

    /**
     * Navigates to the modify view with the selected item.
     *
     * @param selectedItem the selected item to be modified.
     */
    private void navigateToModifyView(ShowComponent selectedItem) {
        try {
            String viewPath = VIEW_MAP.get(selectedItem.getClass());
            if (viewPath == null) {
                throw new IllegalStateException("No view mapping found for: " + selectedItem.getClass());
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            // Use reflection to call the appropriate initializeData method dynamically
            controller.getClass()
                    .getMethod("initializeData", selectedItem.getClass())
                    .invoke(controller, selectedItem);

            managementTitleViewLabel.getScene().setRoot(root);
        } catch (IOException e) {
            new AlertHelper("Error loading view: " + e.getMessage()).executeErrorAlert();
        } catch (ReflectiveOperationException e) {
            new AlertHelper("Error initializing controller: " + e.getMessage()).executeErrorAlert();
        }
    }


    /**
     * Updates the detail display when a new item is selected from the ListView.
     */
    @FXML
    private void onListViewItemSelect() {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ShowComponent selectedItem = aManagementList.get(selectedIndex);
            displayManagerLabel.setText(selectedItem.toString());
        } else {
            displayManagerLabel.setText("Select an item to view details.");
        }
    }

    /**
     * Loads the management list from the specified file.
     *
     * @param pFilename the filename from which to load the data.
     * @return the loaded management list as an observable list.
     */
    private ObservableList<ShowComponent> loadManagementListFrom(String pFilename) {
        List<ShowComponent> components = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pFilename);
            List<Object> rawObjects = readObjects.read();

            // Convert to mutable list and cast to ShowComponent
            components = rawObjects.stream()
                    .filter(ShowComponent.class::isInstance)
                    .map(ShowComponent.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new)); // Ensure it's a mutable list

            // Sort based on the display name for all components
            components.sort((o1, o2) -> {
                String displayName1 = o1.getDisplayName();
                String displayName2 = o2.getDisplayName();

                // Handle nulls gracefully
                if (displayName1 == null) return (displayName2 == null) ? 0 : -1;
                if (displayName2 == null) return 1;

                return displayName1.compareToIgnoreCase(displayName2); // Case-insensitive sorting
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(components);
    }


    /**
     * Saves the management list to the specified file.
     *
     * @param pFilename        the filename to which the data should be saved.
     * @param pManagementList the management list to save.
     * @throws IOException if an error occurs while writing to the file.
     */
    private void saveManagementListToFile(String pFilename, ObservableList<ShowComponent> pManagementList) throws IOException {
        List<Object> serializableList = new ArrayList<>(pManagementList);
        WriteObjects writeObjects = new WriteObjects(pFilename);
        writeObjects.write(serializableList);
    }

    /**
     * Displays a confirmation dialog with the specified title, header, and content.
     *
     * @param title   the title of the dialog.
     * @param header  the header text of the dialog.
     * @param content the content text of the dialog.
     * @return {@code true} if the user confirms, {@code false} otherwise.
     */
    private boolean showConfirmationDialog(String title, String header, String content) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText(header);
        confirmationAlert.setContentText(content);

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    /**
     * Deletes the selected item from the management list and saves the updated list to the file.
     *
     * @param selectedIndex the index of the item to delete.
     * @throws IOException if an error occurs while saving the updated list to the file.
     */
    private void deleteItem(int selectedIndex) throws IOException {
        if (selectedIndex < 0 || selectedIndex >= aManagementList.size()) {
            System.out.println("Invalid selection. No item to delete.");
            AlertHelper nothingChosen = new AlertHelper( "Invalid selection. No item to delete.");
            nothingChosen.executeErrorAlert();

            return;
        }

        Object selectedItem = aManagementList.get(selectedIndex);

        // Check if the selected item is a ShowComponent
        if (selectedItem instanceof ShowComponent) {
            aManagementList.remove(selectedItem);
            managementListView.getItems().remove(selectedIndex);

            saveManagementListToFile(aFileName, aManagementList);
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Selected item is not a ShowComponent. Cannot delete.");
        }
    }
}
