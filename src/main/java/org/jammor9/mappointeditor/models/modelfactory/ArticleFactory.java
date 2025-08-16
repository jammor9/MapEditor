package org.jammor9.mappointeditor.models.modelfactory;

import org.jammor9.mappointeditor.models.ArticleModel;
import org.jammor9.mappointeditor.models.ModelComposite;

public class ArticleFactory implements ModelFactory{

    private static final String FACTORY_TYPE = "ARTICLE";

    @Override
    public ModelComposite createModel(String name) {
        ArticleModel article = new ArticleModel();
        article.setName(name);
        return article;
    }

    @Override
    public String toString() {
        return FACTORY_TYPE;
    }
}
