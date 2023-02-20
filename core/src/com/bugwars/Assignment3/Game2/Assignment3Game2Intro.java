package com.bugwars.Assignment3.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.Game1.ChoseCharacter;
import com.bugwars.BugWars;
import com.bugwars.Helper.FadeScreen;

public class Assignment3Game2Intro implements Screen {

    private Texture centipedeImg, boarderImg;
    private TextureRegion bossImg;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BugWars game;
    private TextureAtlas atlas;
    private TextureRegion antProfile, spiderProfile;

    // Text
    private int switchText = 0;
    private int delayTime = 2;
    private int nextPage = 100;
    private int count = 0;
    private boolean delayStartingText = true;
    private int numOfChars = 0;
    private int numOfChars2 = 0;
    private int numOfChars3 = 0;
    private float charTimer = 0.1f; // Time between text character appearance
    private float currentCharTime = 0;
    private float currentCharTime2 = 0;
    private float currentCharTime3 = 0;
    private int textStartX = 40;
    private int subStringStart = 0;
    private boolean nextFlag = false;
    private boolean nextFlag2 = false;
    private boolean textFinished = false;
    private BitmapFont spaceFont;

    // Ant Text
    private BitmapFont antFont;
    private String antText = "First the spider now you! All of you leave at once!";
    //private String antText1 = "Leave or else!!";
    //private String antText2 = "Umph, your arrogance will be you downfall. I ";
    //private String antText3 = "challenge you to a game.";
    // Ant audio
    private Music antV;

    // Spider Text
    private BitmapFont spiderFont;
    private String spiderText = "This time I'll make sure you're dead..";
    //private String spiderText1 = "I accept drudge..";
    // Spider audio
    private Music spiderV;

    private String space = "< Press SPACE to Start >";

    // Centipede Text
    private BitmapFont centipedeFont;
    private String centText = "Silence pions!! ";
    private String centText1 = "I will not be spoken to in this manner!";
    private String centText2 = "I won't be so easily defeated this time...";
    private String centText3 = "Filthy pests... do you think I could be defeated";
    private String centText4 = "so easily?!?";

    private Music centV;

