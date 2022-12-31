package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Asset manager for Assignment 3 Games
 */
public class AssetMangerAssignment3 {

    private TextureAtlas atlas;
    private TextureRegion antProfile, spiderProfile, gameBoard, whiteDisk, blackDisk, profileGlow,
            gridGlow;
    private Skin skin = new Skin();

    public AssetMangerAssignment3(){

        atlas = new TextureAtlas((Gdx.files.internal("Assignment3/GameOneTiles.atlas")));

        antProfile = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        spiderProfile = new TextureRegion(atlas.findRegion("Start"));
        gameBoard = new TextureRegion(atlas.findRegion("AntBerry"));
        whiteDisk = new TextureRegion(atlas.findRegion("AntWater"));
        blackDisk = new TextureRegion(atlas.findRegion("Berry"));
        profileGlow = new TextureRegion(atlas.findRegion("Berry"));
        gridGlow = new TextureRegion(atlas.findRegion("Berry"));


    }
}
