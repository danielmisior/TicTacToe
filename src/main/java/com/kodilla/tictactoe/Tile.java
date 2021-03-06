package com.kodilla.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    private int fieldNumber;
    private Text text = new Text();

    public Tile(GameController gameController) {

        Rectangle border = new Rectangle(200, 200);
        border.setFill(Color.DARKGREEN);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font(100));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);


        setOnMouseClicked(e -> {

            if(gameController.playerIsOpponent) {
                Tile source = (Tile) e.getSource();
                gameController.makePlayersMove(source);

            } else {
                Tile source = (Tile) e.getSource();
                gameController.makePlayersMove(source);

                if(gameController.computerTurn) {
                gameController.computerMove();
                }
            }
        });
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Text getText() {
        return text;
    }
}
