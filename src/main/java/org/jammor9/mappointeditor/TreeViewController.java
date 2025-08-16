package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.ModelComposite;

import java.awt.event.KeyEvent;
import java.beans.EventHandler;
import java.util.ArrayList;

public class TreeViewController  implements MapListener {

    @FXML public TreeView<ModelComposite> nodeView;
    public TreeItem<ModelComposite> headerItem;

    private final MapModel mapModel = MapModel.getInstance();

    @FXML
    public void initialize() {
        mapModel.registerListener(this);
        nodeView.setContextMenu(createContextMenu());
        createTreeViewCommands();

    }

    @Override
    public void update(Command c) {
        switch (c) {
            case NEW_MAP -> headerItem.setValue(mapModel);
            case ADD_TREE_CHILD -> updateTree();
        }
    }

    //Updates the TreeView whenever a new ModelComposite is added
    private void updateTree() {
        headerItem = mapModel.getTree();
        nodeView.setRoot(headerItem);
        nodeView.refresh();
    }

    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");

        //Allows you to rename the ModelComposite via opening a popup, does not allow empty names
        rename.setOnAction(e -> {
            TreeItem<ModelComposite> selectedItem = nodeView.getSelectionModel().getSelectedItem();
            ModelComposite mc = selectedItem.getValue();
            Popup popup = new Popup();
            HBox hBox = new HBox();
            Label label = new Label("Enter New Name: ");
            TextField textField = new TextField();

            hBox.getChildren().addAll(label, textField);
            hBox.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
                    mc.setName(textField.getText());
                    updateTree();
                    popup.hide();
                }
                else if (ke.getCode() == KeyCode.ESCAPE) {
                    popup.hide();
                }
            });

            popup.getContent().add(hBox);
            popup.show(nodeView.getScene().getWindow());
        });

        contextMenu.getItems().add(rename);

        return contextMenu;
    }

    private void createTreeViewCommands() {

        nodeView.setOnMouseClicked(e -> {
            //Focuses on the selected TreeItem when the TreeView is double clicked
            if (e.getClickCount() == 2) {
                TreeItem<ModelComposite> item = nodeView.getSelectionModel().getSelectedItem();
                System.out.println(item);
            }
        });
    }
}
