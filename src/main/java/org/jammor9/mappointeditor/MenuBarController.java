package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.MapModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements MapListener {

    //FXML IDs
    @FXML public MenuItem quitButton;
    @FXML public MenuItem openProjectButton;
    @FXML public MenuItem newProjectButton;
    @FXML public VBox menuBarRoot;

    MapModel mapModel = MapModel.getInstance();


    @FXML
    public void initialize() {
        mapModel.registerListener(this);
    }

    @FXML
    public void handleNewMap() {
        Stage stage = getStage();
        Popup popup = new Popup();

        GridPane popupGrid = new GridPane();

        Button openFileButton = new Button("Open File...");
        Button closeButton = new Button("Close");


        //Opens a new file
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();

                //Ensure you can only select images with fileChooser
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp")
                );

                File selectedFile = fileChooser.showOpenDialog(stage);
                Image image = new Image(selectedFile.toURI().toString()); //Convert File to Image for JavaFX
                mapModel.createNewMap(selectedFile.getName(), image);
            }
        });

        //Closes popup when pressed
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        });

        popupGrid.add(openFileButton, 0, 0);
        popupGrid.add(closeButton, 1, 0);

        popup.getContent().add(popupGrid);
        popup.setAutoHide(true);

        popup.show(stage);
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