    public Assignment3Game2Intro(OrthographicCamera camera, BugWars game){
        this.camera = camera;
        this.game = game;
        boarderImg = new Texture(Gdx.files.internal("TextBoarder.png"));
        batch = new SpriteBatch();

        atlas = new TextureAtlas((Gdx.files.internal("Assignment3/ConnectFour.atlas")));

        antProfile = new TextureRegion(atlas.findRegion("AntProfile"));
        spiderProfile = new TextureRegion(atlas.findRegion("SpiderProfile"));

        centipedeImg = new Texture(Gdx.files.internal("Assignment3/Game2/Centipede.png"));
        bossImg = new TextureRegion(centipedeImg, 0, 0, 1200, 800);
        antV = Gdx.audio.newMusic(Gdx.files.internal("Assignment2/AntInto/AntIntro.wav"));
        antV.setLooping(true);
        spiderV = Gdx.audio.newMusic(Gdx.files.internal("Sound/SpiderIntro/untitled #85 -2.wav"));
        spiderV.setLooping(true);
        centV = Gdx.audio.newMusic(Gdx.files.internal("Assignment3/Game2/Sounds/CentipedeVoice.wav"));
        centV.setLooping(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        Color antColor = new Color(255f/255f, 43f/255f, 145f/255f, 255f/255f);
        antFont = generator.generateFont(parameter);
        antFont.setColor(antColor);

        Color spiderColor = new Color(42f/255f, 251f/255f, 62f/255f, 255f/255f);
        spiderFont = generator.generateFont(parameter);
        spiderFont.setColor(spiderColor);

        Color centipedeColor = new Color(255f/255f, 165f/255f, 0f/255f, 255f/255f);
        centipedeFont = generator.generateFont(parameter);
        centipedeFont.setColor(centipedeColor);

        //Color spaceColor = new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f);
        spaceFont = generator.generateFont(parameter);
        //spiderFont.setColor(spiderColor);

        generator.dispose(); // Once font is generated dispose of the generator




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
        batch.draw(bossImg, 0, 200);
        batch.draw(boarderImg, 0, 0);

        // Delay the time it takes to show text
        if(delayStartingText == true) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //string1 = "Now that the recruits are trained,";
                    delayStartingText = false;
                }
            }, delayTime);

        }else {
            if (delayStartingText == false) {

                switch(switchText){
                    case 0:
                        // Centipede starts
                        centipedeText2(centText3,centText4, delta);
                        break;
                    case 1:
                        // Ant replies
                        centV.stop();
                        batch.draw(antProfile, 0,220,252,375);
                        antText2(antText, delta);
                        break;
                    case 2:
                        // Spider challenges
                        antV.stop();
                        batch.draw(spiderProfile, 948,220,252,375);
                        spiderText(spiderText, delta);
                        break;
                    case 3:
                        // Centipede accepts
                        spiderV.stop();
                        centipedeText(centText, centText1,centText2, delta);
                        break;
                    case 4:
                        centV.stop();
                        spaceText(delta);
                        // Press Space to start
                        break;
                }

            }
        }

        batch.end();

        // When user hits SPACE, start the Assignment1!
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            spiderV.stop();
            antV.stop();
            centV.stop();
            this.hide();
            game.setScreen(new FadeScreen(game, this, new Game2(camera, game) ));
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
        antV.dispose();
        spiderV.dispose();
        centV.dispose();
        centipedeImg.dispose();
        boarderImg.dispose();
        batch.dispose();

    }

    private void centipedeText(String text, String text1, String text2, float delta){
        if (numOfChars < text.length()) {
            currentCharTime += delta;

            if (currentCharTime >= charTimer) {
                currentCharTime = 0;
                numOfChars++;
            }
        }
        if ((numOfChars == text.length()) && (numOfChars > 0)) {
            nextFlag = true;

        }

        if(!textFinished){
            centV.play(); // Start playing ant voice
        }

        centipedeFont.draw(batch, text.substring(subStringStart, numOfChars), textStartX, 160);




        if (nextFlag == true) {

            if (numOfChars2 < text1.length()) {
                currentCharTime2 += delta;
                if (currentCharTime2 >= charTimer) {
                    currentCharTime2 = 0;
                    numOfChars2++;
                }
            }
            if ((numOfChars2 == text1.length()) && (numOfChars2 > 0)) {
                nextFlag2 = true;

            }

            centipedeFont.draw(batch, text1.substring(subStringStart, numOfChars2), textStartX, 120);
        }

        if (nextFlag2 == true) {

            if (numOfChars3 < text2.length()) {
                currentCharTime3 += delta;
                if (currentCharTime3 >= charTimer) {
                    currentCharTime3 = 0;
                    numOfChars3++;
                }
            } else {
                centV.stop();
                textFinished=true;
                if(currentCharTime3 < 2){
                    currentCharTime3 += delta;
                }else{
                    nextFlag = false;
                    switchText++;
                    numOfChars =0;
                    currentCharTime3=0;
                    textFinished=false;
                    numOfChars2=0;
                    currentCharTime2=0;
                }

            }
            centipedeFont.draw(batch, text2.substring(subStringStart, numOfChars3), textStartX, 80);
        }

    }

    public void centipedeText2(String text1, String text2, float delta){
        if (numOfChars < text1.length()) {
            currentCharTime += delta;

            if (currentCharTime >= charTimer) {
                currentCharTime = 0;
                numOfChars++;
            }
        }
        if ((numOfChars == text1.length()) && (numOfChars > 0)) {
            nextFlag = true;

        }

        if(!textFinished){
            centV.play(); // Start playing ant voice
        }

        centipedeFont.draw(batch, text1.substring(subStringStart, numOfChars), textStartX, 160);




        if (nextFlag == true) {

            if (numOfChars2 < text2.length()) {
                currentCharTime2 += delta;
                if (currentCharTime2 >= charTimer) {
                    currentCharTime2 = 0;
                    numOfChars2++;
                }
            } else {
                centV.stop();
                textFinished=true;
                if(currentCharTime3 < 2){
                    currentCharTime3 += delta;
                }else{
                    nextFlag = false;
                    switchText++;
                    numOfChars =0;
                    currentCharTime=0;
                    currentCharTime3 = 0;
                    textFinished=false;
                    numOfChars2=0;
                    currentCharTime2=0;
                }

            }
            centipedeFont.draw(batch, text2.substring(subStringStart, numOfChars2), textStartX, 120);
        }
    }

    public void spiderText(String spiderText, float delta){
        if (numOfChars < spiderText.length()) {
            currentCharTime += delta;

            if (currentCharTime >= charTimer) {
                currentCharTime = 0;
                numOfChars++;
            }
        }
        if ((numOfChars == spiderText.length()) && (numOfChars > 0)) {
            textFinished = true;

        }

        if(!textFinished){
            spiderV.play(); // Start playing ant voice
        }
        else{
            spiderV.stop();
            textFinished=true;
            if(currentCharTime3 < 2){
                currentCharTime3 += delta;
            }else{
                //nextFlag2 = true;
                switchText++;
                numOfChars =0;
                currentCharTime3=0;
                textFinished=false;
                currentCharTime = 0;
            }
        }

        spiderFont.draw(batch, spiderText.substring(subStringStart, numOfChars), textStartX, 160);

    }

    public void antText2(String text, float delta){
        if (numOfChars < text.length()) {
            currentCharTime += delta;

            if (currentCharTime >= charTimer) {
                currentCharTime = 0;
                numOfChars++;
            }
        }
        if ((numOfChars == text.length()) && (numOfChars > 0)) {
            textFinished = true;

        }

        if(!textFinished){
            antV.play(); // Start playing ant voice

        }else{
            antV.stop();
            if(currentCharTime3 < 2){
                currentCharTime3 += delta;
            }else{
                nextFlag = false;
                switchText++;
                numOfChars =0;
                currentCharTime3=0;
                textFinished=false;
                numOfChars2=0;
                currentCharTime2=0;
            }
        }

        antFont.draw(batch, text.substring(subStringStart, numOfChars), textStartX, 160);


    }

    public void spaceText(float delta){

        spaceFont.draw(batch, space, textStartX, 80);
    }
}