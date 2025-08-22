package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.*;

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
    //Generates a popup to enter the name of a new project, that creates the project
    public void handleNewProject() {
        //Create Popup
        Popup popup = Utils.getQuickInputPopup("Project Name");
        TextField t = (TextField) popup.getContent().getFirst();

        //Add Behaviour
        t.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                if (t.getText().isEmpty()) popup.hide();
                FolderModel folderModel = new FolderModel(t.getText());
                visibleModel.newProject(folderModel);
                popup.hide();
            }
        });

        popup.show(getStage());
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
