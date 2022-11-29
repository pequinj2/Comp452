package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bugwars.BugWars;
import com.bugwars.Helper.AssetManager;
import com.bugwars.PauseMenu;

public class Game2 implements Screen {

    private AssetManager assetMgr;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    public Game2 (OrthographicCamera camera, BugWars game){
        assetMgr = new AssetManager();
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game2Listeners();
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
