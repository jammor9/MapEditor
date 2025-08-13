package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jammor9.mappointeditor.models.MapModel;

public class MapViewController {
    @FXML public ZoomableScrollPane mapViewScrollPane;
    private MapModel mapModel = MapModel.getInstance();
    private ImageView mapImageView;


    @FXML
    private void initialize() {
        this.mapImageView = new ImageView(mapModel.getMapImage());
        mapViewScrollPane.setTarget(mapImageView);
    }

    public void loadMap(ActionEvent actionEvent) {
        mapImageView.setImage(mapModel.getMapImage());
    }
}
