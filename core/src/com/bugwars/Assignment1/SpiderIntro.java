package com.bugwars.Assignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;

/**
 * This class will give the player the introduction to the Assignment1 and a simple player objective
 * - Destroy the evil centipede!!
 */
public class SpiderIntro implements Screen {

    private String text1 = "Hello world!!";
    private Texture spiderImg, boarderImg;
    private SpriteBatch batch;
    private int screenWidth = 1216;
    private int screenHeight = 896;
    private OrthographicCamera camera, newCamera;
    private BugWars game;
    private TextureRegion spid;

    // Text
    // Gaming text downloaded from https://www.dafont.com/retro-gaming.font
    private BitmapFont font;
    private String string1 = "";
    private String string2 = "Destroy it with your webbing before it takes over!";
    private String string3 = "< Press SPACE to Start >";
    private float charTimer = 0.1f; // Time between text character appearance
    private float currentCharTime = 0;
    private float currentCharTime2 = 0;
    private float currentCharTime3 = 0;
    private int numOfChars = 0;
    private int numOfChars2 = 0;
    private int numOfChars3 = 0;
    private boolean delayStartingText = true;
    private int textStartX = 40;
    private int textStartY = 160;
    private int subStrintStart = 0;
    private boolean nextFlag = false;
    private boolean nextFlag2 = false;

    // Audio
    private Music spiderV;

    /**
     * Initialize and play Assignment 1 Assignment1 intro
     * @param camera
     * @param game
     */
    public SpiderIntro (OrthographicCamera camera, BugWars game){
        this.game = game;
        spiderImg = new Texture(Gdx.files.internal("SpiderMainPageCrop.png"));
        spid = new TextureRegion(spiderImg, 100, 100, 1216, 800);
        boarderImg = new Texture(Gdx.files.internal("TextBoarder.png"));
        batch = new SpriteBatch();
        this.camera = camera;
        newCamera = new OrthographicCamera(screenWidth, screenHeight/2);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

        // Get Sound files - https://happycoding.io/tutorials/libgdx/sound
        spiderV = Gdx.audio.newMusic(Gdx.files.internal("Sound/SpiderIntro/untitled #85 -2.wav"));
        spiderV.setLooping(false);



    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Delay the time it takes to show text
        if(delayStartingText == true) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    string1 = "There is an evil centipede that threatens our lair.";
                    delayStartingText = false;
                }
            }, 2);

        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(spid, 0, 200);
        batch.draw(boarderImg, 0, 0);

        if(delayStartingText == false) {
            if (numOfChars < string1.length()) {
                currentCharTime += delta;
                if (currentCharTime >= charTimer) {
                    currentCharTime = 0;
                    numOfChars++;
                }
            }
            if((numOfChars == string1.length()) && (numOfChars > 0)){
                nextFlag = true;

            }
            spiderV.play(); // Start playing spider voice
            font.draw(batch, string1.substring(subStrintStart, numOfChars), textStartX, 160);

        }


        if (nextFlag == true){

            if (numOfChars2 < string2.length()){
                currentCharTime2 += delta;
                if(currentCharTime2 >= charTimer){
                    currentCharTime2 = 0;
                    numOfChars2++;
                }
            }
            else{
                nextFlag2 = true;
            }
            font.draw(batch, string2.substring(subStrintStart,numOfChars2), textStartX, 120);
        }
        if (nextFlag2 == true){
            spiderV.stop(); // Text is done, stop playing spider voice
            if (numOfChars3 < string3.length()){
                currentCharTime3 += delta;
                if(currentCharTime3 >= charTimer){
                    currentCharTime3 = 0;
                    numOfChars3++;
                }
            }
            if(numOfChars3 == string3.length()){
               // spiderV.stop(); // Text is done, stop playing spider voice
            }

            font.draw(batch, string3.substring(subStrintStart,numOfChars3), textStartX, 80);

        }

        batch.end();

        // When user hits SPACE, start the Assignment1!
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //spiderV.stop();
            this.hide();
            game.setScreen(new FadeScreen(game, this,new Assignment1(camera) ));
            //dispose();
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
        spiderV.dispose();
        spiderImg.dispose();
        boarderImg.dispose();
        batch.dispose();


    }
}
