package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.jammor9.mappointeditor.models.MapModel;


public class RootController{

    @FXML public BorderPane root;
    private MapModel mapModel = MapModel.getInstance();

    @FXML
    public void testMethod() {
        System.out.println(mapModel.getTree().getChildren());
    }
}
