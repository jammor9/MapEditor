package org.jammor9.mappointeditor.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.web.HTMLEditor;

public class ArticleModel extends ModelComposite{
    private StringProperty articleName;
    private StringProperty articleData;

    public ArticleModel() {
        articleData = new SimpleStringProperty("");
    }

    public StringProperty getArticleNameProperty() {
        return this.articleName;
    }

    public String getName() {
        return this.articleName.get();
    }

    public StringProperty getArticleDataProperty() {
        return this.articleData;
    }

    public String getArticleData() {
        return this.articleData.get();
    }

    public void setArticleData(String s) {
        this.articleData = new SimpleStringProperty(s);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void setName(String s) {
        this.articleName = new SimpleStringProperty(s);
    }
}
