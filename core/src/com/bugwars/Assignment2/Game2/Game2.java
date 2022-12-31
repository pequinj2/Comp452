package com.bugwars.Assignment2.Game2;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    private ArrayList<AntPlayer> deadAnts = new ArrayList<AntPlayer>();
    private long currentTime, startTime;
    private int newAntsToMake = 0;

    // Final results
    private boolean showFinal = false;
    private Table tbl;
    private Skin skin;
    private TextButton finalBtn, goBackBtn;
    private Stage btnStg = new Stage();
    private BitmapFont font;


    public Game2 (OrthographicCamera camera, BugWars game){
        this.camera = camera;
        assetMgr = new AssetManager();
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game2Listeners();
        assetMgr = new AssetManager();
        scene = new CreateScene(assetMgr);
        batch = new SpriteBatch();
        antFact = new AntFactory(assetMgr, scene);

        // Load the TiledMap for rendering and set the viewport
        map = scene.getMap();

        // Load Hud
        hud = new Hud(assetMgr);

        startTime = millis() + 2000;

        finalBtn(ants);

    }

    @Override
    public void show() {

    }

    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
        }
        if(showFinal){
            // Stop doing updates
        }
        else {
            currentTime = millis();
            // Render ants, the time intervals are to see the movements at a steady speed
            if (currentTime >= startTime) {
                for (AntPlayer ant : ants) {
                    if (ant.getAntAlive()) {
                        scene.cellUpdateAntPrevPos(ant.antPreviousPos());
                        scene.cellUpdateAntPos(ant, ant.antCurrentPos());
                        ant.Update();
                    } else { // If an ant is dead put it in the remove list to dispose of object in update
                        deadAnts.add(ant);
                        scene.removeDeadAnt(ant.antPreviousPos());
                        hud.setDeadAnts();
                    }
                }
                startTime = millis() + 500;
            }
            // Dispose of dead ants
            for (AntPlayer deadAnt : deadAnts) {
                ants.remove(deadAnt);
            }
            deadAnts.clear();

            // Make the new Ants
            for (int i = 0; i < newAntsToMake; i++) {
                ants.add(antFact.makeAnt(this));
                hud.setAliveAnts();
            }
            newAntsToMake = 0;
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
                        ants.add(antFact.makeAnt(this));
                    }

                    // Render base map and update camera position
                    renderer = new OrthogonalTiledMapRenderer(map);
                    camera.setToOrtho(true, viewPortWidth, viewPortHeight);
                    camera.position.x = 255;
                    camera.position.y = 210;

                }
            }
        }
        else if(showFinal){
            batch.begin();
            hud.renderFinal(batch);
            batch.end();
            btnStg.draw();

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
            Gdx.input.setInputProcessor(btnStg);
            btnStg.draw();


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

    public void makeNewAnt(){

        newAntsToMake++;
    }


    public void finalBtn(ArrayList<AntPlayer> ants){
        btnStg = new Stage();
        tbl = new Table();
        btnStg.addActor(tbl);
        tbl.setFillParent(true);
        //tbl.setDebug(true);
        skin = new Skin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = assetMgr.getBtnUp();
        style.down = assetMgr.getBtnDown();

        finalBtn = new TextButton("Show Results",style);
        tbl.add(finalBtn).width(320).padLeft(700).padTop(40);
        tbl.top().left();
        tbl.row();

        goBackBtn = new TextButton("Continue",style);
        tbl.add(goBackBtn).width(320).padLeft(700).padTop(700);
        //tbl.bottom().left();
        goBackBtn.setVisible(false);

        goBackBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showFinal = false;
                goBackBtn.setVisible(false);
                finalBtn.setVisible(true);
            }
        });

        finalBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hud.setFinalResults(ants);
                showFinal = true;
                goBackBtn.setVisible(true);
                finalBtn.setVisible(false);
            }
        });



    }
}
