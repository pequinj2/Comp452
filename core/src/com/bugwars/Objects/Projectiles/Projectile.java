package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.BodyHelperService;

import java.util.ArrayList;

public class Projectile {

    private float x, y;
    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("maps/WebbingTexures.atlas"));
    private TextureRegion projTexture = new TextureRegion(textures.findRegion("Projectile"));
    private Body projBod;
    private Vector2 velocity = new Vector2(10,10);
    public enum ProjState{
        FIRE,
        KILL
    }
    private ProjState currentState;
    private int maxSpeed = 50;
    private int id;
    private Vector2 acceleration = new Vector2(0,0);

    public Projectile(World world, float x, float y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        projBod = BodyHelperService.createProjectiles(world, x, y, 4f);
        projBod.setUserData(this);
        currentState = ProjState.FIRE;
    }

    private void update(){
        velocity.add(acceleration);
        projBod.setLinearVelocity(velocity);
    }

    public void render(SpriteBatch batch){
        update();

        batch.draw(projTexture,
                projBod.getPosition().x,
                projBod.getPosition().y,
                8, // Center of character
                8, // Center of character
                projTexture.getRegionWidth(),
                projTexture.getRegionHeight(),
                2, //Resize
                2,
                0); // Rotation);*/
    }

    /**
     * Flocking AI that groups 'separate', 'align' and 'cohesion' together to create the flocking
     * behavior.
     * These 3 AIs were derived from https://natureofcode.com/book/chapter-6-autonomous-agents/
     * I found this tutorial on flocking alot easier to understand versus the books description.
     * @param projArray
     */
    public void flock(ArrayList<Projectile> projArray){
        // Return the vectors so it;s easier to multiply different weights to get different behaviors
        Vector2 sep = separate(projArray);
        Vector2 ali = align(projArray);
        Vector2 coh = cohesion(projArray);

        // Multiply by a weight to get a different behavior
        sep.scl(5);
        ali.scl(2);
        coh.scl(.7f);

        // Add all the effects to acceleration
        acceleration.add(sep);
        acceleration.add(ali);
        acceleration.add(coh);


    }

    /**
     * Separate AI that will push the projectiles away from each other and stop them from colliding
     * @param projArray
     */
    private Vector2 separate(ArrayList<Projectile> projArray){

        Vector2 current = new Vector2(0,0);
        int count = 0;
        for (Projectile p: projArray){
            // Get the distance between current projectile and the others
            Vector2 distanceDiff = projBod.getPosition().sub(p.projBod.getPosition());

            // Check if the 2 projectiles are at the SAME spot and NOT the same one (id)
            if(distanceDiff.len() == 0.0 && id != p.id){
                // push projectiles away at random positions and velocity
                velocity.x = (float)Math.random() * 10;
                velocity.y = (float)Math.random() * 10;
            }
            // Check if the 2 projectiles are close together and NOT the same one (id)
            else if(distanceDiff.len() < 25.0 && id != p.id){
                Vector2 getNewPos = distanceDiff;
                getNewPos.nor(); // New vector that is away from the 'other'
                current.add(getNewPos);
                count++;
            }
        }

        // Only do this if there are projectiles close together
        if (count > 0){
            current.x = current.x/count;
            current.y = current.y/count;

            current.scl(maxSpeed); // new Desired velocity and direction is the average velocity of its neighbors

        }

        return current;

    }

    /**
     * Try to align the projectiles with their neighbors
     */
    private Vector2 align(ArrayList<Projectile> projArray){
        Vector2 sumVelocities = new Vector2(0,0);
        int sizeArr = projArray.size();
        int count = 0;
        // Go through projectile list and add up all the different velocities
        for(Projectile p : projArray){
            // Get the distance between current projectile and the others
            Vector2 distanceDiff = projBod.getPosition().sub(p.projBod.getPosition());

            // Check if the 2 projectiles are close together and NOT the same one (id)
            if(distanceDiff.len() < 5.0 && id != p.id && distanceDiff.len() > 0.0){
                sumVelocities.add(p.velocity);
                count++;
            }

        }

        if (count > 0) {
            sumVelocities.x = sumVelocities.x / count; // Average velocity
            sumVelocities.y = sumVelocities.y / count;
            sumVelocities.nor(); // Normalize to get the direction
            sumVelocities.scl(maxSpeed); // Go in this direction at maxSpeed
            sumVelocities.sub(velocity);
            return sumVelocities;

        }
        else{
            return new Vector2(0,0);
        }


    }

    /**
     * Calculate the average location of the projectiles and use that as a new target
     * Very similar to 'align' algorithm
     * @param projArray
     */
    private Vector2 cohesion(ArrayList<Projectile> projArray){
        Vector2 sumLocations = new Vector2(0,0);
        int sizeArr = projArray.size();
        int count = 0;
        // Go through projectile list and add up all the different velocities
        for(Projectile p : projArray){
            // Get the distance between current projectile and the others
            Vector2 distanceDiff = projBod.getPosition().sub(p.projBod.getPosition());

            // Check if the 2 projectiles are close together and NOT the same one (id)
            if(distanceDiff.len() < 5.0 && id != p.id  && distanceDiff.len() > 0.0){
                sumLocations.add(p.getBody().getPosition());
                count++;
            }

        }

        if (count > 0) {
            sumLocations.x = sumLocations.x / sizeArr; // Average velocity
            sumLocations.y = sumLocations.y / sizeArr;
            Vector2 newAcc = seek(sumLocations);

            return newAcc;
        }
        else {
            return new Vector2(0,0);
        }
    }

    /**
     * Seek AI to get the new target position based on current target's coordinates
     * @param target
     * @return Vector of new target position
     */
    private Vector2 seek(Vector2 target){
        Vector2 newPosition = projBod.getPosition().sub(target); // Displacement distance from enemy to target
        newPosition.nor(); // Give the vector a proper direction
        newPosition.scl(maxSpeed);
        newPosition.sub(velocity);
        return newPosition;
    }
    /**
     * Once a projectile hits the boarder OR the character set the State to KILL, this will inform the
     * system that this body needs to be destroyed
     */
    public void setProjState(){
        currentState = ProjState.KILL;
    }

    public ProjState getState(){
        return currentState;
    }

    public Body getBody(){
        return projBod;
    }
}
