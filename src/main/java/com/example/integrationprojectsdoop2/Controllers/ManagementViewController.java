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
import java.util.List;
import java.util.Map;

/**
 * Controller for managing the view and operations related to the management of show components.
 * This class provides functionalities to add, modify, delete, and display details of components.
 *
 * @author Samuel
 * @since 1.0
 */
public class ManagementViewController {

    /**
     * Observable list containing all managed {@link ShowComponent} instances.
     */
    private ObservableList<ShowComponent> aManagementList;

    /**
     * The name of the view used for adding and modifying components.
     */
    private String aAddNModifyViewName;

    /**
     * The filename used for saving and loading the management list.
     */
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
        try {
            managementListView.getSelectionModel().selectedItemProperty().addListener(
                    (_, _, _) -> onListViewItemSelect()
            );
            displayManagerLabel.setText("Select an item from the list to see more details.");
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
        }
    }

    /**
     * Sets up the management view with a title, filename for loading data, and a view name for adding/modifying items.
     *
     * @param pTitle              the title of the management view.
     * @param pFilename           the filename used for loading and saving data.
     * @param pAddNModifyViewName the name of the view used for adding and modifying components.
     */
    public void setManagementView(String pTitle, String pFilename, String pAddNModifyViewName) {
        try {
            this.aFileName = pFilename;
            this.aAddNModifyViewName = pAddNModifyViewName;
            this.aManagementList = loadManagementListFrom(pFilename);
            managementTitleViewLabel.setText(pTitle);

            ObservableList<String> displayList = FXCollections.observableArrayList();
            for (ShowComponent component : aManagementList) {
                displayList.add(component.getDisplayName());
            }
            managementListView.setItems(displayList);
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
        }
    }

    /**
     * Handles the action of adding a new item by navigating to the added view.
     */
    public void onAddClickButton() {
            navigateToAdd(aAddNModifyViewName);
    }

    /**
     * Handles the action of deleting the selected item.
     * Shows a confirmation dialog before proceeding with the deletion.
     */
    public void onDeleteClickButton() {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                if (showConfirmationDialog(
                )) {
                    deleteItem(selectedIndex);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Deletion canceled by the user.");
                }
            } else {
                new AlertHelper("No item selected for deletion.").executeErrorAlert();
            }
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
        }
    }

    /**
     * Handles the action of going back to the previous stage by closing the current window.
     */
    public void onBackButton() {
        Stage stage = (Stage) this.managementTitleViewLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of modifying the selected item by navigating to the modified view.
     */
    public void onModifyButton() {
        try {
            int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ShowComponent selectedItem = aManagementList.get(selectedIndex);
                navigateToModifyView(selectedItem);
            } else {
                new AlertHelper("No item selected for modification.").executeErrorAlert();
            }
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
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
     * Navigates to the modified view with the selected item.
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
        try {
            int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ShowComponent selectedItem = aManagementList.get(selectedIndex);
                displayManagerLabel.setText(selectedItem.toString());
            } else {
                displayManagerLabel.setText("Select an item to view details.");
            }
        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
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

            components = rawObjects.stream()
                    .filter(ShowComponent.class::isInstance)
                    .map(ShowComponent.class::cast)
                    .toList();
        } catch (Exception e) {
            AlertHelper errorLoad = new AlertHelper(e.getMessage());
            errorLoad.executeErrorAlert();
        }

        return FXCollections.observableArrayList(components);
    }

    /**
     * Saves the management list to the specified file.
     *
     * @param pFilename       the filename to which the data should be saved.
     * @param pManagementList the management list to save.
     */
    private void saveManagementListToFile(String pFilename, ObservableList<ShowComponent> pManagementList) {

        try {
            List<Object> serializableList = new ArrayList<>(pManagementList);
            WriteObjects writeObjects = new WriteObjects(pFilename);
            writeObjects.write(serializableList);
        } catch (Exception e) {
            AlertHelper errorSave = new AlertHelper(e.getMessage());
            errorSave.executeErrorAlert();
        }
    }

    /**
     * Displays a confirmation dialog with the specified title, header, and content.
     *
     * @return {@code true} if the user confirms, {@code false} otherwise.
     */
    private boolean showConfirmationDialog() {
        ButtonType result = null;
        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete this item?");
            confirmationAlert.setContentText("This action cannot be undone.");

            result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        } catch (Exception e) {
            AlertHelper error = new AlertHelper(e.getMessage());
            error.executeErrorAlert();
        }
        return result == ButtonType.OK;
    }

    /**
     * Deletes the selected item from the management list and saves the updated list to the file.
     *
     * @param pSelectedIndex the index of the item to delete.
     */
    private void deleteItem(int pSelectedIndex){
        if (pSelectedIndex < 0 || pSelectedIndex >= aManagementList.size()) {
            throw new IllegalArgumentException("Invalid selection. No item to delete.");
        }

        Object selectedItem = aManagementList.get(pSelectedIndex);

        // Check if the selected item is a ShowComponent
        if (selectedItem instanceof ShowComponent) {
            aManagementList.remove(selectedItem);
            managementListView.getItems().remove(pSelectedIndex);

            saveManagementListToFile(aFileName, aManagementList);
            AlertHelper success = new AlertHelper("Item deleted successfully.");
            success.executeSuccessAlert();
        } else {
            throw new IllegalArgumentException("Selected item is not a ShowComponent. Cannot delete.");
        }
    }
}
