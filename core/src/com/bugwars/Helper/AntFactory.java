package com.bugwars.Helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;

import java.awt.Point;

/**
 * Factory class that will initiate the ant generation for the simulation
 */
public class AntFactory {

    private AssetManager assetMgr;
    private TextureRegion antPic;
    private Point antHillHome;

    public AntFactory(AssetManager assetMgr){
        this.assetMgr = assetMgr;
        antPic = assetMgr.getAnt();

    }

    public AntPlayer makeAnt(){

        System.out.println("Made an Ant");
        AntPlayer newAnt = new AntPlayer();
        newAnt.Awake();

        return newAnt;
    }

    public void setAntHillHome(Point hillHome){
        antHillHome = hillHome;

    }
}
