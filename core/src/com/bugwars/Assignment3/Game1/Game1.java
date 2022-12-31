package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Screen;
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


    public Game1(OrthographicCamera camera, BugWars game){
        this.camera = camera;
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment3Game1Listeners();
        scene = new CreateScene();
        batch = new SpriteBatch();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // render in CreateScene
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
}
