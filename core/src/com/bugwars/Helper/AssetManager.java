package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * This manager will load all textures that will be required for Assignment2 Game 2 to lower the
 * performance cost in uploading the textures within the different classes. One asset manager will
 * be loaded when Game 2 starts and its reference will be passed to the other classes as needed.
 */
public class AssetManager {

    private TextureAtlas atlas, textFieldAtlas;
    private TextureRegion dirt, ant, antBerry, antWater, food, poison, water, antHill;
    private Skin skin = new Skin();
    private Skin skin2 = new Skin();

    public AssetManager(){

        atlas = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));
        textFieldAtlas = new TextureAtlas((Gdx.files.internal("Hud/Buttons.atlas")));
        skin.addRegions(textFieldAtlas);

        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        ant= new TextureRegion(atlas.findRegion("Start"));
        antBerry= new TextureRegion(atlas.findRegion("AntBerry"));
        antWater= new TextureRegion(atlas.findRegion("AntWater"));
        food = new TextureRegion(atlas.findRegion("Berry"));
        poison = new TextureRegion(atlas.findRegion("Poison_Ground"));
        water = new TextureRegion(atlas.findRegion("Water_Tile"));
        antHill = new TextureRegion(atlas.findRegion("Anthill"));

    }

    /**
     *  All of the below will return there respective textures
     */

    public TextureRegion getDirt() {
        return dirt;
    }

    public TextureRegion getAnt() {
        return ant;
    }

    public TextureRegion getAntBerry() {
        return antBerry;
    }

    public TextureRegion getAntWater() {
        return antWater;
    }

    public TextureRegion getFood() {
        return food;
    }

    public TextureRegion getPoison() {
        return poison;
    }

    public TextureRegion getWater() {
        return water;
    }

    public TextureRegion getAntHill(){
        return antHill;
    }

    public Drawable getSkin() {
        return skin.getDrawable("Button_Up");
    }

    public void dispose(){
        atlas.dispose();

    }


}
