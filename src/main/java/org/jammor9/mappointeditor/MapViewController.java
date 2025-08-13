package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import org.jammor9.mappointeditor.models.MapModel;

public class MapViewController {
    @FXML public ImageView mapImageView;
    public ScrollPane mapViewScrollPane;
    private MapModel mapModel = MapModel.getInstance();

    @FXML
    private void initialize() {
        mapImageView.setImage(mapModel.getMapImage());
    }

    public void loadMap(ActionEvent actionEvent) {
        mapImageView.setImage(mapModel.getMapImage());
    }

    public void zoomPane(ScrollEvent scrollEvent) {
        System.out.println(scrollEvent.getDeltaY());
        if (scrollEvent.getDeltaY() > 0) mapImageView.setScaleX(mapImageView.getScaleX() + 1);
        else mapImageView.setScaleX(mapImageView.getScaleX() - 1);
    }
}
