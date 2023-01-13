package com.bugwars.Assignment3.Game1;

import static java.lang.Math.abs;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

public class Board {

    private Move[][] board = new Move[6][7]; // r, c
    private Array<Move> moves = new Array<>();
    private boolean gameOver = false;

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
     * Is the Static Evaluation Function
     * @return The score for the current position for the current player
     */
    public int evaluate(int currentPlayer){
        int score = 0;
        int enemyScore = 0;
        int scoreCurrent = 0;
        int enemyScoreCurrent = 0;
        int playerID = currentPlayer;
        int enemyID = currentPlayer ^ 1;
        int windowCount = 0;
        int windowCountStart = 0;


        // Horizontal
        for(Move[] moveRow: board){
            windowCount =0;
            windowCountStart =0;
            for(int i=0; i<7; i++){
                Move move = moveRow[i];
                windowCount++;
                //System.out.println("i = : " + windowCount);
                if(windowCount == (windowCountStart+5)){
                    windowCountStart++;
                    scoreCurrent += score;
                    enemyScoreCurrent += enemyScore;
                    score=0;
                    enemyScore=0;
                    if(windowCountStart == 4){
                        scoreCurrent += score;
                        enemyScoreCurrent += enemyScore;
                        break;
                    }
                    i=windowCountStart;
                    windowCount = windowCountStart;
                    //System.out.println("This is i and window start " + i + " " + windowCountStart);
                    continue;
                }else {
                    if (move != null) {
                        System.out.println("\nMove gets a SCORE: "+ move.getX() + " " + move.getY());
                        int currentID = move.getPlayerID();
                        if (playerID == currentID) {
                            score++;
                            enemyScore = 0;
                            if(score == 4){
                                System.out.println("AI will win HORIZONTAL!!!");
                                score = 200;
                            }

                        } else {
                            enemyScore++;
                            score = 0;
                            if(enemyScore == 3){
                                System.out.println("Player will win HORIZONTAL!!!");
                                enemyScore = 100;
                            }
                        }

                    }
                }
            }

        }
        System.out.println("AI score vs Player score HORIZONTAL: "+ scoreCurrent + " " + enemyScoreCurrent);
        // Vertical


        // Get column
       /* for(int g=0; g<7; g++){ // 7 columns we need to go through
            int[] column = new int[6]; // column will have 6 row values
            // Reset window values
            windowCount =0;
            windowCountStart =0;
            score=0;
            enemyScore=0;
            // Build the first column to scan
            for(int j=0; j<6; j++) { // starting row
                Move move = board[j][g];
                if (move != null) {
                    column[j] = move.getPlayerID();
                }else{
                    column[j] = 10; // arbitrary number
                }
            }
            // Scan first column
            for(int i=0; i<6; i++){ // column
                windowCount++;
                if(windowCount == (windowCountStart+5)){
                    windowCountStart++;
                    scoreCurrent += score;
                    enemyScoreCurrent += enemyScore;
                    score=0;
                    enemyScore=0;
                    if(windowCountStart == 3){
                        scoreCurrent += score;
                        enemyScoreCurrent += enemyScore;
                        break;
                    }
                    i=windowCountStart;
                    windowCount = windowCountStart;
                    continue;
                }else {
                    if (playerID == column[i]) {
                        score++;
                        enemyScore =0;
                        if(score == 4){
                            System.out.println("AI will win VERTICAL!!!" + score);
                            score = 200;
                        }
                    }
                    if (enemyID == column[i]) {
                        enemyScore++;
                        score = 0;
                        if(enemyScore == 3){
                            System.out.println("Player will win VERTICAL!!!" + enemyScore);
                            if(column[i+1] == playerID){
                                enemyScore = -100;
                            }
                            else{

                            }

                        }
                    }
                }
            }
        }

        // Positive Diagonal
        int[][][] positiveDiag = new int[][][] {
                {{2,0}, {3,1}, {4,2}, {5,3} },
                {{1,0}, {2,1}, {3,2}, {4,3}, {5,4} },
                {{0,0}, {1,1}, {2,2}, {3,3}, {4,4}, {5,5} },
                {{0,1}, {1,2}, {2,3}, {3,4}, {4,5}, {5,6} },
                {{0,2}, {1,3}, {2,4}, {3,5}, {4,6} },
                {{0,3}, {1,4}, {2,5}, {3,6} };*/
        //};
        /*int[][] positiveDiag1 = new int[][] {{2,0}, {3,1}, {4,2}, {5,3} };
        int[][] positiveDiag2 = new int[][] {{1,0}, {2,1}, {3,2}, {4,3}, {5,4} };
        int[][] positiveDiag3 = new int[][] {{0,0}, {1,1}, {2,2}, {3,3}, {4,4}, {5,5} };
        int[][] positiveDiag4 = new int[][] {{0,1}, {1,2}, {2,3}, {3,4}, {4,5}, {5,6} };
        int[][] positiveDiag5 = new int[][] {{0,2}, {1,3}, {2,4}, {3,5}, {4,6} };
        int[][] positiveDiag6 = new int[][] {{0,3}, {1,4}, {2,5}, {3,6} };*/

       /* for(int i=0; i<positiveDiag.length; i++){

            for(int j=0; j< positiveDiag[i].length; j++){
                windowCount =0;
                windowCountStart =0;
                score=0;
                enemyScore=0;
                for(int g=0; g<positiveDiag[i][j].length; g++){
                    int x = positiveDiag[i][j][g];
                    int y = positiveDiag[i][j][g+1];
                    //System.out.println("This is the 3rd for loop G: "+ x + " " + y);
                    windowCount++;
                    if(windowCount == (windowCountStart+5)) {
                        windowCountStart++;
                        scoreCurrent += score;
                        enemyScoreCurrent += enemyScore;
                        score=0;
                        enemyScore=0;
                        if (windowCountStart == (positiveDiag[i].length%4)) {
                            scoreCurrent += score;
                            enemyScoreCurrent += enemyScore;
                            break;
                        }
                        g = windowCountStart;
                        windowCount = windowCountStart;
                        continue;
                    }
                    Move move = board[x][y];
                    if(move != null){
                        int currentID = move.getPlayerID();
                        if (playerID == currentID) {
                            score++;
                            enemyScore =0;
                            if(score == 4){
                                score = 200;
                            }

                        } else {
                            enemyScore++;
                            score=0;
                            if(enemyScore == 3){
                                 x= positiveDiag[i][j+1][0];
                                 y = positiveDiag[i][j+1][1];
                                 int nextPlayerID = board[x][y].getPlayerID();
                                if(nextPlayerID == playerID){
                                    enemyScore = -100;
                                }
                            }
                        }
                    }
                    g++;
                }
            }
        }

        //int x, y = positiveDiag[i][j];*/


    System.out.println("Returning Scores "+ (scoreCurrent - enemyScoreCurrent));

        return scoreCurrent - enemyScoreCurrent;

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

    public void checkWinner(){

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
        // Diagonal Positive
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
}