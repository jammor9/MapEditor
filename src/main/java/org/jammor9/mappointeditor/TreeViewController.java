package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import org.jammor9.mappointeditor.models.*;

import java.io.File;

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
            case ADD_TREE_CHILD, NEW_PROJECT, NEW_MAP, OPEN_MAP -> updateTree();
        }
    }

    //Updates the TreeView whenever a new ModelComposite is added
    private void updateTree() {
        headerItem = visibleModel.getProjectHeaderView().getTree();
        nodeView.setRoot(headerItem);
        nodeView.refresh();;
    }

    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");
        Menu addSubMenu = new Menu("Add");
        MenuItem addMap = new MenuItem("New Map");
        MenuItem addArticle = new MenuItem("New Article");
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

        //Creates a popup to create a new folder by asking for the name
        addFolder.setOnAction(e-> {
            //Create Popup
            Popup popup = new Popup();
            TextField textField = new TextField();
            textField.setPromptText("Folder Name");
            Pane pane = new Pane();
            pane.getChildren().add(textField);
            popup.getContent().add(pane);
            popup.setAutoHide(true);
            popup.show(nodeView.getScene().getWindow());

            //Add Behaviour
            pane.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER) {
                    if (textField.getText().isEmpty()) popup.hide();
                    FolderModel folderModel = new FolderModel(textField.getText());
                    nodeView.getSelectionModel().getSelectedItem().getValue().add(folderModel);
                    updateTree();
                    popup.hide();
                }
            });
        });

        //Opens FileChooser then instantiates a new MapModel object from the image
        //Adds the new MapModel to the ModelComposite tree from the currently selected item
        addMap.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            //Ensure you can only select images with fileChooser
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp")
            );

            File selectedFile = fileChooser.showOpenDialog(nodeView.getScene().getWindow());
            if (selectedFile == null) return;
            Image image = new Image(selectedFile.toURI().toString()); //Convert File to Image for JavaFX
            MapModel mapModel = new MapModel(selectedFile.getName(), image);
            nodeView.getSelectionModel().getSelectedItem().getValue().add(mapModel);
            visibleModel.addMap(mapModel);
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
