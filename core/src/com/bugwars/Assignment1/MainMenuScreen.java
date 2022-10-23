package com.bugwars.Assignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bugwars.Helper.FadeScreen;

/**
 * Main menu screen implimentation. Code was modified from LibGDX tutorial
 * https://libgdx.com/wiki/start/simple-game-extended
 */
public class MainMenuScreen implements Screen {

    final BugWars game;

    private OrthographicCamera camera;

    public MainMenuScreen (final BugWars game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 900);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Bug Wars!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            this.hide();
            game.setScreen(new FadeScreen(game, this,new SpiderIntro(camera, game) ));
            dispose();
        }


    }

    @Override
    public void resize(int width, int height) {

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
