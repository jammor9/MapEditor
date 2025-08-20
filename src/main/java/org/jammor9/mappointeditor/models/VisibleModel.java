package org.jammor9.mappointeditor.models;

import java.util.ArrayList;

public class VisibleModel {
    private ModelComposite currentView;
    private static VisibleModel singleton = null;
    private ArrayList<ModelListener> listeners;
    private ModelComposite projectHeaderView;

    private VisibleModel() {
        this.currentView = null;
        this.listeners = new ArrayList<>();
    }

    public static VisibleModel getInstance() {
        if (singleton == null) singleton = new VisibleModel();
        return singleton;
    }

    public void setCurrentView(ModelComposite newView) {
        this.currentView = newView;
        if (newView.getClass() == MapModel.class) for (ModelListener l : listeners) l.update(Command.OPEN_MAP);
        else if (newView.getClass() == ArticleModel.class) for (ModelListener l : listeners) l.update(Command.OPEN_ARTICLE);
    }

    public ModelComposite getCurrentView() {
        return this.currentView;
    }

    public ModelComposite getProjectHeaderView() {
        return this.projectHeaderView;
    }

    public void registerListener(ModelListener modelListener) {
        listeners.add(modelListener);
    }

    public void removeListener(ModelListener modelListener) {
        listeners.remove(modelListener);
    }

    public void add(ModelComposite modelComposite) {
        currentView.add(modelComposite);
        for (ModelListener l : listeners) l.update(Command.ADD_TREE_CHILD);
    }

    public void newProject(FolderModel folderModel) {
        this.currentView = folderModel;
        this.projectHeaderView = folderModel;
        for (ModelListener l : listeners) l.update(Command.NEW_PROJECT);
    }

    public void addMap(MapModel mapModel) {
        currentView = mapModel;
        for (ModelListener l : listeners) l.update(Command.NEW_MAP);
    }
}
