package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import com.example.integrationprojectsdoop2.Models.Movie;
import com.example.integrationprojectsdoop2.Models.Show;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class ManagementViewController<T> {

    private ObservableList<T> aManagementList;

    private String aAddNModifyViewName;

    ObservableList<Movie> aMoviesList = Movie.getInstance().getMovies();
    ObservableList<Show> aShowsList = Show.getInstance().getShows();


    @FXML
    private Label managementTitleViewLabel;

    @FXML
    private Label displayManagerLabel;

    @FXML
    private ListView<T> managementListView;

    public ManagementViewController(ObservableList<T> pManagementList, String pAddNModifyViewName) {
        aManagementList = pManagementList;
        aAddNModifyViewName = pAddNModifyViewName;
    }

    public void OnAddClickButton(ActionEvent actionEvent) {
        // Navigate to the add/modify view
        try {
            FXMLLoader addModifyLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + aAddNModifyViewName + ".fxml"));
            Parent addModifyView = addModifyLoader.load();
            displayManagerLabel.getScene().setRoot(addModifyView);
        } catch (IOException e) {
            AlertHelper signupError = new AlertHelper(e.getMessage());
            signupError.executeErrorAlert();
        }
    }

    public void OnDeleteClickButton(ActionEvent actionEvent) {
        // Remove the selected object from the ListView
        T selectedItem = managementListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            aManagementList.remove(selectedItem);
        } else {
            AlertHelper deleteError = new AlertHelper("No item selected for deletion.");
            deleteError.executeErrorAlert();
        }
    }

    public void OnBackButton(ActionEvent actionEvent) {
        // Navigate back to the dashboard (or previous view)
        try {
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/ManagementDashboard.fxml"));
            Parent dashboardView = dashboardLoader.load();
            displayManagerLabel.getScene().setRoot(dashboardView);
        } catch (IOException e) {
            AlertHelper backError = new AlertHelper(e.getMessage());
            backError.executeErrorAlert();
        }
    }

    public void OnModifyButton(ActionEvent actionEvent) {
        // Navigate to the add/modify view, pre-filled with the selected item
        T selectedItem = managementListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader modifyLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + aAddNModifyViewName + ".fxml"));
                Parent modifyView = modifyLoader.load();

                // Pass the selected item to the modify controller (if applicable)
                Object controller = modifyLoader.getController();
                if (controller instanceof ModifyController) {
                    ((ModifyController<T>) controller).initializeData(selectedItem);
                }

                displayManagerLabel.getScene().setRoot(modifyView);
            } catch (IOException e) {
                AlertHelper modifyError = new AlertHelper(e.getMessage());
                modifyError.executeErrorAlert();
            }
        } else {
            AlertHelper modifyError = new AlertHelper("No item selected for modification.");
            modifyError.executeErrorAlert();
        }
    }

    public void setManagementView(String pTitle, ObservableList<T> pManagementList) {
        managementTitleViewLabel.setText(pTitle);
        this.aManagementList = pManagementList;
        managementListView.setItems(aManagementList);
    }

    @FXML
    private void onListViewItemSelect() {
        T selectedItem = managementListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            displayManagerLabel.setText(selectedItem.toString()); // Customize this for specific object details
        } else {
            displayManagerLabel.setText("Select an item to view details.");
        }
    }


}
