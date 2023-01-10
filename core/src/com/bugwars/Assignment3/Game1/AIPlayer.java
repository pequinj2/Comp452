package com.bugwars.Assignment3.Game1;

import static java.lang.Math.max;

public class AIPlayer {

    private Board board;
    private float currentScore, bestScore, recursedScore;
    private Move bestMove;
    private int playerID;

    public AIPlayer(Board board, int playerID){
        this.board = board;
        this.playerID = playerID;

    }
    // Return score and have a pointer to best move because Java cannot return multiple values
    public float miniMax(Board board, int currentDepth, Boolean isMaxPlayer, float alpha, float beta){
        System.out.println("Current depth is: " + currentDepth);
        // Check if max depth has been reached or the game is over
        if(currentDepth == 0 || board.isGameOver()){
            return board.evaluate(playerID);
        }

        bestMove = null;
        bestScore = -1000;

        for(Move move: board.getMoves()){
            Board newBoard = board.makeMove(move,playerID);
            recursedScore = miniMax(newBoard, currentDepth-1, false, -max(alpha, bestScore), -beta);

            currentScore = -recursedScore;

            if(currentScore > bestScore){
                System.out.println("BEST SCORE REACHED");
                bestScore = currentScore;
                bestMove = move;
            }

            if(bestScore >= beta){
                break;
            }
        }
        return bestScore;


        /*// Maximizing player
        if(isMaxPlayer){
            bestScore = -1000;
            for(Move move: board.getMoves()){
                Board newBoard = board.makeMove(move,playerID);
                float score = miniMax(newBoard, currentDepth-1, false, alpha, beta);
                alpha = max(alpha, score);
                if(beta <= alpha){
                    break;
                }
            }
            return bestScore;
        }
        else{
            bestScore = 1000;
            for(Move move: board.getMoves()){
                Board newBoard = board.makeMove(move,playerID);
                float score = miniMax(newBoard, currentDepth-1, true, alpha, beta);
                beta = max(beta, score);
                if(beta <= alpha){
                    break;
                }
            }
            return bestScore;
        }*/


    }

    /**
     *
     * @param board
     */
    private void staticEvaluation(Board board){

        for(Move move: board.getMoves()) {
            // Check vertical

            // Check horizontal

            // Check Diagonal
        }
    }

    public Move getBestMove(){
        return bestMove;
    }


}
