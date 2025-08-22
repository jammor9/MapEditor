package org.jammor9.mappointeditor.models.modelfactory;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.jammor9.mappointeditor.Application;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.ModelComposite;
import org.jammor9.mappointeditor.models.VisibleModel;

import java.io.File;

public class MapFactory implements ModelFactory{

    private static final String FACTORY_TYPE = "Map";

    @Override
    public ModelComposite createModel(String name) {

        FileChooser fileChooser = new FileChooser();

        //Ensure you can only select images with fileChooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog(Application.getStage());
        if (selectedFile == null) return null;
        Image image = new Image(selectedFile.toURI().toString()); //Convert File to Image for JavaFX

        return new MapModel(name, image);
    }

    @Override
    public String toString() {
        return FACTORY_TYPE;
    }
}
