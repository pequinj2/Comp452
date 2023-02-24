package com.bugwars.Assignment3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Helper.AssetManagerA3G2;
import com.bugwars.Objects.Player.Spider;

/**
 * Object class that initializes the Centipede's beam shot, moves it, renders it and checks
 * for collision. This simple collision was done instead of Box 2d because the beam shape will
 * change shape was it is being shot - box2D does not like bodies changing after they've been
 * initialized.
 */
public class BeamObject {

    private AssetManagerA3G2 assetMgr;
    private TextureRegion end, beam;
    private Rectangle rectangle;
    public float startingX, startingY, height;
    public boolean beamRunning = false;
    private int speedOfBeam = 4;
    private Spider spiderPlayer;

    public BeamObject(AssetManagerA3G2 asstMgr, Spider spiderPlayer){
        this.assetMgr = asstMgr;
        this.spiderPlayer = spiderPlayer;
        end = assetMgr.getBeamEnd();
        beam = assetMgr.getBeam();
        rectangle = new Rectangle(-90,100,80,60);
        height = 20;

    }

    /**
     * Check if the beam has hit the spider player or not
     * @param spider
     */
    public void checkCollision(Body spider){
        float spidX = spider.getPosition().x;
        float spidY = spider.getPosition().y;
        float rectX = rectangle.x;
        float rectY = rectangle.y;

        if((spidX-16) <= (rectX+80) && (spidX-16) >= (rectX) &&
                (spidY-16) <= (rectY+height) && (spidY-16) >= (rectY)){
            // Collision
            spiderPlayer.removeHealth(20);
            return;
        }
        if((spidX+16) <= (rectX+80) && (spidX+16) >= (rectX) &&
                (spidY+16) <= (rectY+height) && (spidY+16) >= (rectY)){
            // Collision
            spiderPlayer.removeHealth(20);
        }

    }

    /**
     * Beam has been fired so move it down starting from the position of the centipede's head
     */
    public void moveBeam(){
        startingY -= speedOfBeam;
        height += speedOfBeam;
        rectangle.set(startingX, startingY, 80, height);


    }

    /**
     * initialize the starting coordinates for beam attack
     * @param centipede
     */
    public void readyBeam(Body centipede){
        startingX = centipede.getPosition().x-30;
        startingY = centipede.getPosition().y-50;
        beamRunning = true;
    }

    public void renderBeam(SpriteBatch batch){
        batch.draw(beam, startingX, startingY, 70, height);
        batch.draw(end, startingX-9, startingY-8, 90, 30);

    }

    /**
     * We're resetting the height so the beam will be small and start at the top when fired again
     */
    public void resetHeight(){
        height = 20;
    }

    public Boolean beamRunning(){
        return beamRunning;
    }

    public float getY(){
        return rectangle.getY();
    }


}
