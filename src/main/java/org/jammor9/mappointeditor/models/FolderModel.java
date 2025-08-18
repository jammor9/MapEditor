package org.jammor9.mappointeditor.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//A simple container that holds ModelComposites that have actual data
public class FolderModel extends ModelComposite {

    private StringProperty folderName;

    public FolderModel(String folderName) {
        this.folderName = new SimpleStringProperty(folderName);
    }

    public String getFolderName() {
        return folderName.get();
    }

    public StringProperty getFolderNameProperty() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = new SimpleStringProperty(folderName);
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
