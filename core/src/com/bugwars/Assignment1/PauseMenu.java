package com.bugwars.Assignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {

    private Texture bugWarsImage;
    private SpriteBatch pauseBatch;

    public PauseMenu(){
        pauseBatch = new SpriteBatch();
        bugWarsImage = new Texture(Gdx.files.internal("bugwarssplash2.png"));

    }


    public void render(){
        pauseBatch.begin();
        pauseBatch.draw(bugWarsImage, 150, 150, 300, 300);
        pauseBatch.end();
    }
}
