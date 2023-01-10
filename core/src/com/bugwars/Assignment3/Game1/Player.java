package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {

    private int character;
    private CreateScene scene;
    private int piecePosition = 6;
    private Board board;
    private int playerID;
    private Game1 game;

    public Player(CreateScene scene, Board board, int playerID, Game1 game){
        this.scene = scene;
        this.board = board;
        this.playerID = playerID;
        this.game = game;
    }



    /**
     * While its the user's turn, enable game piece movement via keys
     */
    public void movePlayerPiece(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){ // Move piece Left
            if(piecePosition != 0){
                piecePosition -= 1;
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){ // Move piece right
            if(piecePosition != 6){
                piecePosition += 1;
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){ // Drop piece
            Move getPosition = board.getMove(piecePosition);
            int rowDepth = getPosition.getX();
            if(rowDepth != 6){ // Make sure that the piece fits on the board
                Board newBoard = board.makeMove(getPosition, playerID);
                scene.setRowDepth(rowDepth);
                // Play drop piece animation
                game.setCurrentAction(2);
            }



        }
    }

    public int getPiecePosition(){
        return piecePosition;
    }
}
