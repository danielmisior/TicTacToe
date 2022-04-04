package com.kodilla.tictactoe;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class GameController {

    private GridPane root;
    private Set<Integer> markedTilesX = new HashSet<>();
    private Set<Integer> markedTilesO = new HashSet<>();
    private List<HashSet<Integer>> combinationsOfWinning = new ArrayList<>();
    private String x = "X";
    private String o = "O";
    private String message = "";
    private Integer player1WinsCounter = 0;
    private Integer player2WinsCounter = 0;
    private Integer drawsCounter = 0;
    private Integer roundsCounter = 1;
    static Stage appStage;
    Integer requestedRound;
    boolean playerIsOpponent;
    boolean xPlayer = true;
    boolean computerTurn;

    public GameController(GridPane root) {
        this.root = root;

        HashSet<Integer> win1 = new HashSet<>();
        win1.add(1);
        win1.add(2);
        win1.add(3);
        combinationsOfWinning.add(win1);

        HashSet<Integer> win2 = new HashSet<>();
        win2.add(4);
        win2.add(5);
        win2.add(6);
        combinationsOfWinning.add(win2);

        HashSet<Integer> win3 = new HashSet<>();
        win3.add(7);
        win3.add(8);
        win3.add(9);
        combinationsOfWinning.add(win3);

        HashSet<Integer> win4 = new HashSet<>();
        win4.add(1);
        win4.add(5);
        win4.add(9);
        combinationsOfWinning.add(win4);

        HashSet<Integer> win5 = new HashSet<>();
        win5.add(3);
        win5.add(5);
        win5.add(7);
        combinationsOfWinning.add(win5);

        HashSet<Integer> win6 = new HashSet<>();
        win6.add(1);
        win6.add(4);
        win6.add(7);
        combinationsOfWinning.add(win6);

        HashSet<Integer> win7 = new HashSet<>();
        win7.add(2);
        win7.add(5);
        win7.add(8);
        combinationsOfWinning.add(win7);

        HashSet<Integer> win8 = new HashSet<>();
        win8.add(3);
        win8.add(6);
        win8.add(9);
        combinationsOfWinning.add(win8);
    }

    public boolean ifFieldWasUsed (Tile tile) {
        boolean result = markedTilesX.contains(tile.getFieldNumber()) || markedTilesO.contains(tile.getFieldNumber());
        return result;
    }

    public void makePlayersMove(Tile tile) {

        if(playerIsOpponent) {
            if (!ifFieldWasUsed(tile)) {
                if (xPlayer) {
                tile.getText().setText(x);
                markedTilesX.add(tile.getFieldNumber());
                checkResult(markedTilesX);
                xPlayer = false;
                }
                else {
                    tile.getText().setText(o);
                    markedTilesO.add(tile.getFieldNumber());
                    checkResult(markedTilesO);
                    xPlayer = true;
                }
            }
        } else {
            if(!ifFieldWasUsed(tile)){
                tile.getText().setText(x);
                markedTilesX.add(tile.getFieldNumber());
                checkResult(markedTilesX);
                computerTurn = true;
            }
        }
    }

    public void computerMove() {
        List<Tile> tiles = root.getChildren().stream()
                .filter(n -> n instanceof Tile)
                .map(n -> ((Tile) n))
                .filter(tile -> tile.getText().getText().equals(""))
                .collect(Collectors.toList());

        Random random = new Random();
        int indexOfComputerMove = random.nextInt(tiles.size());

        Tile tile = tiles.get(indexOfComputerMove);
        tile.getText().setText(o);
        markedTilesO.add(tile.getFieldNumber());
        checkResult(markedTilesO);
        computerTurn = false;
    }

    public boolean draw() {
        if((markedTilesX.size() == 4 && markedTilesO.size() == 5)
                || (markedTilesX.size() == 5 && markedTilesO.size() == 4)) {
            return true;
        }
        return false;
    }

    public boolean player1IsWinner() {

        boolean result = combinationsOfWinning.stream()
                .anyMatch(combination -> markedTilesX.containsAll(combination));
        return result;
    }

    public boolean player2IsWinner() {
        boolean result = combinationsOfWinning.stream()
                .anyMatch(combination -> markedTilesO.containsAll(combination));
        return result;
    }

    public void checkResult(Set<Integer> hashSet) {
        if(draw() || player1IsWinner() || player2IsWinner()) {
            endOfRound();
        }
    }

    public void endOfRound() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of Round");
        alert.setHeaderText("Round #" + roundsCounter);

        if(player1IsWinner()) {
            if(playerIsOpponent) {
                roundsCounter++;
                player1WinsCounter++;
                message = "X won this round. Total X wins: " + player1WinsCounter;
            }
            else{
                roundsCounter++;
                player1WinsCounter++;
                message = "Player won this round. Total Player wins: " + player1WinsCounter;
            }
        }
        else if(player2IsWinner()) {
            if(playerIsOpponent) {
                roundsCounter++;
                player2WinsCounter++;
                message = "O won this round. Total O wins: " + player2WinsCounter;
            }
            else  {
                roundsCounter++;
                player2WinsCounter++;
                message = "Computer won this round. Total Computer wins: " + player2WinsCounter;
            }
        }
        else {
            roundsCounter++;
            drawsCounter++;
            message = "Draw! Total draws: " + drawsCounter;
        }
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            this.markedTilesX = new HashSet<>();
            this.markedTilesO = new HashSet<>();
            for(int i = 0; i < root.getChildren().size(); i++) {
                ((Tile) root.getChildren().get(i)).getText().setText("");
            }
        }
        else {
            appStage.close();
        }
        if (requestedRound <= (player1WinsCounter + player2WinsCounter + drawsCounter)) {
                endOfGame();
            }
    }

    public void endOfGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of Game");
        String message = "";

        if(playerIsOpponent) {
            if(player1WinsCounter > player2WinsCounter) {
                alert.setHeaderText("X won!");
                message = "Would you like to play again?";
            }
            else if (player1WinsCounter < player2WinsCounter){
                alert.setHeaderText("O won!");
                message = "Would you like to play again?";
            }
            else {
                alert.setHeaderText("Draw!");
                message = "Would you like to play again?";
            }
        }
            else {
            if(player1WinsCounter > player2WinsCounter) {
                alert.setHeaderText("Victory is Yours!");
                message = "Would you like to play again?";
            }
            else if(player1WinsCounter < player2WinsCounter) {
                alert.setHeaderText("Ops! Computer won.");
                message = "Would you like to play again?";
            }
            else {
                alert.setHeaderText("Draw!");
                message = "Would you like to play again?";
            }
        }

        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            this.markedTilesX = new HashSet<>();
            this.markedTilesO = new HashSet<>();

            for(int i = 0; i < root.getChildren().size(); i++) {
                ((Tile) root.getChildren().get(i)).getText().setText("");
            }
            appStage.setScene(TicTacToeApp.createAppScene(appStage));
        } else  {
            appStage.close();
        }
    }
}