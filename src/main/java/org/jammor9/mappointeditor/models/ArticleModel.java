package org.jammor9.mappointeditor.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.web.HTMLEditor;

public class ArticleModel extends ModelComposite{

    private static final String CLASS_META_KEY = ArticleModel.class.getCanonicalName();

    private String articleName;
    private String articleData;

    public ArticleModel() {
        super(CLASS_META_KEY);
        articleData = "";
    }

    public String getName() {
        return this.articleName;
    }

    public String getArticleData() {
        return this.articleData;
    }

    public void setArticleData(String s) {
        this.articleData = s;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void setName(String s) {
        this.articleName = s;
    }
}
