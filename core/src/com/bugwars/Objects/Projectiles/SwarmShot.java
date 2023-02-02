package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

/**
 * This class works as the controller for the Centipede's AOE burst shot attack.
 * It will call and destroy Projectile objects every 20 seconds
 */
public class SwarmShot {

    private ArrayList<Projectile> projArray;
    private World world;
    private Body origin;
    private Projectile p;

    public SwarmShot(World world, Body origin){
        this.world = world;
        this.origin = origin;
        this.projArray = new ArrayList<Projectile>();
    }

    /**
     * Create 100 projectiles that will act as a swarm to surround and inflict damage to the Spider.
     * Projectile.java is called to create the projectiles which are stored in projArray
     */
    public void fireSwarm(){
        int count = 100;

        if(projArray.size() > 0){
            clearArray();
        }
        for(int i=0; i<count; i++){

            p = new Projectile(world, origin.getPosition().x, origin.getPosition().y, i);
            projArray.add(p);
        }

    }

    /**
     * Go through the projArray and get each state of the projectiles in there - if they're labelled
     * as FIRE then run the flock algorithm on them and render them on the screen
     * @param batch
     */
    public void render(SpriteBatch batch){

        if(projArray.size() > 0) { // if there are projectiles fired

            for (Projectile p : projArray) {
                //System.out.println(p.getState());
                if(p.getState() == Projectile.ProjState.FIRE){
                    p.flock(projArray);
                    p.render(batch);


                }
            }
        }

    }

    /**
     * Destroy the old projectile bodies and clear array
     */
    private void clearArray(){
        for (int i =0; i < projArray.size(); i++ ) {
            world.destroyBody(projArray.get(i).getBody());
        }
        projArray.clear();
    }

    public void dispose(){
        if(p != null) {
            p.dispose();
        }
    }


    public void render2(SpriteBatch batch) {
        if(projArray.size() > 0) { // if there are projectiles fired

            for (Projectile p : projArray) {
                //System.out.println(p.getState());
                if(p.getState() == Projectile.ProjState.FIRE){
                    p.flock2(projArray);
                    p.render(batch);


                }
            }
        }
    }

    /**
     * Create 100 projectiles that will act as a swarm to surround and inflict damage to the Spider.
     * Projectile.java is called to create the projectiles which are stored in projArray
     */
    public void fireSwarm2(){
        int count = 50;

        if(projArray.size() > 0){
            clearArray();
        }
        for(int i=0; i<count; i++){

            p = new Projectile(world, origin.getPosition().x, origin.getPosition().y, i);
            projArray.add(p);
        }

    }
}
