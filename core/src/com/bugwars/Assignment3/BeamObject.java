package com.bugwars.Assignment3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.AssetManagerA3G2;
import com.bugwars.Helper.BodyHelperService;

public class BeamObject {

    private AssetManagerA3G2 assetMgr;
    private TextureRegion end, beam;
    private Rectangle rectangle;
    public float startingX, startingY, height;
    public boolean beamRunning = false;
    private int speedOfBeam = 2;

    public BeamObject(AssetManagerA3G2 asstMgr){
        this.assetMgr = asstMgr;
        end = assetMgr.getBeamEnd();
        beam = assetMgr.getBeam();
        rectangle = new Rectangle(-90,100,80,60);
        height = 20;

    }

    public void checkCollision(Body spider){
        float spidX = spider.getPosition().x;
        float spidY = spider.getPosition().y;
        float rectX = rectangle.x;
        float rectY = rectangle.y;

        if((spidX-16) <= (rectX+80) && (spidX-16) >= (rectX) &&
                (spidY-16) <= (rectY+60) && (spidY-16) >= (rectY)){
            // Collision
            System.out.println("Beam hit");
        }else if((spidX+16) <= (rectX+80) && (spidX+16) >= (rectX) &&
                (spidY+16) <= (rectY+60) && (spidY+16) >= (rectY)){
            // Collision
            System.out.println("Beam hit");
        }else{
            //keep moving
            //System.out.println("Beam miss");
        }

    }

    public void moveBeam(){
        startingY -= speedOfBeam;
        height += speedOfBeam;
        rectangle.set(startingX, startingY, 80, height);


    }

    public void readyBeam(Body centipede){
        startingX = centipede.getPosition().x;
        startingY = centipede.getPosition().y-60;
        beamRunning = true;
    }

    public void renderBeam(SpriteBatch batch){
        System.out.println("Render Beam");
        batch.draw(beam, startingX, startingY, 70, height);
        batch.draw(end, startingX-9, startingY-8, 90, 30);

    }

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
