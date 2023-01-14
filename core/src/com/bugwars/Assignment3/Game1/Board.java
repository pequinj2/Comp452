package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.utils.Array;
import java.util.Arrays;

/**
 * This class is the game board that will determine available moves, piece scores and if a there
 * is a winning combination.
 */
public class Board {

    private Move[][] board = new Move[6][7]; // r, c
    private Array<Move> moves = new Array<>();
    private boolean gameOver = false;
    private Move[] winningWindow;

    public Board (){

    }

    /**
     * Moves that can be made from the board position
     * @return list of move objects
     */
    public Array<Move> getMoves(){
        moves = new Array<>();
       // printBoard();
        int row = 6;
        int col = 7;
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                if(board[i][j] == null ){
                    if(i!=0 ){
                        if(board[i-1][j] != null ){
                            moves.add(new Move(i,j));
                        }
                    }
                    if(i==0){
                        moves.add(new Move(i,j));
                    }
                    if(moves.size == 7){
                        return moves;
                    }
                }
            }
        }
        if(moves.size == 0){
            gameOver ^= gameOver;
            System.out.println("Game is over its a tie!");
        }
        return moves;
    }

    /**
     * Update the board with the position the player wants to make
     */
    public Board makeMove(Move move, int currentPlayer){
        // Copy move to the playing board
        move.updateMove(currentPlayer);
        board[move.getX()][move.getY()] = new Move(move);

        return this;
    }

    /**
     * Update the board with the position the player wants to make
     */
    public void deleteMove(Move move){
        // Copy move to the playing board
        //move.reverseMove();
        board[move.getX()][move.getY()] = null;
        //move.updateMove(currentPlayer);


        //return this;
    }



    /**
     * @return The current player making a move
     */
    private int currentPlayer(){

        return 12;

    }

    public boolean isGameOver(){
        return gameOver;
    }

    public Move getMove(int i) {
        for(Move m: moves){

            if(m.getY() == i){
                //System.out.println("returning this move: "+ m.getX() + ", "+ m.getY());
                return m;
            }
        }
        return new Move(10,10);
    }

    public Move[][] getBoard(){
        return board;
    }

    public Move checkBoard(int x, int y){
        Move getMove = board[x][y];
        return getMove;

    }

    public void printBoard(){
        for(Move[] row: board){
            System.out.println();
            for(Move m: row){
                if(m != null){
                    System.out.print("  ,  " +m.getPlayerID());
                }
                else{
                    System.out.print("," +m);
                }

            }
        }
        System.out.println("\nAvailable moves ");
        for(Move m: moves){
            System.out.print(" , " + m.getX() + " " + m.getY());
        }
    }

    public boolean checkWinner(int playerID){

        int windowLength = 4;
        boolean winnerFound = false;

        // Check horizontal win
        for(Move[] row: board){
            for(int i=0; i< row.length-3; i++){
                Move[] window = Arrays.copyOfRange(row,i, windowLength+i);
                winnerFound = windowWinner(window, playerID);
                if(winnerFound){
                    return true;
                }
            }
        }

        // Check vertical win

        for(int g=0; g<7; g++) { // 7 columns we need to go through
            Move[] column = new Move[6]; // column will have 6 row values
            // Build the first column to scan
            for (int j = 0; j < 6; j++) { // starting row
                Move move = board[j][g];
                if (move != null) {
                    column[j] = move;
                } else {
                    column[j] = null; // arbitrary number
                }
            }
            // Scan first column
            for (int i = 0; i < 6-3; i++) { // column
                Move[] window = Arrays.copyOfRange(column, i, windowLength+i);
                winnerFound = windowWinner(window, playerID);
                if(winnerFound){
                    return true;
                }
            }
        }
        // Check + diagonal win

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                Move[] window = new Move[4];
                for(int index=0; index<4; index++) {
                    window[index] = board[i + index][j + index];
                    //System.out.println("Diagonal positive "+ (i + index)+ " , " +(j + index));
                }
                winnerFound = windowWinner(window, playerID);
                if(winnerFound){
                    return true;
                }

            }
        }

        // Check - diagonal win

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                Move[] window = new Move[4];
                for(int index=0; index<4; index++) {
                    window[index] = board[i+3 - index][j + index];
                    //System.out.println("Diagonal negative "+ (i+3 - index)+ " , " +(j + index));
                }
                winnerFound = windowWinner(window, playerID);
                if(winnerFound){
                    return true;
                }

            }
        }

        return winnerFound;
    }

    /**
     * https://www.youtube.com/watch?v=MMLtza3CZFM&t=4738s
     * Tutorial on building a heuristic for connect 4
     * @param player
     * @return
     */
    public int evaluateBoard(int player){

        int windowLength = 4;
        int score = 0;
        int playerID = player;

        // Center preference
        Move[] centerColumn = new Move[6]; // column will have 6 row values
        // Build the first column to scan
        for (int j = 0; j < 6; j++) { // starting row
            Move move = board[j][3];
            if (move != null) {
                centerColumn[j] = move;
            } else {
                centerColumn[j] = null; // arbitrary number
            }
        }
        int countCenter = 0;
        for(Move move: centerColumn) {
            if (move != null) {
                int currentID = move.getPlayerID();
                if (playerID == currentID) {
                    countCenter++;
                }
            }
        }

        score += (countCenter * 3);

        // Horizontal
        for(Move[] row: board){
            for(int i=0; i< row.length-3; i++){
                Move[] window = Arrays.copyOfRange(row,i, windowLength+i);
                score += windowCount(window, playerID);

            }
        }

        // Vertical
        for(int g=0; g<7; g++) { // 7 columns we need to go through
            Move[] column = new Move[6]; // column will have 6 row values
            // Build the first column to scan
            for (int j = 0; j < 6; j++) { // starting row
                Move move = board[j][g];
                if (move != null) {
                    column[j] = move;
                } else {
                    column[j] = null; // arbitrary number
                }
            }
            // Scan first column
            for (int i = 0; i < 6-3; i++) { // column
                Move[] window = Arrays.copyOfRange(column, i, windowLength+i);
                score += windowCount(window, playerID);
            }
        }


        // Diagonal Positive
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                Move[] window = new Move[4];
                for(int index=0; index<4; index++) {
                    window[index] = board[i + index][j + index];
                    //System.out.println("Diagonal positive "+ (i + index)+ " , " +(j + index));
                }
                score += windowCount(window, playerID);

            }
        }

        //Diagonal negative
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                Move[] window = new Move[4];
                for(int index=0; index<4; index++) {
                    window[index] = board[i+3 - index][j + index];
                    //System.out.println("Diagonal negative "+ (i+3 - index)+ " , " +(j + index));
                }
                score += windowCount(window, playerID);

            }
        }

        return score;

    }

    public int windowCount(Move[] window, int playerID){
        int count =0;
        int nulls =0;
        int score = 0;
        int enemyScore = 0;
        int enemyID = playerID ^ 1;

        for(Move move: window){

            if(move != null){
                int currentID = move.getPlayerID();
                if (playerID == currentID) {
                    count++;
                }
                else {
                    enemyScore++;
                }
            }
            else{
                nulls++;
            }
        }

        if(count==4){
            System.out.println("\n4 squares found!");
            score += 100;
        }
        else if(count==3 && nulls==1){
            System.out.println("\n3 squares found!");
            score += 5;
        }
        else if(count==2 && nulls==2){
            System.out.println("\n2 squares found!");
            score += 2;
        }


        if(enemyScore == 3 && nulls==1){
            System.out.println("\nEnemy could win! squares found!");
            score -= 4;
        }


        return score;
    }

    public boolean windowWinner(Move[] window, int playerID){
        int count = 0;

        for(Move move: window){
            if(move !=null){
                if(move.getPlayerID() != playerID){
                    return false;
                }
            }
            else{ // Null space found so no winner
                return false;
            }
        }
        winningWindow = window;
        gameOver = true;
        return true;
    }

    public Move[] getWinningWindow(){
        return winningWindow;
    }
}