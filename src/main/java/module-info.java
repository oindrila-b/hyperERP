module com.example.hypererp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.example.hypererp to javafx.fxml;
    exports com.example.hypererp;
}