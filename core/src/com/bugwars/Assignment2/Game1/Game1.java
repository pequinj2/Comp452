package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bugwars.Assignment2.ToolTip;
import com.bugwars.PauseMenu;
import com.bugwars.BugWars;

import java.util.PriorityQueue;

public class Game1 implements Screen {

    private SpriteBatch batch, hudBatch; //To render our sprites
    private World world; //To store our box 2D bodies - *** May not need this? Sounds like its for gravity***
    private Box2DDebugRenderer box2DBug;
    private OrthographicCamera camera;
    private BugWars game;
    private Stage stg;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    private int viewPortWidth = 300;
    private int viewPortHeight = 350;

    private TileSelector tileSelector;

    private ToolTip tooltip;
    private TileMap map;

    private PriorityQueue<Tile> queue;

    private Boolean run = false;

    public Game1(OrthographicCamera camera, BugWars game){
        this.game = game;
        this.camera = camera;
        this.pauseMenu = new PauseMenu(this, game, camera);
        stg = new Stage();
        //set Cameras
        //camera.setToOrtho(false, viewPortWidth, viewPortHeight); // Camera is effecting the size of the tile map shown
        tileSelector = new TileSelector(camera, stg);
        batch = new SpriteBatch();
        tooltip = new ToolTip(camera);
        /**
         * Implement the priority queue of our nodes, because the map is a 16x16 graph we need a
         * space of 256. Use TileCompare to sort the nodes properly in a min-Heap structure.
         */
        queue = new PriorityQueue<>(256, new TileCompare());

        InputAdapter test = new InputAdapter() {
            @Override
            public boolean touchDown (int screenX, int x, int y, int pointer) {


                return true;
            }
        };
        InputMultiplexer iM = new InputMultiplexer(test,stg );
        //Gdx.input.setInputProcessor(iM);

    }


    @Override
    public void show() {

    }

    private void update(){
        run = tileSelector.getRun();
    }

    @Override
    public void render(float v) {
        update();

        if(isPaused){ // check if game is paused
            pause();
        }if(run) { // User is done making the tile map run algorithm on created map
            tileSelector.render(batch);

        }else {
            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //batch.begin();
            stg.act(Gdx.graphics.getDeltaTime());
            stg.draw();

            // Show the cost of each of the terrains by getting the Text set in TileSelector
            if(tileSelector.getShowTooltip() == true){
                tooltip.setText(tileSelector.getText());
                tooltip.render(batch);

            }
            // Show the selected tile
            if(tileSelector.getShowTileSelected() == true){
                if(tileSelector.getTexture() != null) {
                    tooltip.setLabel(tileSelector.getTexture());
                    tooltip.renderTileLabel(batch);

                }
            }

            //batch.end();

        }


    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
