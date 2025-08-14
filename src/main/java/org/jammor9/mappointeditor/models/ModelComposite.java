package org.jammor9.mappointeditor.models;

import java.util.ArrayList;

public abstract class ModelComposite {
    private ArrayList<ModelComposite> children;

    public ModelComposite() {
        children = new ArrayList<>();
    }

    public void add(ModelComposite modelComposite) {
        children.add(modelComposite);
    }

    public void remove(ModelComposite modelComposite) {
        children.remove(modelComposite);
    }

    public ArrayList<ModelComposite> getChildren() {
        return children;
    }

    public abstract void execute();

    @Override
    public abstract String toString();
}
