package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.BLACK);

        Rectangle r = new Rectangle(25,25,250, 250);
        r.setFill(Color.BLUE);

        root.getChildren().add(r);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}