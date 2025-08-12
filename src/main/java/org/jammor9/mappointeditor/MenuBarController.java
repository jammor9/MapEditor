package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class MenuBarController {

    @FXML public MenuItem quitButton;
    @FXML public MenuItem openProjectButton;
    @FXML public MenuItem newProjectButton;
    @FXML public AnchorPane menuBarRoot;

    Image mapImage;

    @FXML
    public void handleNewMap() {

    }

    @FXML
    public void closeApplication() {
        Stage stage = (Stage) menuBarRoot.getScene().getWindow();
        stage.close();
    }

}
