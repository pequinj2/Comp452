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
    private TextureRegion antProfile, spiderProfile, antDisk, spiderDisk, profileGlowAnt, profileGlowSpider,
             gameBoard, yellowDisk;
    private Skin skin = new Skin();


    public AssetMangerAssignment3(){

        atlas = new TextureAtlas((Gdx.files.internal("Assignment3/ConnectFour.atlas")));

        antProfile = new TextureRegion(atlas.findRegion("AntProfile"));
        spiderProfile = new TextureRegion(atlas.findRegion("SpiderProfile"));
        gameBoard = new TextureRegion(atlas.findRegion("connect4"));
        antDisk = new TextureRegion(atlas.findRegion("DiskPink"));
        spiderDisk = new TextureRegion(atlas.findRegion("DiskGreen"));
        profileGlowAnt = new TextureRegion(atlas.findRegion("ProfilesGlowP"));
        profileGlowSpider = new TextureRegion(atlas.findRegion("ProfilesGlowG"));
        yellowDisk = new TextureRegion(atlas.findRegion("DiskYellow"));


    }


    /**
     * Return Game board
     * @return
     */
    public TextureRegion getGameBoard(){
        return gameBoard;
    }

    /**
     * @return
     */
    public TextureRegion getSpiderProfile(){
        return spiderProfile;
    }

    /**
     * @return
     */
    public TextureRegion getAntProfile(){
        return antProfile;
    }

    /**
     * @return
     */
    public TextureRegion getSpiderDisk(){
        return spiderDisk;
    }

    /**
     * @return
     */
    public TextureRegion getAntDisk(){
        return antDisk;
    }

    /**
     * @return
     */
    public TextureRegion getSpiderGlow(){
        return profileGlowSpider;
    }

    /**
     * @return
     */
    public TextureRegion getAntGlow(){
        return profileGlowAnt;
    }

    /**
     * @return
     */
    public TextureRegion getEndPiece(){
        return yellowDisk;
    }


}
