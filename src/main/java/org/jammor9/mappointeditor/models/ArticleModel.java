package org.jammor9.mappointeditor.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.web.HTMLEditor;

public class ArticleModel extends ModelComposite{
    private StringProperty articleName;
    private HTMLEditor articleEditor;


    public StringProperty getArticleNameProperty() {
        return this.articleName;
    }

    public String getName() {
        return this.articleName.get();
    }

    public HTMLEditor getArticleEditor() {
        return this.articleEditor;
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
