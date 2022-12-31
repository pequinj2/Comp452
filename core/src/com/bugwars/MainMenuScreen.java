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
import com.bugwars.Assignment1.SpiderIntro;
import com.bugwars.Assignment2.Game1.AntIntro;
import com.bugwars.Assignment2.Game2.AntIntro2;
import com.bugwars.Assignment3.Game1.Game1;
import com.bugwars.Helper.FadeScreen;

/**
 * Main menu screen implementation. Code was modified from LibGDX tutorial
 * https://libgdx.com/wiki/start/simple-game-extended
 */
public class MainMenuScreen implements Screen {

    final BugWars game;
    private OrthographicCamera camera;
    private Texture startImg, bugWarsImg;

    private SpriteBatch pauseBatch;

    private TextButton assignment1, assignment2, assignment3, assignment2Game1, assignment2Game2,
            assignment3Game1, assignment3Game2, back, back2;
    private BitmapFont font;

    private Stage stg;
    private Table tbl, tbl2, tbl3, currentTable;
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
        tbl3 = new Table();
        stg.addActor(tbl);
        tbl.setFillParent(true);
        tbl2.setFillParent(true);
        tbl3.setFillParent(true);
        skin = new Skin();
        pauseBatch = new SpriteBatch();

        buttons = new TextureAtlas((Gdx.files.internal("Hud/Buttons.atlas")));
        skin.addRegions(buttons);

        startImg = new Texture(Gdx.files.internal("Assignment2/StartMenu.png"));
        bugWarsImg = new Texture(Gdx.files.internal("bugwarssplash.png"));

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
        assignment3 = new TextButton("Assignment 3",style);
        assignment2Game1 = new TextButton("Game 1",style);
        assignment2Game2 = new TextButton("Game 2",style);
        assignment3Game1 = new TextButton("Game 1",style);
        assignment3Game2 = new TextButton("Game 2",style);
        back = new TextButton("Back",style);
        back2 = new TextButton("Back",style);

        // The main menu buttons
        tbl.add(assignment1).width(320).padBottom(20).padTop(200);
        tbl.row();
        tbl.add(assignment2).width(320).padTop(20).padBottom(20);
        tbl.row();
        tbl.add(assignment3).width(320).padTop(20);

        tbl2.add(assignment2Game1).width(320).padBottom(20).padTop(200);
        tbl2.row();
        tbl2.add(assignment2Game2).width(320).padTop(20).padBottom(20);
        tbl2.row();
        tbl2.add(back).width(320).padTop(20);

        tbl3.add(assignment3Game1).width(320).padBottom(20).padTop(200);
        tbl3.row();
        tbl3.add(assignment3Game2).width(320).padTop(20).padBottom(20);
        tbl3.row();
        tbl3.add(back2).width(320).padTop(20);

        currentTable = tbl;

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

        assignment3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAssignment3Buttons();
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAssignment1Buttons();
            }
        });

        back2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAssignment1Buttons();
            }
        });

        assignment2Game1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                game.setScreen(new FadeScreen(game, mainMenu, new AntIntro(camera, game) ));
            }
        });

        assignment2Game2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                game.setScreen(new FadeScreen(game, mainMenu, new AntIntro2(camera, game) ));
            }
        });

        assignment3Game1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                game.setScreen(new FadeScreen(game, mainMenu, new Game1(camera, game) ));
            }
        });

        assignment3Game2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //this.hide();
                //game.setScreen(new FadeScreen(game, mainMenu, new AntIntro2(camera, game) ));
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
        game.batch.draw(startImg,0,0, 1200, 900);
        game.batch.draw(bugWarsImg,350,500, 500, 400);
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
        startImg.dispose();
        bugWarsImg.dispose();
        tbl.remove();
        tbl2.remove();
        tbl3.remove();
    }

    /**
     * Show the assignment 2 game option buttons
     */
    private void setAssignment2Buttons(){
        currentTable.remove();
        currentTable = tbl2;
        stg.addActor(tbl2);
    }

    /**
     * Show the assignment 3 game option buttons
     */
    private void setAssignment3Buttons(){
        currentTable.remove();
        currentTable = tbl3;
        stg.addActor(tbl3);
    }

    /**
     * Show assignment option buttons
     */
    private void setAssignment1Buttons() {
        currentTable.remove();
        currentTable = tbl;
        stg.addActor(tbl);
    }
}
