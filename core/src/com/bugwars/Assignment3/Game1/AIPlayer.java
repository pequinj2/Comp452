package com.bugwars.Assignment3.Game1;

import static java.lang.Math.max;

public class AIPlayer {

    private Board board;
    private float currentScore, bestScore, recursedScore;
    private Move bestMove;

    public AIPlayer(Board board){
        this.board = board;

    }
    // Return score and have a pointer to best move because Java cannot return multiple values
    public float minMax(Board board, int playerID, float alpha, float beta, int maxDepth, int currentDepth){

        // Check if max depth has been reached or the game is over
        if(currentDepth == 0 || board.isGameOver()){
            return 0;
        }

        // Maximizing player
        if(playerID == 0){
            bestScore = -10000;
        }
        // Minimizing player
        else{
            bestScore = 10000;
        }

        for(Move move: board.getMoves()){
            Board newBoard = board.makeMove(move, playerID);

            // Recurse
            recursedScore = minMax(newBoard, playerID, -beta, -max(alpha, bestScore), maxDepth, currentDepth-1);
            currentScore = -recursedScore;

            // Update
            if(currentScore > bestScore){
                    bestScore = currentScore;
                    bestMove = move;

            }
            if(bestScore >= beta){
                break;
            }

        }

        return bestScore;
    }


    private void staticEvaluation(){
        // Threat move

        // Defence move
    }
}
