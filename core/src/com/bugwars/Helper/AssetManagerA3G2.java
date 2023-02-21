package com.bugwars.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManagerA3G2 {

    private TextureAtlas bossAtlas, worldAsset;
    private TextureRegion centipedeHead, centipedeBody, centipedeTail, beam, beamEnd, profileGlowSpider,
            gameBoard, yellowDisk;

    public Animation<TextureRegion> bossHeadAnimation;
    private Sound tailAttack, lungeAttack, spiderWin, beamAttack, webPickup, aoeAttack;
    private Music bossMusic;

    public AssetManagerA3G2(){
        bossAtlas = new TextureAtlas((Gdx.files.internal("Assignment3/Game2/Boss.atlas")));
        beamEnd = new TextureRegion(bossAtlas.findRegion("BeamEnd"));
        beam = new TextureRegion(bossAtlas.findRegion("Beam"));

        tailAttack = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/fetus_flyingwhoosh.mp3"));
        lungeAttack = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/Maggot_Enter_Ground_1.mp3"));
        spiderWin = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/isaacbosswin.mp3"));
        beamAttack = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/Boss_Spit_Blob_Barf.mp3"));
        webPickup = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/plop.mp3"));
        aoeAttack = Gdx.audio.newSound(Gdx.files.internal("Assignment3/Game2/Sounds/Monster_Roar_2.mp3"));

        bossMusic =  Gdx.audio.newMusic(Gdx.files.internal("Assignment3/Game2/Sounds/Tool - Vicarious (8 Bit).mp3"));


    }

    /**
     * Construct the animation for the ant moving
     * @return
     */
    public Animation<TextureRegion> bossHeadAnimation() {
        bossHeadAnimation = new Animation<TextureRegion>(0.33f, bossAtlas.findRegions("Head"), Animation.PlayMode.LOOP);

        return bossHeadAnimation;
    }

    public TextureRegion getBeamEnd(){
        return beamEnd;
    }

    public TextureRegion getBeam(){
        return beam;
    }

    public Sound getTailAttack(){
        return tailAttack;
    }

    public Sound getLungeAttack(){
        return lungeAttack;
    }

    public Sound getSpiderWin(){
        return spiderWin;
    }

    public Sound getBeamAttack(){
        return beamAttack;
    }

    public Sound getWebPickup(){
        return webPickup;
    }

    public Sound getAoeAttack(){
        return aoeAttack;
    }

    public Music getBossMusic(){
        return bossMusic;
    }
}
