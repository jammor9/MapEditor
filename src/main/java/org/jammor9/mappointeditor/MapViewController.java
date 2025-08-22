package org.jammor9.mappointeditor;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class MapViewController implements ModelListener {
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

    private void loadMap() {
        MapModel mapModel = getVisibleMap();
        mapImageView.setImage(mapModel.getMapImage());
    }

    @Override
    public void update(Command c) {
        switch (c) {
            case ADD_TREE_CHILD, NEW_MAP, OPEN_MAP -> updateImageView();
        }
    }

    //Creates the ContextMenu for the ImageView
    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createMarker = new MenuItem("Create Marker");
        MenuItem setMarkerScale = new MenuItem("Set Marker Scale");

        //Generates a marker from input when clicked
        createMarker.setOnAction(e -> {
            createMarker();
        });

        //Sets scale of markers relative to the map
        setMarkerScale.setOnAction(e -> {
            setMarkerScale();
        });

        contextMenu.getItems().addAll(createMarker, setMarkerScale);

        return contextMenu;
    }

    public Stage getStage() {
        return (Stage) mapViewScrollPane.getScene().getWindow();
    }

    //Creates all the ImageViews used by the Map, including markers and the map itself
    public void updateImageView() {
        mapImageView.setImage(getVisibleMap().getMapImage());
        ArrayList<ModelComposite> children = getVisibleMap().getChildren();
        imageGroup.getChildren().clear();
        imageGroup.getChildren().add(mapImageView);
        for (ModelComposite mc : children) {
            if (mc.getClass() == MarkerModel.class) {
                Image markerImage = ((MarkerModel) mc).getMarkerImage();
                double x = ((MarkerModel) mc).getX();
                double y = ((MarkerModel) mc).getY();
                ImageView markerView = createMarkerView(x, y, markerImage, (MarkerModel) mc);

                imageGroup.getChildren().add(markerView);
            }
        }
    }

    //Creates a new MarkerView with appropriate functionality
    private ImageView createMarkerView(double x, double y, Image img, MarkerModel mc) {
        ImageView markerView = new ImageView(img);

        //Scale the markerView to the size of the map image
        markerView.setFitWidth(getVisibleMap().getMarkerScale()*mapImageView.getImage().getWidth());
        markerView.setFitHeight(getVisibleMap().getMarkerScale()*mapImageView.getImage().getHeight());
        markerView.setPreserveRatio(true);
        markerView.setX(x - markerView.getFitWidth()/3); //Dividing by 3 centres the image at the mouse point
        markerView.setY(y - markerView.getFitHeight());

        //Adds dragging functionality so that the marker can be moved
        markerView.setOnMouseDragged(e -> {
            mapViewScrollPane.setPannable(false);
            if (e.getX() > 0 && e.getX() < mapImageView.getImage().getWidth()) markerView.setX(e.getX() - markerView.getFitWidth()/3);
//            markerView.setX(Math.clamp(markerView.getX(), 1, mapImageView.getImage().getWidth()-1));
            if (e.getY() > 0 && e.getY() < mapImageView.getImage().getHeight()) markerView.setY(e.getY() - markerView.getFitHeight());
//            markerView.setY(Math.clamp(markerView.getY(), 1, mapImageView.getImage().getHeight()-1));
        });

        markerView.setOnMouseClicked(e -> {
            visibleModel.setCurrentView(mc.getMarkerType());
        });

        //Once drag has finished make the ScrollPane pannable again and update marker coords
        markerView.setOnMouseReleased(e -> {
            mapViewScrollPane.setPannable(true);
            mc.setX(e.getX());
            mc.setY(e.getY());
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

    //Generate a new marker
    public void createMarker() {
        MarkerModel markerModel = new MarkerModel();
        markerModel.setX(mousePointX);
        markerModel.setY(mousePointY);
        markerModel.setMarkerImage(new Image(Objects.requireNonNull(mapViewScrollPane.getClass().getResource("img/marker2.png")).toExternalForm()));
        Dialog<MarkerModel> markerDialog = new MarkerDialog(markerModel); //Open a Dialog Box to create a new Marker
        Optional<MarkerModel> result = markerDialog.showAndWait();
        if (result.isPresent()) {
            MarkerModel newMarker = result.get();
            visibleModel.add(newMarker);
        }
    }

    //Set marker scale
    public void setMarkerScale() {
        Popup popup = Utils.getQuickInputPopup("Enter Decimal (0-1)");
        TextField t = (TextField) popup.getContent().getFirst();

        t.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (t.getText().isEmpty()) popup.hide();
                try {
                    double d = Double.parseDouble(t.getText());
                    d = Math.clamp(d, 0, 1);
                    getVisibleMap().setMarkerScale(d);
                    popup.hide();
                    updateImageView();
                } catch (NumberFormatException ex) {
                    popup.hide();
                }
            }
        });

        popup.show(getStage());
    }

    public MapModel getVisibleMap() {
        return (MapModel) visibleModel.getCurrentView();
    }

    public ArticleModel getVisibleArticle() {
        return (ArticleModel) visibleModel.getCurrentView();
    }
}
