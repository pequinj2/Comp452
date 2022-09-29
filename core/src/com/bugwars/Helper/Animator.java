package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
    // Objects used
    Animation<TextureRegion> spiderAnimation; // Must declare frame type (TextureRegion)
    Texture walk;
    TextureAtlas atlas;

    public Animation<TextureRegion> walkingAnimation;


    public Animation<TextureRegion> Animator() {
        setAtlas();

        walkingAnimation = new Animation<TextureRegion>(0.33f, atlas.findRegions("SpiderSprite"), Animation.PlayMode.LOOP);

        return walkingAnimation;
    }

    public void setAtlas(){
        this.atlas = new TextureAtlas(Gdx.files.internal("maps/SpiderWalk.atlas"));

    }

}
