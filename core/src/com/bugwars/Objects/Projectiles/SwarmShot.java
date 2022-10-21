package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class SwarmShot {

    private ArrayList<Projectile> projArray, killProj;
    private World world;
    private Body origin;

    public SwarmShot(World world, Body origin){
        this.world = world;
        this.origin = origin;
        this.projArray = new ArrayList<Projectile>();
        this.killProj = new ArrayList<Projectile>();


    }

    public void fireSwarm(){
        int count = 100;

        if(projArray.size() > 0){
            clearArray();
        }
        for(int i=0; i<count; i++){

            Projectile p = new Projectile(world, origin.getPosition().x, origin.getPosition().y, i);
            projArray.add(p);
        }

    }

    private void update(){
        if(projArray.size() > 0) { // if there are projectiles fired
            for (Projectile p : projArray) {
                if(p.getState() == Projectile.ProjState.KILL){
                    killProj.add(p);
                    projArray.remove(p);
                }

            }
        }
    }

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



    private void align(){

    }

    private void seek(){

    }
}
