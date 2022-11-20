package com.bugwars.Helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bugwars.BugWars;


/**
 * This class is used to give a fade animation effect to the screen transitions
 * Code for this class was taken from:
 * https://gist.github.com/Leowbattle/0cff3a06aabe4ee6bd9f723f8565506d
 * created by Leobattle
 */
public class FadeScreen implements Screen {
    private Game game;
    private Screen current, next;
    private OrthographicCamera camera;
    private int screenWidth = 1216;
    private int screenHeight = 896;
    private boolean fade = true;
    private ShapeRenderer shapeRenderer;
    private float change = 0;


    public FadeScreen(BugWars game, Screen current, Screen next){
        this.game = game;
        this.current = current;
        this.next = next;
        shapeRenderer = new ShapeRenderer();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (fade == true){
            current.render(Gdx.graphics.getDeltaTime());//Called when the screen should render itself.
        }
        else{
            next.render(Gdx.graphics.getDeltaTime());
        }

        /* The following code uses libGDXs OpenGL to use its API that will allow us to draw to the screen
        // https://happycoding.io/tutorials/libgdx/graphics
        // Different kinds of OpenGL blending:
        */ https://stackoverflow.com/questions/25347456/how-to-do-blending-in-libgdx
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0,0,0,change); // change is opacity ?
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Create our shape, in this case 'filled' is our shape
        shapeRenderer.rect(0,0, screenWidth, screenHeight); // Create filled rectangle
        shapeRenderer.end(); // End makes sure the shape we want is rendered
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (change > 1){
            fade = false;

            current.dispose(); // Dispose of previous screen asset
        }
        else if (change <= 0 && fade == false) {

            game.setScreen(next); // fade is done, set screen to next screen

        }
        change += fade == true ? 0.02 : -0.02;




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

        shapeRenderer.dispose();

    }
}
