package com.bugwars.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PlayerHud {

    private float health;
    private float enemyHealth;
    private SpriteBatch hudBatch;
    private BitmapFont font;
    private String playerName = "Spider Dude";
    private String enemyName = "Evil Centipede";
    private String tempHealth = " / 100";
    private String playerNumHealth = " / 100";
    private String enemyNumHealth = " / 100";
    private int playerNum = 100;
    private int enemyNum = 100;
    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("Hud/HealthImg.atlas"));
    private TextureRegion playerHealthBarColor = new TextureRegion(textures.findRegion("Player_Health"));
    private TextureRegion enemyHealthBarColor = new TextureRegion(textures.findRegion("Enemy_Health"));
    private TextureRegion playerHealthBar = new TextureRegion(textures.findRegion("Health_Bar"));
    private TextureRegion enemyHealthBar = new TextureRegion(textures.findRegion("Health_Bar"));


    public PlayerHud(float player, float enemy) {
        health = player;
        enemyHealth = enemy;
        hudBatch = new SpriteBatch();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator

    }

    public void render(SpriteBatch batch){

        font.draw(batch, playerName, 60, 870); // Display Name
        batch.draw(playerHealthBar, 30, 800, 300, 50); // Base Health Bar
        batch.draw(playerHealthBarColor, 30, 800, 300 * (health/100), 50); // Color in Health Bar
        font.draw(batch, playerNumHealth, 60, 790); // Number Representation of Health

        font.draw(batch, enemyName, 1000, 870);// Display Name
        batch.draw(enemyHealthBar, 900, 800, 300, 50);// Base Health Bar
        batch.draw(enemyHealthBarColor, 900, 800, 300 * (enemyHealth/100), 50);// Color in Health Bar
        font.draw(batch, enemyNumHealth, 1000, 790);// Number Representation of Health



    }

    /**
     * Update and cast to string the player's and enemy's health
     * @param playerHealth
     * @param enemyHealth
     */
    public void update(float playerHealth, float enemyHealth){
        health = playerHealth;
        this.enemyHealth = enemyHealth;
        String newHealth = (int)health + tempHealth;
        String newEnemyHealth = (int)this.enemyHealth + tempHealth;
        playerNumHealth = newHealth;
        enemyNumHealth = newEnemyHealth;
    }
}
