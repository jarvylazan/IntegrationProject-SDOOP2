module com.example.integrationprojectsdoop2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.integrationprojectsdoop2 to javafx.fxml;
    exports com.example.integrationprojectsdoop2;
    exports com.example.integrationprojectsdoop2.Controllers;
    opens com.example.integrationprojectsdoop2.Controllers to javafx.fxml;
}