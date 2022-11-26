package com.bugwars.Assignment2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment1.Assignment1;
import com.bugwars.Assignment2.Game1.Game1;
import com.bugwars.BugWars;
import com.bugwars.Helper.FadeScreen;

/**
 * This class will give the player the introduction to the Assignment1 and a simple player objective
 * - Destroy the evil centipede!!
 */
public class AntIntro implements Screen {

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
    private String string2 = "Create different mazes the recruits will have to ";
    private String string3 = "explore through for food.";

    private float charTimer = 0.1f; // Time between text character appearance
    private float currentCharTime = 0;
    private float currentCharTime2 = 0;
    private float currentCharTime3 = 0;
    private int numOfChars = 0;
    private int numOfChars2 = 0;
    private int numOfChars3 = 0;
    private boolean delayStartingText = true;
    private int textStartX = 40;
    private int subStrintStart = 0;
    private boolean nextFlag = false;
    private boolean nextFlag2 = false;

    // Audio
    private Music spiderV;

    // Text
    private FileHandle handle;
    private String textLines[];
    private String showTextLines[] = new String[3];
    private int currentStringLine = 0;
    private int delayTime = 2;
    private int nextPage = 100;
    private int count = 0;


    /**
     * Initialize and play Assignment 2 Game1 and Game2 intros
     * @param camera
     * @param game
     */
    public AntIntro(OrthographicCamera camera, BugWars game){
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

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(spid, 0, 200);
        batch.draw(boarderImg, 0, 0);

        // Delay the time it takes to show text
        if(delayStartingText == true) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    string1 = "We have new recruits that need to be trained.";
                    delayStartingText = false;
                }
            }, delayTime);

        }else {

            if (delayStartingText == false) {

                if (numOfChars < string1.length()) {
                    currentCharTime += delta;
                    System.out.println(" Check times " + currentCharTime + " current char time " + charTimer);
                    if (currentCharTime >= charTimer) {
                        currentCharTime = 0;
                        numOfChars++;
                    }
                }
                if ((numOfChars == string1.length()) && (numOfChars > 0)) {
                    nextFlag = true;

                }
                System.out.println("This is the substring " + string1.substring(subStrintStart, numOfChars) + "Sub start " +subStrintStart+ " numof char "+numOfChars);
                spiderV.play(); // Start playing spider voice
                font.draw(batch, string1.substring(subStrintStart, numOfChars), textStartX, 160);

            }


            if (nextFlag == true) {

                if (numOfChars2 < string2.length()) {
                    currentCharTime2 += delta;
                    if (currentCharTime2 >= charTimer) {
                        currentCharTime2 = 0;
                        numOfChars2++;
                    }
                } else {
                    nextFlag2 = true;
                }
                font.draw(batch, string2.substring(subStrintStart, numOfChars2), textStartX, 120);
            }
            if (nextFlag2 == true) {
                System.out.println("Flag2 running");
                if(string3.equals("< Press SPACE to Start >")){
                    spiderV.stop();
                    subStrintStart = 0;
                    numOfChars3 = string3.length();
                }else {
                    if (numOfChars3 < string3.length()) {
                        currentCharTime3 += delta;
                        if (currentCharTime3 >= charTimer) {
                            currentCharTime3 = 0;
                            numOfChars3++;
                        }
                    }

                    if (numOfChars3 == string3.length()) {
                        spiderV.stop(); // Text is done, stop playing spider voice

                        if(count == nextPage){

                            numOfChars = 0;
                            numOfChars2 = 0;
                            numOfChars3 = 0;
                            string1 = "Their success in foraging will bring glory to ";
                            string2 = "the Queen and Colony!";
                            string3 = "< Press SPACE to Start >";
                            nextFlag = false;
                            nextFlag2 = false;
                                //currentCharTime = 0.1f;
                            currentCharTime2 = 0;
                            currentCharTime3 = 0;


                        }else if (count > nextPage){
                            //do nothing
                        }
                        else{
                            count++;
                        }

                    }
                }
                font.draw(batch, string3.substring(subStrintStart, numOfChars3), textStartX, 80);

            }
        }






        batch.end();

        // When user hits SPACE, start the Assignment1!
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //spiderV.stop();
            this.hide();
            game.setScreen(new FadeScreen(game, this,new Game1(camera, game) ));
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
