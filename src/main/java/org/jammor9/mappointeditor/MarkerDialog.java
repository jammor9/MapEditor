package org.jammor9.mappointeditor;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.jammor9.mappointeditor.models.MarkerModel;
import org.jammor9.mappointeditor.models.modelfactory.ArticleFactory;
import org.jammor9.mappointeditor.models.modelfactory.ModelFactory;

import javax.swing.*;

public class MarkerDialog extends Dialog<MarkerModel> {
    private MarkerModel marker;

    private TextField nameField;
    private ChoiceBox<ModelFactory> choiceBox;
    private boolean badResult = false;

    public MarkerDialog(MarkerModel markerModel) {
        super();
        this.marker = markerModel;
        this.nameField = new TextField();
        this.nameField.setPromptText("Marker Name");
        createChoiceBox();
        createWindow();
        resultsConverter();
    }

    private void createChoiceBox() {
        this.choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(new ArticleFactory());
    }

    private void createWindow() {
        GridPane gridPane = new GridPane();
        gridPane.add(nameField, 0, 0);
        gridPane.add(choiceBox, 0, 1);
        getDialogPane().setContent(gridPane);
        getDialogPane().getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private boolean validateDialog() {
        return !nameField.getText().isEmpty();
    }

    private void resultsConverter() {
        Callback<ButtonType, MarkerModel> markerModelCallback = buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (!validateDialog()) return null;
                if (choiceBox.getSelectionModel().getSelectedItem() == null) return null;
                marker.setName(nameField.getText());
                marker.setMarkerType(choiceBox.getSelectionModel().getSelectedItem().createModel(nameField.getText()));
                return this.marker;
            }
            return null;
        };
        setResultConverter(markerModelCallback);
    }
}
