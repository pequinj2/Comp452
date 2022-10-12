package com.bugwars.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PlayerHud {

    private float health;
    private float enemyHealth;
    private SpriteBatch hudBatch;
    private Texture playerHealthBar, enemyHealthBar;
    private BitmapFont font;
    private String playerName = "Spider Dude";
    private String enemyName = "Evil Centipede";


    public PlayerHud(float player, float enemy) {
        health = player;
        enemyHealth = enemy;
        hudBatch = new SpriteBatch();
        playerHealthBar = new Texture(Gdx.files.internal("Hud/playerHealthBar.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

    }

    public void render(SpriteBatch batch){
        font.draw(batch, playerName, 30, 870);
        batch.draw(playerHealthBar, 30, 830, health, 20);

        font.draw(batch, enemyName, 30, 140);
        //batch.draw(enemyHealthBar, 30, 120, enemyHealth, 20);



    }

    public void update(float playerHealth, float enemyHealth){
        health = playerHealth;
        this.enemyHealth = enemyHealth;
    }
}
