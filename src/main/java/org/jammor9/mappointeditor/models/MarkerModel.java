package org.jammor9.mappointeditor.models;

import javafx.beans.property.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import org.jammor9.mappointeditor.models.modelfactory.ArticleFactory;
import org.jammor9.mappointeditor.models.modelfactory.ModelFactory;

public class MarkerModel extends ModelComposite {

    private StringProperty markerName;
    private ObjectProperty<Image> markerImage;
    private ModelComposite markerType;
    private double x;
    private double y;

    public MarkerModel() {
        this.markerType = null;
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
        return this.markerType;
    }

    public void setMarkerType(ModelComposite markerType) {
        this.markerType = markerType;
    }

    //Creates a form in a popup that is used to produce a new MarkerModel
    //ModelComposite is who it will be assigned as the child of, Window is where it will be displayed
    public static void createMarker(ModelComposite modelComposite, Window window, double x, double y, Image image) {
        MarkerModel markerModel = new MarkerModel();
        Popup popup = new Popup();
        GridPane gridPane = new GridPane();

        Label label = new Label("Enter Marker Name: ");
        TextField textField = new TextField();
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 1, 0);

        ChoiceBox<ModelFactory> choiceBox = new ChoiceBox<>();
        //Instantiate different Model Types
        choiceBox.getItems().add(new ArticleFactory());

        gridPane.add(choiceBox, 0, 1);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            //Read Text Field
            if (textField.getText().isEmpty()) {
                popup.hide(); //Don't create a MarkerModel with an empty name
                return;
            }
            markerModel.setMarkerNameProperty(new SimpleStringProperty(textField.getText()));

            //Read ChoiceBox
            ModelFactory mf = choiceBox.getSelectionModel().getSelectedItem();
            ModelComposite markerType = mf.createModel(textField.getText());

            markerModel.setX(x);
            markerModel.setY(y);
            markerModel.setMarkerImage(image);
            markerModel.setMarkerType(markerType);
            modelComposite.add(markerModel);
            popup.hide();
        });

        gridPane.add(submitButton, 0, 2);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
        gridPane.setStyle("-fx-background-color: #3b3b3b;");

        popup.getContent().add(gridPane);
        popup.setAutoHide(true);
        popup.show(window);
    }
}
