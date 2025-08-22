module org.jammor9.mappointeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.naming;
    requires jdk.compiler;
    requires java.desktop;
    requires jdk.jshell;
    requires com.google.gson;

    opens org.jammor9.mappointeditor to com.google.gson, javafx.fxml;
    exports org.jammor9.mappointeditor;
    exports org.jammor9.mappointeditor.models;
    opens org.jammor9.mappointeditor.models to javafx.fxml, com.google.gson;
    exports org.jammor9.mappointeditor.models.modelfactory;
    opens org.jammor9.mappointeditor.models.modelfactory to javafx.fxml;
    exports org.jammor9.mappointeditor.Utils;
    opens org.jammor9.mappointeditor.Utils to javafx.fxml;
}