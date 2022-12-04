package com.bugwars.Helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bugwars.Assignment2.Game2.AntPlayer;

public class AntFactory {


    private AssetManager assetMgr;
    private TextureRegion antPic;

    public AntFactory(AssetManager assetMgr){
        this.assetMgr = assetMgr;
        antPic = assetMgr.getAnt();

    }

    public AntPlayer makeAnt(){

        AntPlayer newAnt = new AntPlayer(antPic);

        return newAnt;
    }
}
