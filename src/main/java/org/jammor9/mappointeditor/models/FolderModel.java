package org.jammor9.mappointeditor.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//A simple container that holds ModelComposites that have actual data
public class FolderModel extends ModelComposite {

    private static final String CLASS_META_KEY = FolderModel.class.getCanonicalName();

    private String folderName;

    public FolderModel(String folderName) {
        super(CLASS_META_KEY);
        this.folderName = folderName;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return getFolderName();
    }

    @Override
    public void setName(String s) {
        setFolderName(s);
    }
}
