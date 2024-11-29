package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.ShowComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ManagementViewController {

    private ObservableList<ShowComponent> aManagementList;
    private String aAddNModifyViewName;

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
    }

    public void setManagementView(String pTitle, ObservableList<ShowComponent> pManagementList, String pAddNModifyViewName) {
        this.aManagementList = pManagementList;
        this.aAddNModifyViewName = pAddNModifyViewName;

        managementTitleViewLabel.setText(pTitle);

        // Populate the ListView with display names
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (ShowComponent component : aManagementList) {
            displayList.add(component.getDisplayName());
        }
        managementListView.setItems(displayList);
    }

    public void onAddClickButton(ActionEvent actionEvent) {
        navigateToView(aAddNModifyViewName);
    }

    public void onDeleteClickButton(ActionEvent actionEvent) {
        int selectedIndex = managementListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            aManagementList.remove(selectedIndex);
            managementListView.getItems().remove(selectedIndex);
        } else {
            new AlertHelper("No item selected for deletion.").executeErrorAlert();
        }
    }

    public void onBackButton(ActionEvent actionEvent) {
        navigateToView("ManagementDashboard");
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

    private void navigateToView(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + viewName + ".fxml"));
            Parent view = loader.load();
            managementTitleViewLabel.getScene().setRoot(view);
        } catch (IOException e) {
            new AlertHelper(e.getMessage()).executeErrorAlert();
        }
    }

    private void navigateToModifyView(ShowComponent selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + aAddNModifyViewName + ".fxml"));
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
            displayManagerLabel.setText(selectedItem.getDisplayName());
        } else {
            displayManagerLabel.setText("Select an item to view details.");
        }
    }
}
