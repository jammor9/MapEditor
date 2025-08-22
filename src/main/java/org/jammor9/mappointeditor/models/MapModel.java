package org.jammor9.mappointeditor.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class MapModel extends ModelComposite {

    private static final String CLASS_META_KEY = MapModel.class.getCanonicalName();

    private String mapName;
    private String imgUrl;
    private double markerScale;

    public MapModel(String mapName, String imgUrl) {
        super(CLASS_META_KEY);
        this.mapName = mapName;
        this.imgUrl = imgUrl;
        this.markerScale = 0.05;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(String s) {
        this.mapName = s;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public Image getMapImage() {
        return new Image(imgUrl);
    }

    public Double getMarkerScale() {
        return this.markerScale;
    }

    public void setMarkerScale(Double d) {
        this.markerScale = d;
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
