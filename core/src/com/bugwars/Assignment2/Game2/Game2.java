package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.BugWars;
import com.bugwars.Helper.AntFactory;
import com.bugwars.Helper.AssetManager;
import com.bugwars.PauseMenu;

import java.util.ArrayList;

/**
 * Main class of Game 2 Assignment 2, this class will control the flow of the game, including
 * tracking the ants and calling other classes to initiate different events in the ant simulation.
 */
public class Game2 implements Screen {

    // Create Map
    private AssetManager assetMgr;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private CreateScene scene;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    private OrthographicCamera camera;
    private int viewPortWidth = 600;
    private int viewPortHeight = 600;
    private SpriteBatch batch;

    // Player Hud
    private Hud hud;

    // Get how many ants the user wants
    private Boolean getUserInput = true;

    // Ants
    private AntPlayer currentAnt;
    private AntFactory antFact;
    private ArrayList<AntPlayer> ants = new ArrayList<AntPlayer>();

    public Game2 (OrthographicCamera camera, BugWars game){
        this.camera = camera;
        assetMgr = new AssetManager();
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game2Listeners();
        assetMgr = new AssetManager();
        scene = new CreateScene(assetMgr);
        batch = new SpriteBatch();
        antFact = new AntFactory(assetMgr);

        // Load the TiledMap for rendering and set the viewport
        map = scene.getMap();


        // load initial world objects

        // Load Hud
        hud = new Hud(assetMgr);

    }

    @Override
    public void show() {

    }

    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
        }


    }

    @Override
    public void render(float delta) {

        update();

        if(isPaused){ // check if game is paused
            pause();
        }
        else if (getUserInput){ // Game starts - get the number of ants the user wants to start with
            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Stage stg = hud.getStg();
            stg.draw();
            stg.act();
            Gdx.input.setInputProcessor(stg);
            // When the user has inputted a number and clicks enter - check if the number is valid
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                // Returns true if number is valid - start generating simulation
                if(hud.getText()){
                    getUserInput = false; // Done with this section so mark as 'false' to skip
                    stg.dispose();

                    /**
                     * Generate scene items in random locations on the map:
                     * 0 = Berry
                     * 1 = Poison
                     * 2 = Water
                     * 3 = Ant Hill
                      */
                    scene.generateItems(0);
                    scene.generateItems(0);
                    scene.generateItems(1);
                    scene.generateItems(1);
                    scene.generateItems(2);
                    scene.generateItems(2);
                    scene.generateItems(3);
                    // Set the location of the home ant hill
                    antFact.setAntHillHome(scene.getAntHillHome());

                    int numOfAnts = hud.getNumOfAnts();
                    // For the number of ants the user wants, call the ant factory to make a new one
                    // and add them to the 'ants' array for tracking
                    for(int i=0; i < numOfAnts; i++){
                        ants.add(antFact.makeAnt());
                    }

                    // Render base map and update camera position
                    renderer = new OrthogonalTiledMapRenderer(map);
                    camera.setToOrtho(true, viewPortWidth, viewPortHeight);
                    camera.position.x = 255;
                    camera.position.y = 210;

                }
            }
        }
        else {

            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            renderer.render();
            camera.update();
            renderer.setView(camera);
            batch.begin();
            hud.render(batch);
            batch.end();


        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pauseMenu.render();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        assetMgr.dispose();
    }
}
