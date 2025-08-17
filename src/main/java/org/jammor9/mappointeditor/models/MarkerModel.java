package org.jammor9.mappointeditor.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import org.jammor9.mappointeditor.models.modelfactory.ModelFactory;

public class MarkerModel extends ModelComposite {

    private StringProperty markerName;
    private ObjectProperty<Image> markerImage;
    private ObjectProperty<ModelComposite> markerType;
    private double x;
    private double y;

    public MarkerModel() {
        this.markerType = new SimpleObjectProperty<>();
    }

    @Override
    public String toString() {
        return getMarkerName();
    }

    @Override
    public void setName(String s) {
        setMarkerNameProperty(new SimpleStringProperty(s));
    }

    public StringProperty getMarkerNameProperty() {
        return this.markerName;
    }

    public String getMarkerName() {
        return this.markerName.get();
    }

    public void setMarkerNameProperty(StringProperty markerName) {
        this.markerName = markerName;
    }

    public ObjectProperty<Image> getMarkerImageProperty() {
        return this.markerImage;
    }

    public Image getMarkerImage() {
        return this.markerImage.get();
    }

    public void setMarkerImage(Image image) {
        this.markerImage = new SimpleObjectProperty<>(image);
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
        return this.markerType.get();
    }

    public void setMarkerType(ModelComposite markerType) {
        this.markerType = new SimpleObjectProperty<>(markerType);
    }

    public ObjectProperty<ModelComposite> getMarkerTypeProperty() {
        return this.markerType;
    }
}
