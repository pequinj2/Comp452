package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManagerA3G2 {

    private TextureAtlas bossAtlas, worldAsset;
    private TextureRegion centipedeHead, centipedeBody, centipedeTail, beam, beamEnd, profileGlowSpider,
            gameBoard, yellowDisk;

    public Animation<TextureRegion> bossHeadAnimation;

    public AssetManagerA3G2(){
        bossAtlas = new TextureAtlas((Gdx.files.internal("Assignment3/Game2/Boss.atlas")));

    }

    /**
     * Construct the animation for the ant moving
     * @return
     */
    public Animation<TextureRegion> bossHeadAnimation() {
        bossHeadAnimation = new Animation<TextureRegion>(0.33f, bossAtlas.findRegions("Head"), Animation.PlayMode.LOOP);

        return bossHeadAnimation;
    }
}
