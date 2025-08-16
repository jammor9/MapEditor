package org.jammor9.mappointeditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jammor9.mappointeditor.models.MapModel;
import org.jammor9.mappointeditor.models.VisibleModel;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String WINDOW_NAME = "Map Editor";

    //Base Model
    private VisibleModel visibleModel = VisibleModel.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm());
        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("img/marker.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}