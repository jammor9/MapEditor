package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.ModelListener;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.VisibleModel;

import java.io.File;

public class MenuBarController implements ModelListener {

    //FXML IDs
    @FXML public MenuItem quitButton;
    @FXML public MenuItem openProjectButton;
    @FXML public MenuItem newProjectButton;
    @FXML public HBox menuBarRoot;
    @FXML public MenuItem saveProjectButton;

    private VisibleModel visibleModel = VisibleModel.getInstance();

    @FXML
    public void initialize() {
        visibleModel.registerListener(this);
    }

    @FXML
    public void handleNewMap() {
        FileChooser fileChooser = new FileChooser();

        //Ensure you can only select images with fileChooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile == null) return;
        Image image = new Image(selectedFile.toURI().toString()); //Convert File to Image for JavaFX
        MapModel mapModel = new MapModel(selectedFile.getName(), image);
        visibleModel.setCurrentView(mapModel);
    }

    @FXML
    public void closeApplication() {
        Stage stage = getStage();
        stage.close();
    }

    private Stage getStage() {
        return (Stage) menuBarRoot.getScene().getWindow();
    }

    @Override
    public void update(Command c) {

    }
}
