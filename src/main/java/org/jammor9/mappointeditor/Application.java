package org.jammor9.mappointeditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String WINDOW_NAME = "Map Editor";
    private static Stage staticStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm());
        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("img/marker.png"))));
        staticStage = stage;
        stage.show();
    }

    public static Stage getStage() {
        return staticStage;
    }

    public static void main(String[] args) {
        launch();
    }
}