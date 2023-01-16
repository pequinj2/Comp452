package com.bugwars.Assignment3.Game1;

/**
 * Move class is used for storing information on the current move made on the game board or
 * future moves the AI wishes to make. Keeps track of Row, Column, move Score and the Player ID
 * that made the move.
 */
public class Move {

    private int x, y;
    private int playerID;
    private int score;


    public Move(int x, int y){
        this.x = x;
        this.y = y;

    }

    public Move(Move move) {
        this.playerID = move.getPlayerID();
        this.x = move.getX();
        this.y = move.getY();
    }

    /**
     * Update position with which character has a disk placed
     * @param playerID
     */
    public void updateMove(int playerID){
        this.playerID =playerID;

    }

    /**
     * Return the row number (starts at 0)
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Return the col number (starts at 0)
     * @return
     */
    public int getY() {
        return y;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void updateScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}
