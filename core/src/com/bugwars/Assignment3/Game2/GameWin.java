package com.bugwars.Assignment3.Game2;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.bugwars.BugWars;
import com.bugwars.Helper.Animator;
import com.bugwars.Helper.FadeScreen;
import com.bugwars.MainMenuScreen;

/**
 * This class will display the final winning screen for Assignment 3 Game 2
 */
public class GameWin implements Screen {

    private Animation<TextureRegion> animation;
    private Animator animate;
    private SpriteBatch batch;
    private float stateTime;
    private OrthographicCamera camera;
    private float viewPortWidth = 250;
    private float viewPortHeight = 250;
    // Pause Menu
    private BugWars game;
    private boolean isDone = false;
    private boolean start = false;
    private long delayTimer, currentTime;


    public GameWin(OrthographicCamera camera, BugWars game){
        this.game = game;
        this.camera = camera;
        animate = new Animator();
        animation = animate.gameTwoWinAnimator();
        batch = new SpriteBatch();
        stateTime = 0f;

        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        Vector3 position = camera.position;
        position.x = 270;
        //position.y = 400;
        delayTimer = millis() + (2*1000);
    }

    @Override
    public void show() {

    }

    private void update(){
        if(animation.isAnimationFinished(stateTime)){
            isDone=true;
        }
        if(Gdx.input.isTouched() && isDone){
            // Go back to main menu
            game.setScreen(new FadeScreen(game, this, new MainMenuScreen(game) ));
        }

    }


    @Override
    public void render(float v) {
        if(start) {
            update();

            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            /**
             * https://stackoverflow.com/questions/34164309/gl-color-buffer-bit-regenerating-which-memory
             * Explains why clearing the color buffer is important
             */
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion winFrame = animation.getKeyFrame(stateTime, false);
            batch.draw(winFrame, 0, 0);
            batch.end();
        }
        else{
            // Simple timer to start the animation from fade screen
            currentTime = millis();
            if(currentTime > delayTimer){
                start = true;
            }
        }
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
