package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.jammor9.mappointeditor.models.MapModel;


public class RootController{

    @FXML public AnchorPane root;
    private MapModel mapModel = MapModel.getInstance();

    //Updates Child Controllers when a new map is created
    public void newMap(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    @FXML
    public void testMethod() {
        System.out.println(mapModel);
    }
}
