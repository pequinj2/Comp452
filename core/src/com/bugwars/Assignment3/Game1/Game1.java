package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private AIPlayer ai;

    // Overall game
    private int user, aiPlayer, currentAction;
    private Boolean pieceDropped = false;
    private Board board;
    private int piecePosition;
    private BitmapFont font;
    private String tie = "Game Over - Tie!";


    public Game1(OrthographicCamera camera, BugWars game, int i){
        this.camera = camera;
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment3Game1Listeners();
        scene = new CreateScene(this);
        batch = new SpriteBatch();
        board = new Board();

        user = i;
        aiPlayer = i ^ 1;
        player = new Player(scene, board, user, this);
        ai = new AIPlayer(board, aiPlayer, this, scene);
        // Maybe need to change these locations later
        scene.currentPiece(user);
        currentAction = 0;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator


    }

    @Override
    public void show() {

    }

    public void update(){

        if(isPlayersTurn){
            switch(currentAction){
                case 0:
                    player.movePlayerPiece();
                    piecePosition = player.getPiecePosition();
                    scene.movePiece(piecePosition);
                    break;
                case 1:
                    scene.dropPiece(piecePosition);
                    break;

            }

        }
        else{
            switch(currentAction){
                case 0:
                    scene.currentPiece(aiPlayer);
                    ai.playTurn();
                    piecePosition = ai.getPiecePosition();
                    System.out.println("IN THE AI TURN 2 animation " + piecePosition );

                    board.printBoard();
                    break;
                case 1:
                    scene.dropPiece(piecePosition);
                    break;

            }

        }

    }

    @Override
    public void render(float v) {
        if(board.isGameOver()){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.draw(batch, tie, 40, 830);
            scene.render(batch);

            batch.end();

        }else{
            update();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            // render in CreateScene
            scene.render(batch);

            batch.end();

        }


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
        isPlayersTurn = !isPlayersTurn;

        if(isPlayersTurn) {
            System.out.println("USer Player");
            scene.currentPiece(user);
        }
        else{
            System.out.println("AI Player");
            scene.currentPiece(aiPlayer);

        }
    }

    public void setCurrentAction(int i){
        currentAction = currentAction ^ 1;

    }
}
