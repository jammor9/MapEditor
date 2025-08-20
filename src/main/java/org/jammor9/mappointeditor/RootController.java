package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.jammor9.mappointeditor.models.*;


public class RootController implements ModelListener {

    @FXML public BorderPane root;
    @FXML public HBox mapView;
    @FXML public AnchorPane editorView;
    public StackPane rootStack;
    private VisibleModel visibleModel = VisibleModel.getInstance();

    @FXML
    public void testMethod() {

    }

    @FXML
    public void initialize() {
        rootStack.setAlignment(Pos.CENTER);
        visibleModel.registerListener(this);
    }

    @FXML
    public void openTextEditor() {
        mapView.setVisible(false);
        editorView.setVisible(true);
    }

    @FXML
    public void openMapEditor() {
        mapView.setVisible(true);
        editorView.setVisible(false);
    }

    @Override
    public void update(Command c) {
        switch(c) {
            case NEW_MAP, OPEN_MAP -> openMapEditor();
            case OPEN_ARTICLE -> openTextEditor();
        }
    }
}
