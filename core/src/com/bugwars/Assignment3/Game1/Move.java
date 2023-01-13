package com.bugwars.Assignment3.Game1;

/**
 * May not need player ID
 */
public class Move {

    private int x, y;
    private int playerID;
    private int score;

    public Move(int playerID, int x, int y){
        this.playerID = playerID;
        this.x = x;
        this.y = y;

    }

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

    public void reverseMove(){
        this.playerID = 2;
        x--;
    }



    /**
     * Return the row number (starts at 0)
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Return the column number (starts at 0)
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Return the column number (starts at 0)
     * @return
     */
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
