package com.bugwars.Assignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseMenu {

    private Texture bugWarsImage, endGameBackground;
    private SpriteBatch pauseBatch;

    private TextButton retry, quit;
    private BitmapFont font;

    private Stage stg;
    private Table tbl;
    private Skin skin;

    public PauseMenu(){
        stg = new Stage();
        tbl = new Table();
        stg.addActor(tbl);
        tbl.setDebug(true);
        tbl.setFillParent(true);
        skin = new Skin();
        pauseBatch = new SpriteBatch();

        bugWarsImage = new Texture(Gdx.files.internal("Hud/Pause_Menu.png"));
        endGameBackground = new Texture(Gdx.files.internal("Hud/Blank_Background.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        retry = new TextButton("Retry",style);
        quit = new TextButton("Quit",style);
        tbl.add(retry).width(150).padRight(20);
        tbl.add(quit).width(150).padLeft(20);


    }


    public void render(){
        pauseBatch.begin();
        pauseBatch.draw(bugWarsImage, 150, 150, 300, 300);
        pauseBatch.end();
    }

    public void endScreenMenu(){
        pauseBatch.begin();
        pauseBatch.draw(endGameBackground, 260, 130, 700, 700);
        pauseBatch.end();
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
    }
}
