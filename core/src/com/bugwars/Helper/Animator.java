package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ***** SELF NOTE *****
 * Dont initialize all these seperatly just put them all in one class - may need to change
 */

public class Animator {
    // Objects used
    Animation<TextureRegion> spiderAnimation; // Must declare frame type (TextureRegion)
    Texture walk;
    TextureAtlas atlas;

    public Animation<TextureRegion> walkingAnimation;
    public Animation<TextureRegion> centipedeMouthAnimation;


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

}
