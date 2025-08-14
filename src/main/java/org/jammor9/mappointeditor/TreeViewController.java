package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.ModelComposite;

public class TreeViewController  implements MapListener {

    @FXML public TreeView<ModelComposite> nodeView;
    public TreeItem<ModelComposite> headerItem;

    private MapModel mapModel = MapModel.getInstance();

    @FXML
    public void initialize() {
        mapModel.registerListener(this);
    }

    @Override
    public void update() {
        headerItem.setValue(mapModel);
    }
}
