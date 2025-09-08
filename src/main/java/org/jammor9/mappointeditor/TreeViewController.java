package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import org.jammor9.mappointeditor.Utils.Utils;
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
        headerItem.setExpanded(true);
        nodeView.refresh();;
    }

    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");
        Menu addSubMenu = new Menu("Add");
        MenuItem addMap = new MenuItem("New Map");
        MenuItem addArticle = new MenuItem("New Article");
        MenuItem addFolder = new MenuItem("New Folder");
        MenuItem deleteItem = new MenuItem("Delete");
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
            Popup popup = Utils.getQuickInputPopup("Folder Name");
            TextField t = (TextField) popup.getContent().getFirst();

            //Add Behaviour
            t.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER) {
                    if (t.getText().isEmpty()) popup.hide();
                    FolderModel folderModel = new FolderModel(t.getText());
                    nodeView.getSelectionModel().getSelectedItem().getValue().add(folderModel);
                    updateTree();
                    popup.hide();
                }
            });

            popup.show(nodeView.getScene().getWindow());

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
            MapModel mapModel = new MapModel(selectedFile.getName(), selectedFile.toURI().toString());
            nodeView.getSelectionModel().getSelectedItem().getValue().add(mapModel);
            visibleModel.addMap(mapModel);
        });

        //Instantiate a new article
        addArticle.setOnAction(e -> {
            Popup popup = Utils.getQuickInputPopup("Article Name");
            TextField t = (TextField) popup.getContent().getFirst();

            t.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER) {
                    if (!t.getText().isEmpty()) {
                        ArticleModel articleModel= new ArticleModel();
                        articleModel.setName(t.getText());
                        nodeView.getSelectionModel().getSelectedItem().getValue().add(articleModel);
                        updateTree();
                    }
                    popup.hide();
                }
            });

            popup.show(nodeView.getScene().getWindow());
        });

        //Checks if the user really wants to the selected item
        deleteItem.setOnAction(e->handleDeleteButton());

        contextMenu.getItems().addAll(rename, addSubMenu, deleteItem);

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

    private void handleDeleteButton() {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        ModelComposite selected = nodeView.getSelectionModel().getSelectedItem().getValue();
        if (selected == visibleModel.getProjectHeaderView()) return;

        Label label = new Label("Are you sure you want to delete " + selected.toString() +" this action is irreversible AND will delete all children!");
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        confirmButton.setOnAction(e -> {
            visibleModel.getProjectHeaderView().delete(selected, selected.getParentIdentifier());
            updateTree();
            popup.hide();
        });

        cancelButton.setOnAction(e -> popup.hide());

        GridPane gridPane = new GridPane();
        gridPane.add(label, 0, 0);
        gridPane.add(confirmButton, 0, 1);
        gridPane.add(cancelButton, 1, 1);
        popup.getContent().add(gridPane);
        popup.show(nodeView.getScene().getWindow());
    }

}
