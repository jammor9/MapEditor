package org.jammor9.mappointeditor.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class MapModel extends ModelComposite {
    private StringProperty mapNameProperty;
    private ObjectProperty<Image> mapImageProperty;
    private DoubleProperty markerScale;

    public MapModel(String mapName, Image image) {
        this.mapNameProperty = new SimpleStringProperty(mapName);
        this.mapImageProperty = new SimpleObjectProperty<>(image);
        this.markerScale = new SimpleDoubleProperty(0.05);
    }

    public void createNewMap(String mapName, Image image) {
        this.mapNameProperty.set(mapName);
        this.mapImageProperty.set(image);
    }

    public StringProperty getMapNameProperty() {
        return this.mapNameProperty;
    }

    public String getMapName() {
        return this.mapNameProperty.get();
    }

    public void setMapName(String s) {
        mapNameProperty = new SimpleStringProperty(s);
    }

    public Image getMapImage() {
        return this.mapImageProperty.get();
    }

    public ObjectProperty<Image> getMapImageProperty() {
        return this.mapImageProperty;
    }

    public DoubleProperty getMarkerScaleProperty() {
        return this.markerScale;
    }

    public Double getMarkerScale() {
        return this.markerScale.get();
    }

    public void setMarkerScale(Double d) {
        this.markerScale = new SimpleDoubleProperty(d);
    }

    @Override
    public String toString() {
        return getMapName();
    }

    @Override
    public void setName(String s) {
        setMapName(s);
    }
}
