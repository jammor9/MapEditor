package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.web.HTMLEditor;
import org.jammor9.mappointeditor.models.ArticleModel;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.ModelListener;
import org.jammor9.mappointeditor.models.VisibleModel;

public class EditorViewController implements ModelListener {
    @FXML public HTMLEditor articleEditor;
    private VisibleModel visibleModel = VisibleModel.getInstance();
    private ArticleModel currentModelData;
    private boolean ctrlPressed = false;

    @FXML
    public void initialize() {
        visibleModel.registerListener(this);
        currentModelData = null;

        articleEditor.setOnKeyPressed(e -> {
            if (currentModelData == null) return;

            if (e.getCode() == KeyCode.CONTROL) ctrlPressed = true;

            if (e.getCode() == KeyCode.S && ctrlPressed) {
                currentModelData.setArticleData(articleEditor.getHtmlText());
            }
        });

        articleEditor.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.CONTROL) ctrlPressed = false;
        });
    }

    @Override
    public void update(Command c) {
        switch(c) {
            case OPEN_ARTICLE -> loadArticle();
        }
    }

    //Changes the editable data in the HTMLEditor
    private void loadArticle() {
        if (currentModelData != null) {
            currentModelData.setArticleData(articleEditor.getHtmlText());
        }
        currentModelData = (ArticleModel) visibleModel.getCurrentView();
        articleEditor.setHtmlText(currentModelData.getArticleData());
    }
}
