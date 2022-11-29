package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    private TextureAtlas atlas;
    private TextureRegion dirt, ant, food, poison, water;

    public AssetManager(){

        atlas = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));

        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        ant= new TextureRegion(atlas.findRegion("Start"));
        food = new TextureRegion(atlas.findRegion("Berry"));
        poison = new TextureRegion(atlas.findRegion("Poison_Ground"));
        water = new TextureRegion(atlas.findRegion("Poison_Ground"));

    }


    public void dispose(){
        atlas.dispose();

    }
}
