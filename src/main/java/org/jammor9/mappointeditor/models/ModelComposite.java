package org.jammor9.mappointeditor.models;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public abstract class ModelComposite {
    private ArrayList<ModelComposite> children;
    private final String CLASS_META_KEY;

    public ModelComposite(String CLASS_META_KEY) {
        this.CLASS_META_KEY = CLASS_META_KEY;
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

    //Returns a tree structure of the entire composite model to be read via TreeView
    public TreeItem<ModelComposite> getTree() {
        TreeItem<ModelComposite> root = new TreeItem<>(this);
        for (ModelComposite mc : root.getValue().getChildren()) root.getChildren().addAll(mc.getTree());
        return root;
    }

    public String getClassMetaKey() {
        return CLASS_META_KEY;
    }

    @Override
    public abstract String toString();

    public abstract void setName(String s);
}
