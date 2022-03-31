package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToeApp extends Application {

    private static GridPane root;
    private static GameController gameController;

    public static Parent createBoard() {

        root = new GridPane();
        gameController = new GameController(root);
        root.setPrefSize(600, 600);
        int tileCounter = 0;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Tile tile = new Tile(gameController);

                tile.setPrefSize(200, 200);
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
                tileCounter++;
                tile.fieldNumber = tileCounter;

                root.getChildren().add(tile);
            }
        }
        return root;
    }

    public static Scene createAppScene(Stage stage) {

        Scene appScene = new Scene(createBoard());
        Scene sceneWithButtons;
        FlowPane pane = new FlowPane();
        TextField textField = new TextField();

        Button b1 = new Button("How many rounds would you like to play?");
        Button b2 = new Button("Player vs Player");
        Button b3 = new Button("Player vs Computer");
        pane.getChildren().addAll(b2, b3);

        VBox layout = new VBox(10);
        Insets insets = new Insets(40, 40, 40, 40);
        layout.setPadding(insets);
        layout.getChildren().addAll(textField, b1, pane);

        sceneWithButtons = new Scene(layout, 400, 400);

        b1.setOnAction(e -> {
            gameController.requestedRound = Integer.valueOf(textField.getText());
           /* System.out.println(textField.getText());
            stage.close();
            stage.setScene(appScene);
            stage.show(); */
        });
        b2.setOnAction(e -> {
            gameController.requestedRound = Integer.valueOf(textField.getText());
            System.out.println(textField.getText());
            stage.close();
            stage.setScene(appScene);
            stage.show();
            gameController.playerIsOpponent = true;
        });
        b3.setOnAction(e -> {
            gameController.requestedRound = Integer.valueOf(textField.getText());
            stage.close();
            stage.setScene(appScene);
            stage.show();
            gameController.playerIsOpponent = false;
        });
        return sceneWithButtons;
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(createAppScene(stage));
        stage.setTitle("Tic Tac Toe");
        gameController.appStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}