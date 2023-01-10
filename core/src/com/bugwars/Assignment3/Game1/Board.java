package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.utils.Array;

public class Board {

    private Move[][] board = new Move[6][7]; // r, c
    private Array<Move> moves = new Array<>();

    public Board (){

        for(int i = 0; i<7; i++){
            moves.add(new Move(0,i));
        }

    }

    /**
     * Moves that can be made from the board position
     * @return list of move objects
     */
    public Array<Move> getMoves(){
        return moves;
    }

    /**
     * Update the board with the position the player wants to make
     */
    public Board makeMove(Move move, int currentPlayer){
        move.updateMove(currentPlayer);
        // Copy move to the playing board
        board[move.getX()][move.getY()] = new Move(move);
        return this;
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

    public boolean isGameOver(){
        return false;
    }

    public Move getMove(int i) {

        for(Move m: moves){
            System.out.print(" , " + m.getX() + " ID: " + m.getPlayerID() + " , ");
        }
        return moves.get(i);
    }
}
