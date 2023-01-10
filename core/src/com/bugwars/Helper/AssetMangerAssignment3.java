package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bugwars.Assignment1.Assignment1;

/**
 * Asset manager for Assignment 3 Games
 */
public class AssetMangerAssignment3 {

    private TextureAtlas atlas;
    private TextureRegion antProfile, spiderProfile, antDisk, spiderDisk, profileGlowAnt, profileGlowSpider,
            gridGlow, gameBoard;
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
        //gridGlow = new TextureRegion(atlas.findRegion("Berry"));


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
}
