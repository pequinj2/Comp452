package com.bugwars.Helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bugwars.Assignment2.Game2.CreateScene;
import com.bugwars.Assignment2.Game2.Game2;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;

/**
 * Factory class that will initiate the ant generation for the simulation
 */
public class AntFactory {

    private AssetManager assetMgr;
    private TextureRegion antPic, antPicBerry, antPicWater;
    private int antHillHome;
    private CreateScene map;
    private int count = 0;

    public AntFactory(AssetManager assetMgr, CreateScene map){
        this.assetMgr = assetMgr;
        this.map = map;
        antPic = assetMgr.getAnt();
        antPicBerry = assetMgr.getAntBerry();
        antPicWater = assetMgr.getAntWater();

    }

    /**
     * Create a new ant and pass the Ant Hill cell ID to it
     * @return
     * @param game2
     */
    public AntPlayer makeAnt(Game2 game2){

        AntPlayer newAnt = new AntPlayer();
        count++;
        newAnt.Awake(antHillHome, antPic, antPicBerry, antPicWater, map, count, game2);

        return newAnt;
    }

    /**
     * When created, set the Ant Hill home location
     * @param hillHome
     */
    public void setAntHillHome(int hillHome){
        antHillHome = hillHome;

    }
}
