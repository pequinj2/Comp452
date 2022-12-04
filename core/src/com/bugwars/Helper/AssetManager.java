package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AssetManager {

    private TextureAtlas atlas, textFieldAtlas;
    private TextureRegion dirt, ant, food, poison, water, antHill;
    private Skin skin = new Skin();
    private Skin skin2 = new Skin();

    public AssetManager(){

        atlas = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));
        textFieldAtlas = new TextureAtlas((Gdx.files.internal("Hud/Buttons.atlas")));
        skin.addRegions(textFieldAtlas);
        skin2.add("Button_Up", skin.getDrawable("Button_Up"));

        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        ant= new TextureRegion(atlas.findRegion("Start"));
        food = new TextureRegion(atlas.findRegion("Berry"));
        poison = new TextureRegion(atlas.findRegion("Poison_Ground"));
        water = new TextureRegion(atlas.findRegion("Water_Tile"));
        antHill = new TextureRegion(atlas.findRegion("Anthill"));

    }


    public TextureRegion getDirt() {
        return dirt;
    }

    public TextureRegion getAnt() {
        return ant;
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

    public void dispose(){
        atlas.dispose();

    }

    public Drawable getSkin() {
        return skin.getDrawable("Button_Up");
    }
}
