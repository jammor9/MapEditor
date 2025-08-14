module org.jammor9.mappointeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.naming;

    opens org.jammor9.mappointeditor to javafx.fxml;
    exports org.jammor9.mappointeditor;
    exports org.jammor9.mappointeditor.models;
    opens org.jammor9.mappointeditor.models to javafx.fxml;
}