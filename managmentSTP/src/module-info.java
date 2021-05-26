module managmentSTP {
    requires java.sql;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens sample;
    opens sample.Controllers;
    opens sample.utils;

}