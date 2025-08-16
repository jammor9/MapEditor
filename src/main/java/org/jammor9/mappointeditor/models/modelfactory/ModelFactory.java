package org.jammor9.mappointeditor.models.modelfactory;

import org.jammor9.mappointeditor.models.ModelComposite;

public interface ModelFactory {
    public ModelComposite createModel(String name);
}
