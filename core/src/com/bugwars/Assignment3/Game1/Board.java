package com.bugwars.Assignment3.Game1;

public class Board {

    private Move[][] board = new Move[6][7]; // r, c
    private Move[] moves;

    public Board (){

    }

    /**
     * Moves that can be made from the board position
     * @return list of move objects
     */
    private Move[] getMoves(){

        return new Move[4];
    }

    /**
     * Update the board with the position the player wants to make
     */
    private void makeMove(){

    }

    /**
     * Is the Static Evaluation Function
     * @return The score for the current position for the current player
     */
    private float evaluate(){

        return 0;

    }

    /**
     * @return The current player making a move
     */
    private int currentPlayer(){

        return 12;

    }

    private boolean isGameOver(){
        return false;
    }
}
