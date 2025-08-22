package org.jammor9.mappointeditor;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

public class Utils {

    public static Popup getQuickInputPopup(String prompt) {
        Popup popup = new Popup();
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        Pane pane = new Pane(textField);
        popup.getContent().add(textField);
        popup.setAutoHide(true);
        return popup;
    }

}
