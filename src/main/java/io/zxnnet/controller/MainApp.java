package io.zxnnet.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Main.fxml")));
        Scene scene = new Scene(root, 1366, 768);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("application.css")).toExternalForm());

        primaryStage.setTitle("GitU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
