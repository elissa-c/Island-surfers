module oop.hunt.huntproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens oop.hunt.huntproject to javafx.fxml;
    exports oop.hunt.huntproject;
}