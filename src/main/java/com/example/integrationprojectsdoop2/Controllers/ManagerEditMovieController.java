package com.example.integrationprojectsdoop2.Controllers;

import com.example.integrationprojectsdoop2.Models.ModifyController;
import com.example.integrationprojectsdoop2.Models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
public class ManagerEditMovieController implements ModifyController<Movie> {
    @FXML
    private TextField titleField;
    @FXML
    private TextField directorField;

    @Override
    public void initializeData(Movie movie) {
        titleField.setText(movie.getAMovie_Title());
    }
}
