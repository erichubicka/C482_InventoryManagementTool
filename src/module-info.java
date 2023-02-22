//this file adds security to the controller and includes file/package permissions to be used by the controller
module ehubicka.inventory {
    requires javafx.controls;
    requires javafx.fxml;

//following code allows the following files/packages to be used by the controller
    opens ehubicka.Main to javafx.fxml;
    exports ehubicka.Main;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}