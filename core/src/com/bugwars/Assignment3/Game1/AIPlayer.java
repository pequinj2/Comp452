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
    private int playerID;
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
     * NegaMax /w will try to find the maximum possible score for each player. It will evaluate each
     * terminal node, then negate the scores found and pick the maximum value from the nodes of that
     * branch.
     * @param board Current instance of the game board
     * @param depth Depth of how far the AI wants to look
     * @param playerID PlayerID of what score is currently being evaluated (switches between 0 & 1)
     * @param alpha lower/upper bound
     * @param beta upper/lower bound
     * @return best score
     */
    public int negamax(Board board, int depth, int playerID, int alpha, int beta){
        // Check if we've reached the terminal node
        if(depth == 0){
            int multiplier = 1;
            // If this is the opponent's score of the node, make it negative because they're trying
            // to make us get the least score
            // (Current Player) != AI's player ID
            if(playerID!=this.playerID){
                multiplier=-1;
            }
            return multiplier * board.evaluateBoard(playerID);
        }
        // Get all VALID available moves - this will change as depth is decreased
        Array<Move> availableMoves = board.getMoves();
        // Set the negative infinity value - NegaMax looks for the highest value hence the negative infinity
        int bestScore = -Integer.MAX_VALUE;
        // Go through all current available moves for the current board instance
        for (int i = 0; i < availableMoves.size; i++) {
            // Make a new 'Move' as to not permanently modify it until we know the best move
            Move move = new Move(availableMoves.get(i));
            // Make 'fake' move on the board to see future results
            Board newBoard = board.makeMove(move, playerID);
            /** Invert the score bubbled up from child node below
             * If AI turn, find it's highest score
             * If its the opponents turn, whatever their best score is - is the worst score for the AI
             * so negate score to find it's highest value
             * Pass in:
             * - Board instance
             * - Next depth to search
             * - Player ID of opponents turn (goes back and forth from 0 or 1)
             * - Set the negation of alpha and beta for the next player's lower and upper bounds of
             * possible values that represent a window a node must score in based on it's parent node
              */
            int score = -negamax(newBoard, depth - 1, playerID ^1, -beta,-alpha);
            // Delete 'fake' move that was placed on the board - do this to keep board at original
            // when NegaMax is finished or onto the next instance
            // state, depending on the current instance of the board
            newBoard.deleteMove(move);
            // If highest score found
            if(score>bestScore){
                // Set new highest score
                bestScore = score;
                // ## Tutorial ##
                // Check if we're back at the root node - if so assign best move, if not keep digging
                if(depth == DEPTH){
                    bestMove = move;
                }
            }
            // Get new best score to check if next score is less than or equal to it - if so cut off
            alpha = max(alpha, bestScore);
            // Terminal node is outside of lower/upper bound so cut off that branch - no point to look at it
            if(alpha >= beta){
                break;
            }
        }
        // Return highest score found
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


}
