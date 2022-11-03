package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Assignment2.ToolTip;
import com.bugwars.PauseMenu;
import com.bugwars.BugWars;

public class Game1 implements Screen {

    private SpriteBatch batch, hudBatch; //To render our sprites
    private World world; //To store our box 2D bodies - *** May not need this? Sounds like its for gravity***
    private Box2DDebugRenderer box2DBug;
    private OrthographicCamera camera;
    private BugWars game;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    private int viewPortWidth = 300;
    private int viewPortHeight = 350;

    private TileSelector tileSelector;

    private ToolTip tooltip;



    public Game1(OrthographicCamera camera, BugWars game){
        this.game = game;
        this.camera = camera;
        this.pauseMenu = new PauseMenu(this, game, camera);
        //set Cameras
        //camera.setToOrtho(false, viewPortWidth, viewPortHeight); // Camera is effecting the size of the tile map shown
        tileSelector = new TileSelector(camera);
        batch = new SpriteBatch();
        tooltip = new ToolTip(camera);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if(isPaused){ // check if game is paused
            pause();
        }else {
            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //batch.begin();
            tileSelector.render(batch);

            // Show the cost of each of the terrains by getting the Text set in TileSelector
            if(tileSelector.getShowTooltip() == true){
                tooltip.setText(tileSelector.getText());
                tooltip.render(batch);
            }
            // Show the selected tile
            if(tileSelector.getShowTileSelected() == true){
                tooltip.setLabel(tileSelector.getTexture());
                tooltip.renderTileLabel(batch);
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
