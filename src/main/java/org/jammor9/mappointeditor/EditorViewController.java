package org.jammor9.mappointeditor;

import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.jammor9.mappointeditor.models.ArticleModel;
import org.jammor9.mappointeditor.models.Command;
import org.jammor9.mappointeditor.models.MapListener;
import org.jammor9.mappointeditor.models.VisibleModel;

public class EditorViewController implements MapListener {
    @FXML public HTMLEditor articleEditor;
    VisibleModel visibleModel = VisibleModel.getInstance();
    ArticleModel currentModelData;

    @FXML
    public void initialize() {
        visibleModel.registerListener(this);
        currentModelData = null;
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
