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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.bugwars.BugWars;
import com.bugwars.Helper.AssetManager;
import com.bugwars.PauseMenu;
import com.sun.org.apache.xpath.internal.operations.Bool;

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

    private Boolean getUserInput = true;

    public Game2 (OrthographicCamera camera, BugWars game){
        this.camera = camera;
        assetMgr = new AssetManager();
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game2Listeners();
        assetMgr = new AssetManager();
        scene = new CreateScene(assetMgr);
        batch = new SpriteBatch();

        // Load the TiledMap for rendering and set the viewport
        map = scene.getMap();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        camera.position.x = 255;
        //camera.position.y = 255;

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
        else if (getUserInput){
            Stage stg = hud.getStg();
            stg.draw();
            stg.act();
            Gdx.input.setInputProcessor(stg);
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                if(hud.getText()){
                    getUserInput = false;
                    stg.dispose();
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
