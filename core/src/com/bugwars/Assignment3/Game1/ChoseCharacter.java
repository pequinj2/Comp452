package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.bugwars.BugWars;
import com.bugwars.Helper.FadeScreen;

public class ChoseCharacter implements Screen {

    private int chosenCharacter;
    private Texture antAndSpiderImg, boarderImg, chracterHighlight;
    private TextureRegion characters, chracterHighlightBoarder;
    private BugWars game;
    private OrthographicCamera camera;
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font;
    private String choseChar = "Press <a> or <d> to select character, when the character you want is highlighted press <SPACE> to begin.";
    private String choseChar2 = "Note Ant will always go first.";
    private int highlightX = 0;
    private int highlightY = 100;


    public ChoseCharacter(OrthographicCamera camera, BugWars game){
        this.camera = camera;
        this.game = game;
        chosenCharacter = 0; // Default to the ant

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator

        antAndSpiderImg = new Texture(Gdx.files.internal("Assignment3/AntSpiderPic.png"));
        chracterHighlight = new Texture(Gdx.files.internal("Assignment3/CharacterHighlight.png"));
        chracterHighlightBoarder = new TextureRegion(chracterHighlight, 0, 100, 1200, 800);
        characters = new TextureRegion(antAndSpiderImg, highlightX, highlightY, 1200, 800);
        boarderImg = new Texture(Gdx.files.internal("TextBoarder.png"));

    }
    @Override
    public void show() {

    }

    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            // Highlight ant
            chosenCharacter = 0;
            highlightX = 0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            // Highlight spider
            chosenCharacter = 1;
            highlightX = 600;

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            // Select character
            game.setScreen(new FadeScreen(game, this, new Game1(camera, game, chosenCharacter) ));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(characters, 0, 200);
        batch.draw(boarderImg, 0, 0);
        batch.draw(chracterHighlightBoarder, highlightX, 200);
        font.draw(batch,choseChar,40,160);
        font.draw(batch,choseChar2,40,120);
        batch.end();
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
