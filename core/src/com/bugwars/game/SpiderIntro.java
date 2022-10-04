package com.bugwars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpiderIntro implements Screen {

    String text1 = "Hello world!!";
    Texture spiderImg;
    SpriteBatch batch;
    private int screenWidth = 1216;
    private int screenHeight = 896;
    private OrthographicCamera camera, newCamera;
    private BugWars game;
    TextureRegion spid;

    public SpiderIntro (OrthographicCamera camera, BugWars game){
        this.game = game;
        spiderImg = new Texture(Gdx.files.internal("SpiderMainPageCrop.png"));
        spid = new TextureRegion(spiderImg, 100, 100, 1216, 800);
        batch = new SpriteBatch();
        this.camera = camera;
        newCamera = new OrthographicCamera(screenWidth, screenHeight/2);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //System.out.println("This is the camera position X: " + newCamera.position.x + " This sis the image position: "+ (spiderImg.getWidth()/2));
        batch.begin();
        batch.draw(spid, 0, 200);
        batch.end();

        if (Gdx.input.isTouched()) {
            this.hide();
            game.setScreen(new Assignment1(camera));
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
