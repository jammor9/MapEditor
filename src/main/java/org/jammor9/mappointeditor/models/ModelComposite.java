package org.jammor9.mappointeditor.models;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.UUID;

public abstract class ModelComposite {
    private ArrayList<ModelComposite> children;
    private final String CLASS_META_KEY;
    private UUID parentIdentifier;
    private final UUID modelIdentifier;

    public ModelComposite(String CLASS_META_KEY) {
        this.CLASS_META_KEY = CLASS_META_KEY;
        this.children = new ArrayList<>();
        this.modelIdentifier = UUID.randomUUID();
        this.parentIdentifier = null;
    }

    public void add(ModelComposite modelComposite) {
        modelComposite.parentIdentifier = modelIdentifier;
        children.add(modelComposite);
    }

    public void remove(ModelComposite modelComposite) {
        modelComposite.parentIdentifier = null;
        children.remove(modelComposite);
    }

    public void delete(ModelComposite mc, UUID parentID) {
        if (modelIdentifier == parentID) children.remove(mc);
        else for (ModelComposite child : children) mc.delete(child, parentID);
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

    public UUID getModelIdentifier() {
        return modelIdentifier;
    }

    public UUID getParentIdentifier() {
        return parentIdentifier;
    }

    @Override
    public abstract String toString();

    public abstract void setName(String s);
}
