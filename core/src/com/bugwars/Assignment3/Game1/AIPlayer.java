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

    private int DEPTH = 4;

    public AIPlayer(Board board, int playerID, Game1 game, CreateScene scene){
        this.board = board;
        this.playerID = playerID;
        System.out.println("This is the AI Id: " + playerID);
        this.game = game;
        this.scene = scene;

    }

    /**
     * Works but not as good as NegaMax
     * @param board
     * @param currentDepth
     * @param isMaxPlayer
     * @param alpha
     * @param beta
     * @return
     */
    public float miniMax(Board board, int currentDepth, Boolean isMaxPlayer, float alpha, float beta){
        //System.out.println("\nCurrent depth is: " + currentDepth);
        Array<Move> availableMoves = board.getMoves();
        int enemyID = playerID ^ 1;
        // Check if max depth has been reached or the game is over
        if(currentDepth == 0 || board.isGameOver()){
            return board.evaluateBoard(playerID);
        }
        int randIndex = (int)Math.random() * availableMoves.size;
        bestMove = availableMoves.get(randIndex);

        if(isMaxPlayer) {
            int bestScore = -1000;
            for (int i = 0; i < availableMoves.size; i++) {
                Move move = new Move(availableMoves.get(i));
                Board newBoard = board.makeMove(move, playerID);
                int score = (int) miniMax(newBoard, currentDepth - 1, false, alpha, beta);
                newBoard.deleteMove(move);
                if(score > bestScore){
                    bestScore = score;
                    move.updateScore(bestScore);
                    if(currentDepth == DEPTH){
                        bestMove = move;
                    }
                    System.out.println("\nMove max"+move.getX()+move.getY()+" gets a score of: "+ bestScore);

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
                Board newBoard = board.makeMove(move, enemyID);
                int score = (int) miniMax(newBoard, currentDepth - 1, true, alpha, beta);
                newBoard.deleteMove(move);
                if(score < bestScore){
                    bestScore = score;
                    move.updateScore(bestScore);
                    if(currentDepth == DEPTH){
                        bestMove = move;
                    }
                    System.out.println("\nMove min"+move.getX()+move.getY()+" gets a score of: "+ bestScore);

                }
                beta = min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }

    }

    /**
     * https://www.youtube.com/watch?v=5UVwksLYAKI&t=892s
     * Chess Engine in Python - Part 14 - Nega Max and Alpha Beta Pruning - Eddie Sharick
     * @param board
     * @param depth
     * @param playerID
     * @param alpha
     * @param beta
     * @return
     */
    public int negamax(Board board, int depth, int playerID, int alpha, int beta){
        if(depth == 0){
            int multiplier = 1;
            if(playerID==0){
                multiplier=-1;
            }
            return multiplier * board.evaluateBoard(playerID);
        }

        Array<Move> availableMoves = board.getMoves();
        int bestScore = -10000;
        for (int i = 0; i < availableMoves.size; i++) {
            Move move = new Move(availableMoves.get(i));
            Board newBoard = board.makeMove(move, playerID);
            int score = -negamax(newBoard, depth - 1, playerID ^1, -max(alpha, bestScore), -beta);
            newBoard.deleteMove(move);

            if(score>bestScore){
                bestScore = score;
                if(depth == DEPTH){
                    bestMove = move;
                }
            }

            if(bestScore>= beta){
                break;
            }
        }

        return bestScore;
    }


    public void playTurn(){
        //float temp = miniMax(board, DEPTH, true, -1000, 1000);
        int temp = negamax(board, DEPTH, playerID, -1000, 1000);
        System.out.println("This is the minimax score: " + temp + "\nThis is the best move: " + bestMove.getX() +" " + bestMove.getY());
        piecePosition = bestMove.getX();
        scene.movePiece(bestMove.getY());
        scene.setRowDepth(piecePosition);
        setBestMove();
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

}
