module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Open the package to JavaFX Reflection
    opens com.example to javafx.fxml;

    // Export the package for use outside the module
    exports com.example;
}
