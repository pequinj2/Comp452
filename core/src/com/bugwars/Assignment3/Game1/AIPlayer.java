package com.bugwars.Assignment3.Game1;

import static java.lang.Math.max;
import static java.lang.Math.min;
import com.badlogic.gdx.utils.Array;

/**
 * AI class that will face off against the user. It will use NegaMax with ab pruning to try and beat
 * it's opponent. Main calls will be to the 'Board' class to run evaluations on moves it wants to
 * make, then call the 'Game1' and 'CreateScene' classes to render move and switch turns.
 */
public class AIPlayer {

    private Board board;
    private Move bestMove;
    private int playerID, piecePosition;
    private Game1 game;
    private CreateScene scene;
    private int DEPTH = 4;

    public AIPlayer(Board board, int playerID, Game1 game, CreateScene scene){
        this.board = board;
        this.playerID = playerID;
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
     * The above youtube tutorial was used to help debug negamax function.
     * NegaMax
     * @param board Current instance of the game board
     * @param depth Depth of how far the AI wants to look
     * @param playerID PlayerID of what score is currently being evaluated (switches between 0 & 1)
     * @param alpha
     * @param beta
     * @return
     */
    public int negamax(Board board, int depth, int playerID, int alpha, int beta){
        if(depth == 0){
            int multiplier = 1;
            if(playerID!=this.playerID){
                multiplier=-1;
            }
            return multiplier * board.evaluateBoard(playerID);
        }

        Array<Move> availableMoves = board.getMoves();
        int bestScore = -Integer.MAX_VALUE;
        for (int i = 0; i < availableMoves.size; i++) {
            Move move = new Move(availableMoves.get(i));
            Board newBoard = board.makeMove(move, playerID);
            int score = -negamax(newBoard, depth - 1, playerID ^1, -beta,-alpha);
            newBoard.deleteMove(move);

            if(score>bestScore){
                bestScore = score;
                if(depth == DEPTH){
                    bestMove = move;
                }
            }
            alpha = max(alpha, bestScore);
            if(alpha >= beta){
                break;
            }
        }

        return bestScore;
    }


    /**
     * Call negamax function to find the best move to play on the current game board. When 'bestMove'
     * is found by NegaMax, call the 'CreateScene' class to render move.
     */
    public void playTurn(){
        int temp = negamax(board, DEPTH, playerID, -1000, 1000);
        scene.movePiece(bestMove.getY());
        scene.setRowDepth(bestMove.getX());
        setBestMove();
    }

    /**
     * Make the move on the actual game board then switch to next player.
     * Note: game.setCurrentAction() will see if a winning move is made
     */
    public void setBestMove(){
        board.makeMove(bestMove,playerID);
        game.setCurrentAction();
    }

    /**
     * Get the X (Row) value that the piece was dropped at
     * @return
     */
    public int getPiecePosition(){
        return piecePosition;
    }

}
