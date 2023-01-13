package com.bugwars.Assignment3.Game1;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.badlogic.gdx.utils.Array;

public class AIPlayer {

    private Board board;
    private float currentScore,recursedScore;
    private Move bestMove;
    private int playerID, piecePosition, arrayPosition;
    private Game1 game;
    private CreateScene scene;

    private Array<Move> movesToMake;
    private Array<Move> allowedMoves;

    public AIPlayer(Board board, int playerID, Game1 game, CreateScene scene){
        this.board = board;
        this.playerID = playerID;
        System.out.println("This is the AI Id: " + playerID);
        this.game = game;
        this.scene = scene;

    }
    // Return score and have a pointer to best move because Java cannot return multiple values
    public float miniMax(Board board, int currentDepth, Boolean isMaxPlayer, float alpha, float beta){
        //System.out.println("\nCurrent depth is: " + currentDepth);
        Array<Move> availableMoves = board.getMoves();
        // Check if max depth has been reached or the game is over
        if(currentDepth == 0 || board.isGameOver()){
            return board.evalualteBoard(playerID);
        }
        bestMove = null;

        // Maximizing player
       /* if(isMaxPlayer){

            for(int i = 0; i<availableMoves.size; i++){
                Move move = new Move(availableMoves.get(i));
                Move prevMove = availableMoves.get(i);
                System.out.println("Checking move MAX: " + move.getX() + " , " + move.getY());
                Board newBoard = board.makeMove(move,playerID);
               //board.printBoard();
                float score = miniMax(newBoard, currentDepth-1, false, alpha, beta);
                newBoard.deleteMove(prevMove);
                //System.out.println("Checking Alpha: " + alpha + " , " + score);
                //availableMoves.set(i, prevMove);
                System.out.println("SCORE and ALPHA: " + move.getX() + " ," + move.getY() +" - "+score+" , "+alpha);
                if(score > alpha){
                    alpha = score;

                    bestMove = new Move(prevMove);
                    System.out.println("Returning alpha move?: " + bestMove.getX() + " ,"+ bestMove.getY());
                    arrayPosition = i;
                }
                if(beta <= alpha){
                    break;
                }
            }

            return alpha;
        }
        else{


            for(int i = 0; i<availableMoves.size; i++){
                Move move = new Move(availableMoves.get(i));
                //System.out.println("ON THIS Move?: " + move.getX() + " ,"+ move.getY());
                Move prevMove = availableMoves.get(i);
                System.out.println("Checking move MIN: " + move.getX() + " , " + move.getY());
                Board newBoard = board.makeMove(move,playerID);
                float score = miniMax(newBoard, currentDepth-1, true, alpha, beta);
                newBoard.deleteMove(prevMove);
                //availableMoves.set(i, prevMove);
                //System.out.println("Returning: " + move.getX());
                if(score < beta){
                    beta = score;
                    //bestMove = new Move(prevMove);
                    //System.out.println("Returning Move?: " + bestMove.getX() + " ,"+ bestMove.getY());
                    arrayPosition = i;
                }

                if(beta <= alpha){
                    break;
                }
            }
            return beta;
        }*/

        if(isMaxPlayer) {
            int bestScore = -1000;
            for (int i = 0; i < availableMoves.size; i++) {
                Move move = new Move(availableMoves.get(i));
                Board newBoard = board.makeMove(move, playerID);
                System.out.println("Checking move MAX: " + move.getX() + " , " + move.getY());
                int score = (int) miniMax(newBoard, currentDepth - 1, false, alpha, beta);
                newBoard.deleteMove(move);
                if(score > bestScore){
                    bestScore = score;
                    bestMove = move;
                    move.updateScore(bestScore);
                    System.out.println("\nMove max"+move.getX()+move.getY()+" gets a score of: "+ bestScore);
                    updateMovesMade(move, bestScore);

                }
                alpha = max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }else{
            int bestScore = 1000;
            for (int i = 0; i < availableMoves.size; i++) {
                Move move = new Move(availableMoves.get(i));
                Board newBoard = board.makeMove(move, playerID);
                System.out.println("Checking move MIN: " + move.getX() + " , " + move.getY());
                int score = (int) miniMax(newBoard, currentDepth - 1, true, alpha, beta);
                newBoard.deleteMove(move);
                if(score < bestScore){
                    bestScore = score;
                    bestMove = move;
                    move.updateScore(bestScore);
                    System.out.println("\nMove min"+move.getX()+move.getY()+" gets a score of: "+ bestScore);
                    updateMovesMade(move, bestScore);
                }
                beta = min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }

    }

    public void playTurn(){
        allowedMoves = board.getMoves();
        movesToMake = new Array<Move>();
        float temp = miniMax(board, 4, true, -1000, 1000);
        Array<Move> finalMoves = new Array<Move>();
        for (Move m: movesToMake){
            for(Move checkMove: allowedMoves){
                if(m.getX() == checkMove.getX() && m.getY()==checkMove.getY()){
                    finalMoves.add(m);
                }
            }
        }
        for(Move getFinal: finalMoves){
            System.out.println("Move ID: " + getFinal.getX()+","+getFinal.getY()+"   Score: "+ getFinal.getScore());
            if(bestMove.getScore() < getFinal.getScore()){
                bestMove = getFinal;
            }
        }
        System.out.println("This is the minimax score: " + temp + "\nThis is the best move: " + bestMove.getX() +" " + bestMove.getY());
        piecePosition = bestMove.getX();
        //System.out.println("The piece position: " + piecePosition);
        scene.movePiece(bestMove.getY());
        scene.setRowDepth(piecePosition);
        setBestMove();
        //game.setCurrentAction(1);
    }


    public Move getBestMove(){
        System.out.println("This is the best move: "+ bestMove.getX() +","+ bestMove.getY());
        return bestMove;
    }

    public void setBestMove(){
        board.makeMove(bestMove,playerID);
        //board.getMoves().set(arrayPosition, bestMove);
        game.setCurrentAction(1);
    }

    public int getPiecePosition(){
        return piecePosition;
    }

    public void updateMovesMade(Move move, int bestScore){
        if(movesToMake.size != 0){
            boolean moveFound = false;
            for(Move m: movesToMake){
                if(m.getX()==move.getX() && m.getY()==move.getY() ){
                    if(m.getScore()<move.getScore()) {
                        m.updateScore(bestScore);
                    }
                    moveFound = true;
                    break;
                }
            }
            if(moveFound != true){
                movesToMake.add(move);
            }
        }
        else{
            movesToMake.add(move);
        }

    }
}
