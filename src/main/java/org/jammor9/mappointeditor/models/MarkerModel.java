package org.jammor9.mappointeditor.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import org.jammor9.mappointeditor.models.modelfactory.ModelFactory;

import java.util.ArrayList;

public class MarkerModel extends ModelComposite {

    private static final String CLASS_META_KEY = MarkerModel.class.getCanonicalName();

    private String markerName;
    private String markerImageURL;
    private ModelComposite markerType;
    private double x;
    private double y;

    public MarkerModel() {
        super(CLASS_META_KEY);
    }

    @Override
    public String toString() {
        return getMarkerName();
    }

    @Override
    public void setName(String s) {
        setMarkerName(s);
    }

    @Override
    public ArrayList<ModelComposite> getChildren() {
        return markerType.getChildren();
    }

    @Override
    public void add(ModelComposite mc) {
        markerType.add(mc);
    }

    @Override
    public void remove(ModelComposite mc) {
        markerType.remove(mc);
    }

    public String getMarkerName() {
        return this.markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public Image getMarkerImage() {
        return new Image(this.markerImageURL);
    }

    public void setMarkerImage(String markerImageURL) {
        this.markerImageURL = markerImageURL;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ModelComposite getMarkerType() {
        return this.markerType;
    }

    public void setMarkerType(ModelComposite markerType) {
        this.markerType = markerType;
    }
}
