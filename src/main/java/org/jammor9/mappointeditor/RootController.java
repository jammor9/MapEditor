package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.jammor9.mappointeditor.models.MapModel;


public class RootController{

    @FXML public BorderPane root;
    @FXML public HBox mapView;
    @FXML public AnchorPane editorView;
    public StackPane rootStack;
    private MapModel mapModel = MapModel.getInstance();

    @FXML
    public void testMethod() {
        System.out.println(mapModel.getTree().getChildren());
    }

    @FXML
    public void initialize() {
        rootStack.setAlignment(Pos.CENTER);
    }

    @FXML
    public void openTextEditor(ActionEvent actionEvent) {
        mapView.setVisible(false);
        editorView.setVisible(true);
    }

    @FXML
    public void openMapEditor(ActionEvent actionEvent) {
        mapView.setVisible(true);
        editorView.setVisible(false);
    }
}
