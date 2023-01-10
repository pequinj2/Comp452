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
        for(Move m: moves){
            System.out.println(m.getX() + "   " + m.getY());
        }
        return moves;
    }

    /**
     * Update the board with the position the player wants to make
     */
    public Board makeMove(Move move, int currentPlayer){

        // Copy move to the playing board
        System.out.println(move.getX() + " x and y " + move.getY());
        board[move.getX()][move.getY()] = new Move(move);
        move.updateMove(currentPlayer);

        return this;
    }

    /**
     * Is the Static Evaluation Function
     * @return The score for the current position for the current player
     */
    public float evaluate(int currentPlayer){
        float score = 0;
        int playerID = currentPlayer;
        int multiplyer = 0;
        for(Move[] row: board){
            for(Move col: row) {

                if(col == null){
                    score += 1;
                }
                else if(col.getPlayerID() == playerID){
                    multiplyer+=1;
                    score = 10 * multiplyer;
                }
                else{
                    multiplyer = -1;
                    score -=10;
                }
            }
            System.out.println(score);
        }
        return score;

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


        System.out.println("break");
        return moves.get(i);
    }

    public Move[][] getBoard(){
        return board;
    }

    public Move checkBoard(int x, int y){
        Move getMove = board[x][y];
        return getMove;

    }
}
