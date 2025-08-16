package org.jammor9.mappointeditor.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class MapModel extends ModelComposite {
    private StringProperty mapNameProperty;
    private ObjectProperty<Image> mapImageProperty;

    public MapModel(String mapName, Image image) {
        this.mapNameProperty = new SimpleStringProperty(mapName);
        this.mapImageProperty = new SimpleObjectProperty<>(image);
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

    @Override
    public String toString() {
        return getMapName();
    }

    @Override
    public void setName(String s) {
        setMapName(s);
    }
}
