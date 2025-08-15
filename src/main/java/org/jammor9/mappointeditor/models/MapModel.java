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
    private ArrayList<MarkerModel> markerModels;

    private static MapModel mapModel = null;
    private ArrayList<MapListener> listeners;

    private MapModel() {
        this.mapNameProperty = new SimpleStringProperty("");
        this.mapImageProperty = new SimpleObjectProperty<>();
        this.markerModels = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public static MapModel getInstance() {
        if (mapModel == null) mapModel = new MapModel();
        return mapModel;
    }

    public void createNewMap(String mapName, Image image) {
        this.mapNameProperty.set(mapName);
        this.mapImageProperty.set(image);
        for (MapListener l : listeners) l.update(Command.NEW_MAP);
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

    public void registerListener(MapListener mapListener) {
        listeners.add(mapListener);
    }

    public void removeListener(MapListener mapListener) {
        listeners.remove(mapListener);
    }

    @Override
    public String toString() {
        return getMapName();
    }

    @Override
    public void setName(String s) {
        setMapName(s);
    }

    @Override
    public void add(ModelComposite modelComposite) {
        super.add(modelComposite);
        for (MapListener l : listeners) l.update(Command.ADD_TREE_CHILD);
    }

    @Override
    public void execute() {

    }
}
