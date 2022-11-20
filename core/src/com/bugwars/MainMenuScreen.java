package com.bugwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bugwars.Assignment1.Assignment1;
import com.bugwars.Assignment1.SpiderIntro;
import com.bugwars.Assignment2.Game1.Game1;
import com.bugwars.Helper.FadeScreen;

/**
 * Main menu screen implementation. Code was modified from LibGDX tutorial
 * https://libgdx.com/wiki/start/simple-game-extended
 */
public class MainMenuScreen implements Screen {

    final BugWars game;
    private OrthographicCamera camera;

    private SpriteBatch pauseBatch;

    private TextButton assignment1, assignment2, assignment2Game1, assignment2Game2, back;
    private BitmapFont font;

    private Stage stg;
    private Table tbl, tbl2;
    private Skin skin;
    private TextureAtlas buttons;

    public MainMenuScreen mainMenu;

    public MainMenuScreen (final BugWars game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 900);
        mainMenu = this;
        stg = new Stage();
        tbl = new Table();
        tbl2 = new Table();
        stg.addActor(tbl);
        tbl.setDebug(true);
        tbl.setFillParent(true);
        tbl2.setDebug(true);
        tbl2.setFillParent(true);
        skin = new Skin();
        pauseBatch = new SpriteBatch();

        buttons = new TextureAtlas((Gdx.files.internal("Hud/Buttons.atlas")));
        skin.addRegions(buttons);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = skin.getDrawable("Button_Up");
        style.down = skin.getDrawable("Button_Down");

        assignment1 = new TextButton("Assignment 1",style);
        assignment2 = new TextButton("Assignment 2",style);
        assignment2Game1 = new TextButton("Game 1",style);
        assignment2Game2 = new TextButton("Game 2",style);
        back = new TextButton("Back",style);

        // The main menu buttons
        tbl.add(assignment1).width(320).padBottom(20);
        tbl.row();
        tbl.add(assignment2).width(320).padTop(20);

        tbl2.add(assignment2Game1).width(320).padBottom(20);
        tbl2.row();
        tbl2.add(assignment2Game2).width(320).padTop(20).padBottom(20);
        tbl2.row();
        tbl2.add(back).width(320).padTop(20);



        assignment1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                game.setScreen(new FadeScreen(game, mainMenu, new SpiderIntro(camera, game) ));
            }
        });

        assignment2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAssignment2Buttons();
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAssignment1Buttons();
            }
        });

        assignment2Game1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                game.setScreen(new FadeScreen(game, mainMenu, new Game1(camera, game) ));
            }
        });

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        Gdx.input.setInputProcessor(stg);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Bug Wars!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();


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
        buttons.dispose();
    }

    /**
     * Show the assignment 2 game option buttons
     */
    private void setAssignment2Buttons(){
        tbl.remove();
        stg.addActor(tbl2);
    }

    /**
     * Show assignment option buttons
     */
    private void setAssignment1Buttons() {
        tbl2.remove();
        stg.addActor(tbl);
    }
}
