package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bugwars.Assignment2.ToolTip;
import com.bugwars.Helper.FadeScreen;
import com.bugwars.PauseMenu;
import com.bugwars.BugWars;

import java.util.HashMap;

/**
 * The initial class that starts Game 1 of Assignment 2. This is the main class that will oversee
 * the creation of the user generated map and call the next Screen class, RunMap.java, to simulate
 * the A* algorithm back to the user
 */
public class Game1 implements Screen {

    private SpriteBatch batch, hudBatch; //To render our sprites
    private OrthographicCamera camera;
    private BugWars game;
    private Stage stg;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    private TileSelector tileSelector;
    private ToolTip tooltip;
    private GenerateGraph graph;
    private HashMap<Integer, Tile> map;
    private Heuristic hue;

    private Boolean run = false;
    private Boolean runAStar = false;

    private PathFindingAStar path;


    public Game1(OrthographicCamera camera, BugWars game){
        this.game = game;
        this.camera = camera;
        // Initialize Pause Menu
        this.pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game1Listeners();
        stg = new Stage();
        tileSelector = new TileSelector(camera, stg);
        batch = new SpriteBatch();
        tooltip = new ToolTip(camera);
    }



    @Override
    public void show() {

    }

    /**
     * Check if user wants to run the simulation and/or user clicks ESC to access the Pause Menu
     */
    private void update(){
        // Get status of Run button
        run = tileSelector.getRun();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
        }
    }

    @Override
    public void render(float v) {
        update();

        if(isPaused){ // check if game is paused
            pause();
        }else if(run) { // If true, user is done making the tile map
            // If there is a problem with the map, issue warning
            if(tileSelector.getWarningText() != "") {
                tileSelector.render(batch); // Show warning
                run = false;
                tileSelector.setRun(); // To false so user can fix map
            }
            else{ // No problem with map found, create the priority queue and generate map with algorithm
                graph = tileSelector.getGraph(); // Get the graph of the translated Button objects to Tile objects
                runAStar = true; // Set true to run simulation in next world step
                run = false;
                tileSelector.setRun(); // To false because map is ready for simulation

            }

        } else if(runAStar){
            // Get the graph and tile connections from the user generated map
            map = graph.getQueue();
            // Get the Start and End tiles
            Tile srt = graph.getStartTile();
            Tile end = graph.getEndTile();
            hue = new Heuristic(end); // Ready heuristic with desired end node
            // Ready the A* algorithm
            path = new PathFindingAStar(map, srt, end, hue);
            runAStar = false;
            // Set Screen to RunMap to show simulation
            game.setScreen(new FadeScreen(game, this, new RunMap(tileSelector.getBtnList(), camera, path, game)));
        }
        else { // Else let user create map of their choosing
            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stg.act(Gdx.graphics.getDeltaTime());
            stg.draw();
            tileSelector.render(batch);
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
        //batch.dispose();
        //stg.dispose();
        pauseMenu.dispose();
        //tileSelector.dispose();

    }

}
