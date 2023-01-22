package com.bugwars.Assignment3.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Hud {
    private BitmapFont font;
    private int count = 0;
    private String score;
    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("Hud/HealthImg.atlas"));
    private TextureRegion enemyHealthBarColor = new TextureRegion(textures.findRegion("Enemy_Health"));
    private TextureRegion enemyHealthBar = new TextureRegion(textures.findRegion("Health_Bar"));
    private String enemyNumHealth = " / 100";
    private float enemyHealth;
    private String tempHealth = " / 100";

    public Hud(int enemy){

        enemyHealth = enemy;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // Once font is generated dispose of the generator


    }

    public void render(SpriteBatch hudBatch) {
        font.draw(hudBatch, "Score:  ", 60, 870);// Display Name
        font.draw(hudBatch, Integer.toString(count), 220, 870);// Number Representation of Health
        hudBatch.draw(enemyHealthBar, 900, 800, 300, 50);// Base Health Bar
        hudBatch.draw(enemyHealthBarColor, 900, 800, 300 * (enemyHealth/100), 50);// Color in Health Bar
        font.draw(hudBatch, enemyNumHealth, 950, 780);// Number Representation of Health
    }

    public void update(float enemyHealth) {
        this.enemyHealth = enemyHealth;
        if (this.enemyHealth < 0){
            this.enemyHealth = 0;
        }
        String newEnemyHealth = this.enemyHealth + tempHealth;
        enemyNumHealth = newEnemyHealth;

    }

    public void incrementCount(){
        count++;
    }
}
