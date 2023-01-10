package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugwars.Assignment3.Game1.CreateScene;
import com.bugwars.BugWars;
import com.bugwars.PauseMenu;

public class Game1 implements Screen {

    // Create Map
    private OrthographicCamera camera;
    private CreateScene scene;
    private SpriteBatch batch;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    // Player
    private Boolean isPlayersTurn = true;
    private Player player;

    // AI

    // Overall game
    private int user, aiPlayer, currentAction;
    private Boolean pieceDropped = false;
    private Board board;


    public Game1(OrthographicCamera camera, BugWars game, int i){
        this.camera = camera;
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment3Game1Listeners();
        scene = new CreateScene(this);
        batch = new SpriteBatch();
        board = new Board();

        user = i;
        aiPlayer ^= i;
        player = new Player(scene, board, user, this);
        // Maybe need to change these locations later
        scene.currentPiece(user);
        currentAction = 0;


    }

    @Override
    public void show() {

    }

    public void update(){

        switch(currentAction){
            case 0: // User's turn
                player.movePlayerPiece();
                scene.movePiece(player.getPiecePosition());
                break;
            case 1: // AI's turn
                break;
            case 2: // Dropping game piece
                //System.out.println(currentAction);
                scene.dropPiece(player.getPiecePosition());
                break;
        }


    }

    @Override
    public void render(float v) {
        update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // render in CreateScene
        scene.render(batch);

        batch.end();


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void changeCurrentPlayer(){
        // Change current player
        isPlayersTurn ^= isPlayersTurn;

        if(isPlayersTurn) {
            scene.currentPiece(user);
        }
        else{
            scene.currentPiece(aiPlayer);

        }
    }

    public void setCurrentAction(int i){
        currentAction = i;
    }
}
