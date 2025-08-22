package org.jammor9.mappointeditor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import org.jammor9.mappointeditor.Utils.AbstractSerializer;
import org.jammor9.mappointeditor.Utils.Utils;
import org.jammor9.mappointeditor.models.*;

import java.io.*;

public class SaveAndLoad {

    private static final String SAVE_DIRECTORY_NAME = "saves/";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ModelComposite.class, new AbstractSerializer<ModelComposite>()).create();

    public static void saveFile() {
        ModelComposite root = VisibleModel.getInstance().getProjectHeaderView();
        Popup popup = Utils.getQuickInputPopup("Save Name");
        TextField t = (TextField) popup.getContent().getFirst();

        t.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if(!t.getText().isEmpty()) {
                    String path = SAVE_DIRECTORY_NAME + t.getText() + ".json";
                    File file = new File(path);
                    if(!file.exists()) {
                        try {
                            file.createNewFile();
                            FileWriter w = new FileWriter(path);
                            gson.toJson(root, w);
                            w.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    popup.hide();
                }
            }
        });

        popup.show(Application.getStage());
    }

    public static void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(SAVE_DIRECTORY_NAME));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );

        File selectedFile = fileChooser.showOpenDialog(Application.getStage());
        if (selectedFile == null) return;
        else {
            try {
                FileReader r = new FileReader(selectedFile.toURI().getPath());
                FolderModel root = gson.fromJson(r, FolderModel.class);
                VisibleModel.getInstance().newProject(root);
                VisibleModel.getInstance().setCurrentView(root);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
