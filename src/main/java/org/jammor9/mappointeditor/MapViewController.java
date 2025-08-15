package org.jammor9.mappointeditor;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.MarkerModel;

public class MapViewController implements MapListener {
    @FXML public ZoomableScrollPane mapViewScrollPane;
    private MapModel mapModel = MapModel.getInstance();
    private ImageView mapImageView;
    private ContextMenu contextMenu;

    @FXML
    private void initialize() {
        this.mapImageView = new ImageView(mapModel.getMapImage());

        contextMenu = createContextMenu(); //Create ContextMenu

        //Initialise the context menu call
        mapImageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenu.show(mapImageView, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });


        //Initialise the ZoomableScrollPane
        mapViewScrollPane.setTarget(mapImageView);
        mapModel.registerListener(this);
    }

    public void loadMap() {
        mapImageView.setImage(mapModel.getMapImage());
    }

    @Override
    public void update(Command c) {
        if (c == Command.NEW_MAP) loadMap();
    }

    //Creates the ContextMenu for the ImageView
    public ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createMarker = new MenuItem("Create Marker");

        createMarker.setOnAction(e -> {
            MarkerModel.createMarker(mapModel, getStage());
        });

        contextMenu.getItems().addAll(createMarker);

        return contextMenu;
    }

    public Stage getStage() {
        return (Stage) mapViewScrollPane.getScene().getWindow();
    }
}
