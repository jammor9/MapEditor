package org.jammor9.mappointeditor.models;

import java.util.ArrayList;

public class VisibleModel {
    private ModelComposite currentView;
    private static VisibleModel singleton = null;
    private ArrayList<MapListener> listeners;

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
        if (newView.getClass() == MapModel.class) for (MapListener l : listeners) l.update(Command.NEW_MAP);
        else if (newView.getClass() == ArticleModel.class) for (MapListener l : listeners) l.update(Command.OPEN_ARTICLE);
        System.out.println("Test");
    }

    public ModelComposite getCurrentView() {
        return this.currentView;
    }

    public void registerListener(MapListener mapListener) {
        listeners.add(mapListener);
    }

    public void removeListener(MapListener mapListener) {
        listeners.remove(mapListener);
    }

    public void add(ModelComposite modelComposite) {
        currentView.add(modelComposite);
        for (MapListener l : listeners) l.update(Command.ADD_TREE_CHILD);
    }
}
