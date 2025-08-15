package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.ModelComposite;

import java.util.ArrayList;

public class TreeViewController  implements MapListener {

    @FXML public TreeView<ModelComposite> nodeView;
    public TreeItem<ModelComposite> headerItem;

    private final MapModel mapModel = MapModel.getInstance();

    @FXML
    public void initialize() {
        mapModel.registerListener(this);
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
}
