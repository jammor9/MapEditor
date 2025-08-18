package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import org.jammor9.mappointeditor.models.*;

public class TreeViewController  implements ModelListener {

    @FXML public TreeView<ModelComposite> nodeView;
    public TreeItem<ModelComposite> headerItem;

    private VisibleModel visibleModel = VisibleModel.getInstance();

    @FXML
    public void initialize() {
        visibleModel.registerListener(this);
        nodeView.setContextMenu(createContextMenu());
        createTreeViewCommands();

    }

    @Override
    public void update(Command c) {
        switch (c) {
            case NEW_MAP -> headerItem.setValue(visibleModel.getCurrentView());
            case ADD_TREE_CHILD -> updateTree();
        }
    }

    //Updates the TreeView whenever a new ModelComposite is added
    private void updateTree() {
        headerItem = visibleModel.getCurrentView().getTree();
        nodeView.setRoot(headerItem);
        nodeView.refresh();
    }

    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");
        Menu addSubMenu = new Menu("Add");
        MenuItem addMap = new MenuItem("Add Map");
        MenuItem addArticle = new MenuItem("Add Article");
        MenuItem addFolder = new MenuItem("New Folder");
        addSubMenu.getItems().addAll(addMap, addArticle, addFolder);

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
        contextMenu.getItems().add(addSubMenu);

        return contextMenu;
    }

    private void createTreeViewCommands() {

        nodeView.setOnMouseClicked(e -> {
            //Focuses on the selected TreeItem when the TreeView is double clicked
            if (e.getClickCount() == 2) {
                ModelComposite mc = nodeView.getSelectionModel().getSelectedItem().getValue();
                if (mc.getClass() == MarkerModel.class) visibleModel.setCurrentView(((MarkerModel) mc).getMarkerType());
                else visibleModel.setCurrentView(mc);
            }
        });
    }
}
