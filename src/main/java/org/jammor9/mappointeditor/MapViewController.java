package org.jammor9.mappointeditor;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.*;
import java.util.ArrayList;
import java.util.Map;

public class MapViewController implements MapListener {
    @FXML public ZoomableScrollPane mapViewScrollPane;

    private Group imageGroup;
    private ImageView mapImageView;
    private final VisibleModel visibleModel = VisibleModel.getInstance();
    private ContextMenu contextMenu;

    private double mousePointX;
    private double mousePointY;

    @FXML
    private void initialize() {
        contextMenu = createContextMenu(); //Create ContextMenu
        mapImageView = new ImageView();

        //Initialise the ZoomableScrollPane
        imageGroup = new Group(mapImageView);
        mapViewScrollPane.setTarget(imageGroup);
        visibleModel.registerListener(this);

        //Get coordinates when right click called
        imageGroup.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                mousePointX = e.getX();
                mousePointY = e.getY();
            }
        });

        //Initialise the context menu call
        imageGroup.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenu.show(imageGroup, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
    }

    public void loadMap() {
        MapModel mapModel = getVisibleMap();
        mapImageView.setImage(mapModel.getMapImage());
    }

    @Override
    public void update(Command c) {
        switch (c) {
            case NEW_MAP -> loadMap();
            case ADD_TREE_CHILD -> updateImageView();
        }
    }

    //Creates the ContextMenu for the ImageView
    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createMarker = new MenuItem("Create Marker");


        createMarker.setOnAction(e -> {
            Image image = new Image(mapViewScrollPane.getClass().getResource("img/marker2.png").toExternalForm()); //Get the Marker Image from Resources
            MapModel mapModel = getVisibleMap();
            MarkerModel.createMarker(visibleModel,mapModel, getStage(), mousePointX, mousePointY, image);
        });

        contextMenu.getItems().addAll(createMarker);

        return contextMenu;
    }

    public Stage getStage() {
        return (Stage) mapViewScrollPane.getScene().getWindow();
    }

    //Creates all the ImageViews used by the Map, including markers and the map itself
    public void updateImageView() {
        ArrayList<ModelComposite> children = getVisibleMap().getChildren();
        imageGroup.getChildren().clear();
        imageGroup.getChildren().add(mapImageView);
        for (ModelComposite mc : children) {
            if (mc.getClass() == MarkerModel.class) {
                Image markerImage = ((MarkerModel) mc).getMarkerImage();
                double x = ((MarkerModel) mc).getX();
                double y = ((MarkerModel) mc).getY();
                ImageView markerView = createMarkerView(x, y, markerImage, mc);
                imageGroup.getChildren().add(markerView);
            }
        }
        loadMap();
    }

    //Creates a new MarkerView with appropriate functionality
    private ImageView createMarkerView(double x, double y, Image img, ModelComposite mc) {
        ImageView markerView = new ImageView(img);
        markerView.setX(x - img.getWidth()/2);
        markerView.setY(y - img.getHeight());

        //Adds dragging functionality so that the marker can be moved
        markerView.setOnMouseDragged(e -> {
            mapViewScrollPane.setPannable(false);
            if (e.getX() > 0 && e.getX() < mapImageView.getImage().getWidth()) markerView.setX(e.getX() - markerView.getImage().getWidth()/2);
            if (e.getY() > 0 && e.getY() < mapImageView.getImage().getHeight()) markerView.setY(e.getY() - markerView.getImage().getHeight());
        });


        //Once drag has finished make the ScrollPane pannable again
        markerView.setOnMouseReleased(e -> {
            mapViewScrollPane.setPannable(true);
        });

        //Changes mouse cursor when hovering over
        markerView.setOnMouseEntered(e -> {
            imageGroup.getScene().setCursor(Cursor.HAND);
        });

        //Revert to normal once mouse moves away
        markerView.setOnMouseExited(e -> {
            imageGroup.getScene().setCursor(Cursor.DEFAULT);
        });

        //Creates Tooltip for Marker
        Tooltip tooltip = new Tooltip(mc.toString());
        Tooltip.install(markerView, tooltip);

        return markerView;
    }

    public MapModel getVisibleMap() {
        return (MapModel) visibleModel.getCurrentView();
    }

    public ArticleModel getVisibleArticle() {
        return (ArticleModel) visibleModel.getCurrentView();
    }
}
