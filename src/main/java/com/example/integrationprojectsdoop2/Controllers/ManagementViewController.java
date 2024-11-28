package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Helpers.AlertHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;


public class ManagementViewController {


    //private String aTitle;

    private ObservableList<Object> aManagementList;

    private String aAddNModifyViewName;

    @FXML
    private Label managementTitleViewLabel;

    @FXML
    private Label DisplayManagerLabel;

    @FXML
    private ListView<Object> ManagementListView;

    public ManagementViewController( ObservableList<Object> pManagementList, String pAddNModifyViewName) {
        aManagementList = pManagementList;
        aAddNModifyViewName = pAddNModifyViewName;
    }

    public void OnAddClickButton(ActionEvent actionEvent) {
        // TODO: Call the view addmodify of the proper chosen view.
        try {
            FXMLLoader addModifyLoader = new FXMLLoader(getClass().getResource("/com/example/integrationprojectsdoop2/" + aAddNModifyViewName + ".fxml"));
            Parent addModifyView = addModifyLoader.load();
            DisplayManagerLabel.getScene().setRoot(addModifyView);
        }
        catch (IOException e) {
            AlertHelper SignupError = new AlertHelper(e.getMessage());
            SignupError.executeErrorAlert();
        }
    }

    public void OnDeleteClickButton(ActionEvent actionEvent) {
        // TODO:Remove selected object in the list view doesnt matter which view.
    }

    public void OnBackButton(ActionEvent actionEvent) {
        // TODO:CLose the current view or reuse it to bring back the dashboard
    }

    public void OnModifiyButton(ActionEvent actionEvent) {
        // TODO: call the same view of Add for the modify
    }

    public void setManagementView(String pTitle, List<Object> pManagementList) {
        try {
            managementTitleViewLabel.setText(pTitle);

            this.aManagementList = pManagementList;
        }
        catch (Exception e) {
            e.printStackTrace();
            AlertHelper managementError = new AlertHelper(e.getMessage());
            managementError.executeErrorAlert();
        }
    }
}
