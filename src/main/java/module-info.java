module com.example.integrationprojectsdoop2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integrationprojectsdoop2 to javafx.fxml;
    exports com.example.integrationprojectsdoop2;
}