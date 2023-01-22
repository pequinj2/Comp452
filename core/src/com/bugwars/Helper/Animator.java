package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ***** SELF NOTE *****
 * Dont initialize all these separately just put them all in one class - may need to change
 */

public class Animator {

    private TextureAtlas atlas;

    public Animation<TextureRegion> walkingAnimation;
    public Animation<TextureRegion> centipedeMouthAnimation, antAnimation;
    public Animation<TextureRegion> endGameTextures;


    public Animation<TextureRegion> AnimatorSpider() {
        setSpiderAtlas();

        walkingAnimation = new Animation<TextureRegion>(0.33f, atlas.findRegions("SpiderSprite"), Animation.PlayMode.LOOP);

        return walkingAnimation;
    }

    public void setSpiderAtlas(){
        this.atlas = new TextureAtlas(Gdx.files.internal("maps/SpiderWalk.atlas"));

    }

    /**
     * Construct the animation for the centipedes mouth moving
     * @return
     */
    public Animation<TextureRegion> CentipedeMouthAnimator() {
        setCentipedeMouthAtlas();

        centipedeMouthAnimation = new Animation<TextureRegion>(0.33f, atlas.findRegions("Centipede_Head"), Animation.PlayMode.LOOP);

        return centipedeMouthAnimation;
    }

    /**
     * Create the animation atlas for Centipede mouth moving
     */
    public void setCentipedeMouthAtlas(){
        this.atlas = new TextureAtlas(Gdx.files.internal("maps/CentipedeHead.atlas"));

    }

    /**
     * Small end game animation for when the player wins
     * @return
     */
    public Animation<TextureRegion> winGameAnimator() {
        this.atlas = new TextureAtlas(Gdx.files.internal("End Game/EndState.atlas"));
        endGameTextures = new Animation<TextureRegion>(0.33f, atlas.findRegions("You_Win_Happy"), Animation.PlayMode.LOOP);

        return endGameTextures;
    }

    /**
     * Small end game animation for when the player loses
     * @return
     */
    public Animation<TextureRegion> loseGameAnimator() {
        this.atlas = new TextureAtlas(Gdx.files.internal("End Game/EndState.atlas"));
        endGameTextures = new Animation<TextureRegion>(0.33f, atlas.findRegions("You_Died_Ghost"), Animation.PlayMode.LOOP);

        return endGameTextures;
    }

    //*****************************************************************************************
    // ASSIGNMENT 3 GAME 2 UPDATE

    /**
     * Construct the animation for the ant moving
     * @return
     */
    public Animation<TextureRegion> antAnimator() {
        setAntAtlas();
        antAnimation = new Animation<TextureRegion>(0.33f, atlas.findRegions("Ant"), Animation.PlayMode.LOOP);

        return antAnimation;
    }

    /**
     * Create the animation atlas for ant moving
     */
    public void setAntAtlas(){
        this.atlas = new TextureAtlas(Gdx.files.internal("Assignment3/Ant.atlas"));

    }


    public void dispose(){
        atlas.dispose();

    }
}
