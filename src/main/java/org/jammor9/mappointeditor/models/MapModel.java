package org.jammor9.mappointeditor.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class MapModel {
    private StringProperty mapNameProperty;
    private ObjectProperty<Image> mapImageProperty;

    private static MapModel mapModel = null;

    private MapModel() {
        this.mapNameProperty = new SimpleStringProperty("");
        this.mapImageProperty = new SimpleObjectProperty<>();
    }

    public static MapModel getInstance() {
        if (mapModel == null) mapModel = new MapModel();
        return mapModel;
    }

    public StringProperty getMapNameProperty() {
        return this.mapNameProperty;
    }

    public String getMapName() {
        return this.mapNameProperty.get();
    }

    public void setMapNameProperty(String mapName) {
        this.mapNameProperty.set(mapName);
    }

    public Image getMapImage() {
        return this.mapImageProperty.get();
    }

    public ObjectProperty<Image> getMapImageProperty() {
        return this.mapImageProperty;
    }

    public void setMapImageProperty(Image image) {
        this.mapImageProperty.set(image);
    }
}
