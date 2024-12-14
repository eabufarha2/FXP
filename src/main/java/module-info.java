module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;

    requires org.kordamp.ikonli.fontawesome;
    requires javafx.graphics;

    opens com.example to javafx.fxml;

    exports com.example;
}
