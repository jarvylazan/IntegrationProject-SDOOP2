package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Helpers.ReadObjects;
import com.example.integrationprojectsdoop2.Helpers.WriteObjects;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.ShowComponent;
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

public class ManagementViewController {

    private ObservableList<ShowComponent> aManagementList;
    private String aAddNModifyViewName;
    private String aFileName;

    @FXML
    private Label managementTitleViewLabel;

    @FXML
    private Label displayManagerLabel;

    @FXML
    private ListView<String> managementListView;

    // Called automatically after FXML loading
    @FXML
    public void initialize() {
        managementListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onListViewItemSelect()
        );
        displayManagerLabel.setText("Select an Items from the List to see more details.");
    }

    public void setManagementView(String pTitle, String pFilename, String pAddNModifyViewName) {
        this.aFileName = pFilename;
        this.aAddNModifyViewName = pAddNModifyViewName;
        this.aManagementList = loadManagementListFrom(pFilename);
        managementTitleViewLabel.setText(pTitle);

        // Populate the ListView with display names
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (ShowComponent component : aManagementList) {
            displayList.add(component.getDisplayName());
        }
        managementListView.setItems(displayList);
    }

    public void onAddClickButton(ActionEvent actionEvent) {
        navigateToAdd(aAddNModifyViewName);
    }

    public void onDeleteClickButton(ActionEvent actionEvent) throws IOException {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Show confirmation dialog
            if (showConfirmationDialog("Confirm Deletion",
                    "Are you sure you want to delete this item?",
                    "This action cannot be undone.")) {
                // Proceed with deletion if confirmed
                deleteItem(selectedIndex);
            } else {
                System.out.println("Deletion canceled by the user.");
            }
        } else {
            new AlertHelper("No item selected for deletion.").executeErrorAlert();
        }
    }






    public void onBackButton(ActionEvent actionEvent) {
        Stage stage = (Stage) this.managementTitleViewLabel.getScene().getWindow();
        stage.close();
    }

    public void onModifyButton(ActionEvent actionEvent) {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ShowComponent selectedItem = aManagementList.get(selectedIndex);
            navigateToModifyView(selectedItem);
        } else {
            new AlertHelper("No item selected for modification.").executeErrorAlert();
        }
    }

    private void navigateToAdd(String viewName) {
        try {
            FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + viewName));
            Parent addView = addLoader.load();
            managementTitleViewLabel.getScene().setRoot(addView);
        } catch (IOException e) {
            new AlertHelper(e.getMessage()).executeErrorAlert();
        }
    }

    private void navigateToModifyView(ShowComponent selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + aAddNModifyViewName));
            Parent modifyView = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ModifyController) {
                ((ModifyController<ShowComponent>) controller).initializeData(selectedItem);
            }

            managementTitleViewLabel.getScene().setRoot(modifyView);
        } catch (IOException e) {
            new AlertHelper(e.getMessage()).executeErrorAlert();
        }
    }

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

    private ObservableList<ShowComponent> loadManagementListFrom(String pAddNModifyViewName) {
        List<ShowComponent> components = new ArrayList<>();
        try {
            ReadObjects readObjects = new ReadObjects(pAddNModifyViewName);
            List<Object> rawObjects = readObjects.read();

            // Safely cast raw objects to User instances
            components = rawObjects.stream()
                    .filter(ShowComponent.class::isInstance)
                    .map(ShowComponent.class::cast)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace(); // Log exceptions for debugging
        }

        return FXCollections.observableArrayList(components);
    }

    private void saveManagementListToFile(String pFilename, ObservableList<ShowComponent> pManagementList) throws IOException {
        WriteObjects writeObjects = new WriteObjects(pFilename);
        List<Object> components = Collections.singletonList(pManagementList);

        writeObjects.write(components);
    }

    private boolean showConfirmationDialog(String title, String header, String content) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText(header);
        confirmationAlert.setContentText(content);

        // Wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    private void deleteItem(int selectedIndex) throws IOException {
        ShowComponent selectedItem = aManagementList.get(selectedIndex);
        aManagementList.remove(selectedIndex);
        managementListView.getItems().remove(selectedIndex);

        // Save the updated list to the file
        saveManagementListToFile(aFileName, aManagementList);

        System.out.println("Item deleted successfully.");
    }

}
