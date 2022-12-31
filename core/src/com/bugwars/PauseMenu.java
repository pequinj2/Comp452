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
import com.bugwars.Assignment1.Assignment1;
import com.bugwars.Assignment2.Game1.Game1;
import com.bugwars.Assignment2.Game2.Game2;
import com.bugwars.BugWars;
import com.bugwars.Helper.FadeScreen;
import com.bugwars.MainMenuScreen;

/**
 * Pause menu and also end game menu that will have the buttons 'Retry and 'Quit'
 */
public class PauseMenu {

    private Texture bugWarsImage, endGameBackground;
    private SpriteBatch pauseBatch;

    private TextButton retry, quit, mainMenuReturn;
    private BitmapFont font;

    private Stage stg;
    private Table tbl;
    private Skin skin;

    private TextureAtlas buttons;

    private BugWars game;
    private OrthographicCamera camera;
    private Screen assign;




    public PauseMenu(final Screen assign, final BugWars game, final OrthographicCamera camera){
        this.camera = camera;
        this.game = game;
        this.assign = assign;
        stg = new Stage();
        tbl = new Table();
        stg.addActor(tbl);
        //tbl.setDebug(true);
        tbl.setFillParent(true);
        skin = new Skin();
        pauseBatch = new SpriteBatch();

        bugWarsImage = new Texture(Gdx.files.internal("Hud/Pause_Menu.png"));
        endGameBackground = new Texture(Gdx.files.internal("Hud/Blank_Background.png"));
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

        retry = new TextButton("Retry",style);
        quit = new TextButton("Quit",style);
        mainMenuReturn = new TextButton("Main Menu",style);
        tbl.add(retry).width(150).left();
        tbl.add(quit).width(150).left();
        tbl.row();
        tbl.add(mainMenuReturn).width(250).padTop(20);
    }


    public void render(){
        Gdx.input.setInputProcessor(stg);
        pauseBatch.begin();
        pauseBatch.draw(bugWarsImage, 260, 130, 700, 700);
        pauseBatch.end();
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
    }

    public void endScreenMenu(){
        pauseBatch.begin();
        pauseBatch.draw(endGameBackground, 260, 130, 700, 700);
        pauseBatch.end();
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
    }

    public void dispose(){
        bugWarsImage.dispose();
        endGameBackground.dispose();
        buttons.dispose();
    }


    public void assignment1Listeners(){
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new Assignment1(camera, game) ));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        mainMenuReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new MainMenuScreen(game)));
            }
        });
    }

    public void assignment2Game1Listeners(){
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new Game1(camera, game) ));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        mainMenuReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new MainMenuScreen(game)));
            }
        });
    }


    public void assignment2Game2Listeners() {
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new Game2(camera, game) ));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        mainMenuReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new MainMenuScreen(game)));
            }
        });
    }

    public void assignment3Game1Listeners(){
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new Game1(camera, game) ));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        mainMenuReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new MainMenuScreen(game)));
            }
        });
    }


    public void assignment3Game2Listeners() {
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new Game2(camera, game) ));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        mainMenuReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FadeScreen(game, assign , new MainMenuScreen(game)));
            }
        });
    }
}
