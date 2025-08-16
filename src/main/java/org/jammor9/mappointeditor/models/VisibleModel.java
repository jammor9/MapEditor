package org.jammor9.mappointeditor.models;

public class VisibleModel {
    private ModelComposite currentView;
    private static VisibleModel singleton = null;

    private VisibleModel() {
        this.currentView = null;
    }

    public static VisibleModel getInstance() {
        if (singleton == null) singleton = new VisibleModel();
        return singleton;
    }

    public void setCurrentView(ModelComposite newView) {
        this.currentView = newView;
    }
}
