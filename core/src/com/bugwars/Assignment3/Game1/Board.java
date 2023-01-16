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
     * Valid moves that can be made from the game board's current instance
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
     * Delete move from the game board
     */
    public void deleteMove(Move move){
        board[move.getX()][move.getY()] = null;
    }

    /**
     * Return true or false if the game is over - whether a winner is found or game board is full
     * @return
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Return a Move object at the desired column position - there is only 1 valid move in each
     * column which is why we're just looking for that
     * @param i column value we're looking for
     * @return Move at the column position or invalid move
     */
    public Move getMove(int i) {
        for(Move m: moves){
            if(m.getY() == i){
                return m;
            }
        }
        return new Move(10,10);
    }

    /**
     * Get the current instance of the game board
     * @return
     */
    public Move[][] getBoard(){
        return board;
    }

    /**
     * Helper function used for debugging to get the output of current board instance and current
     * available moves
     */
    public void printBoard(){
        for(Move[] row: board){
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

    /**
     * Similar to 'evaluateBoard', this function will go through the board and see if there is a
     * 'window' of 4 similar pieces together and return true if found. Calls 'windowWinner' function
     * to evaluate if there are 4 similar pieces together.
     * @param playerID ID of winner we're looking for
     * @return True if winner is found - false otherwise
     */
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
     * How to Program a Connect 4 AI (implementing the minimax algorithm) - Keith Galli
     * The above tutorial was used to build the heuristic for the game.
     * This function will take in player id it's evaluating and then go through the entire game board
     * and tally up the points for the pieces it finds in that board instance. 'windowCount' function
     * is used to help tally up these scores
     * @param player Player ID function is evaluating
     * @return Return board score
     */
    public int evaluateBoard(int player){
        // Length of the array 'window' being used to find sections of the game board
        int windowLength = 4;
        // Initial score of the board
        int score = 0;
        // ID of the player the function is evaluating
        int playerID = player;

        // Center preference
        // Because the center column of the board is shown to yield a greater chance of
        // winning the game, any pieces found here are given a point boost.
        // Initialize array to store center column - column will have 6 row values
        Move[] centerColumn = new Move[6];
        // Build column to scan
        for (int j = 0; j < 6; j++) {
            // j = row , 3 = center column
            Move move = board[j][3];
            centerColumn[j] = move;
        }
        // Counter for the evaluating player's game pieces
        int countCenter = 0;
        // Go through column
        for(Move move: centerColumn) {
            // Make sure there is a move there
            if (move != null) {
                int currentID = move.getPlayerID();
                // Make sure the player ID is the evaluating player's then increment counter
                if (playerID == currentID) {
                    countCenter++;
                }
            }
        }
        // Add counter booster the the score
        score += (countCenter * 3);

        // Horizontal
        // Get a board row
        for(Move[] row: board){
            // Increment the window array starting from 0 to column 3
            for(int i=0; i< row.length-3; i++){
                // Slice the row for the window
                Move[] window = Arrays.copyOfRange(row,i, windowLength+i);
                // Evaluate window and get score
                score += windowCount(window, playerID);

            }
        }

        // Vertical
        // Generate the boards columns
        for(int g=0; g<7; g++) { // 7 columns we need to go through
            Move[] column = new Move[6]; // column will have 6 row values
            // Build the first column to scan
            for (int j = 0; j < 6; j++) { // starting row
                Move move = board[j][g];
                column[j] = move;
            }
            // Scan first column with window array
            for (int i = 0; i < 3; i++) { // column
                Move[] window = Arrays.copyOfRange(column, i, windowLength+i);
                // Evaluate window and get score
                score += windowCount(window, playerID);
            }
        }


        // Diagonal Positive - bottom left to top right
        // Row position - 0 to 2
        for(int i=0; i<3; i++){
            // Column position = 0 to 3
            for(int j=0; j<4; j++){
                // Reset window array
                Move[] window = new Move[4];
                // Build the window
                for(int index=0; index<4; index++) {
                    window[index] = board[i + index][j + index];
                }
                // Evaluate window and get score
                score += windowCount(window, playerID);

            }
        }

        //Diagonal negative - top left to bottom right
        // Row position - 0 to 2
        for(int i=0; i<3; i++){
            // Column position = 0 to 3
            for(int j=0; j<4; j++){
                // Reset window array
                Move[] window = new Move[4];
                // Build the window
                for(int index=0; index<4; index++) {
                    window[index] = board[i+3 - index][j + index];
                }
                // Evaluate window and get score
                score += windowCount(window, playerID);

            }
        }

        return score;

    }

    /**
     * Takes in a 'window' array and the player ID to be evaluated and returns a score based on
     * what criteria was found in the window
     * @param window Move array
     * @param playerID ID to be evaluated
     * @return score
     */
    public int windowCount(Move[] window, int playerID){
        int count =0; // Evaluating player counter
        int nulls =0; // Empty square counter
        int score = 0; // Evaluating player's score
        int enemyScore = 0; // Opponent counter

        for(Move move: window){
            if(move != null){
                int currentID = move.getPlayerID();
                // If move in window is not null then get the Player ID and see if it matches the
                // current evaluating player, if so, increment 'count' else increment enemy's score count
                if (playerID == currentID) {
                    count++;
                }
                else {
                    enemyScore++;
                }
            }
            // No Move object, therefore, empty square - increment empty square counter
            else{
                nulls++;
            }
        }
        // Tally up the score depending on the state of the window array found above
        if(count==4){ // Winning move found
            score += 100;
        }
        else if(count==3 && nulls==1){ // 3 player spaces found and 1 empty found
            score += 5;
        }
        else if(count==2 && nulls==2){ // 2 player spaces found and 2 empty found
            score += 1;
        }


        if(enemyScore==3 && nulls==1){ // 3 enemy spaces found and 1 empty found
            score -= 4;
        }
        else if(enemyScore==2 && nulls==2){ // 2 enemy spaces found and 2 empty found
            score -= 1;
        }

        // Return overall score found of window
        return score;
    }

    /**
     * Scan the 'window' array if a winner has been found
     * @param window
     * @param playerID
     * @return True or false if a winner was found
     */
    public boolean windowWinner(Move[] window, int playerID){
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

    public void setGameOver(){
        gameOver = true;
    }
}