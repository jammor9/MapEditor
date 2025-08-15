package org.jammor9.mappointeditor.models;

import com.sun.source.tree.Tree;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public abstract class ModelComposite {
    private ArrayList<ModelComposite> children;
    private ModelComposite parent;

    public ModelComposite() {
        children = new ArrayList<>();
    }

    public void add(ModelComposite modelComposite) {
        children.add(modelComposite);
        modelComposite.parent = this;
    }

    public void remove(ModelComposite modelComposite) {
        children.remove(modelComposite);
        modelComposite.parent = null;
    }

    public ArrayList<ModelComposite> getChildren() {
        return children;
    }

    //Returns a tree structure of the entire composite model to be read via TreeView
    public TreeItem<ModelComposite> getTree() {
        TreeItem<ModelComposite> root = new TreeItem<>(this);
        for (ModelComposite mc : children) root.getChildren().addAll(mc.getTree());
        return root;
    }

    public ModelComposite getParent() {
        return parent;
    }

    public abstract void execute();

    @Override
    public abstract String toString();
}
