package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.Utils.Utils;
import org.jammor9.mappointeditor.models.*;

import java.util.Optional;

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

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        if (!visibleModel.isActiveProject()) return;

        SaveAndLoad.saveFile();;
    }

    @FXML
    public void handleLoad(ActionEvent actionEvent) {
        SaveAndLoad.loadFile();
    }

    private Stage getStage() {
        return (Stage) menuBarRoot.getScene().getWindow();
    }

    @Override
    public void update(Command c) {

    }

    public void createNewCalendar(ActionEvent actionEvent) {
        CalendarModel calendarModel = new CalendarModel(null);
        Dialog<CalendarModel> markerDialog = new CalendarDialog(calendarModel); //Open a Dialog Box to create a new Marker
        Optional<CalendarModel> result = markerDialog.showAndWait();
        if (result.isPresent()) {
            CalendarModel newCalendar = result.get();
        }
    }
}
