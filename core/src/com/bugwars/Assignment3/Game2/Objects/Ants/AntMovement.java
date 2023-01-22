package com.bugwars.Assignment3.Game2.Objects.Ants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;


import java.util.Random;

public class AntMovement {

    private float x, y,velX, velY, speed;
    private Body body;
    private int rotation, maxSpeed;
    private Random random = new Random();
    private double wanderAngle =0;
    private boolean addSub = false;


    public AntMovement(Body body){
        this.body = body;
        speed = 2;
        rotation = 0;
        maxSpeed = 50;
    }

    public void wander(){

        Vector2 currentPos = body.getPosition();
        float x = (random.nextInt(32+ 32)  -32);
        float y = (random.nextInt(32+ 32)  -32);
        if(y < 0){
            rotation = 180;
        }else{
            rotation = 0;
        }
        Vector2 targetVector = new Vector2(x, y);

        Vector2 temp = new Vector2(targetVector); // Set new variable to target Vector so we don;t change original value
        Vector2 newPosition;
        if(addSub){
            newPosition = temp.add(currentPos); // Displacement distance from enemy to target
            addSub = !addSub;
        }else{
            newPosition = temp.sub(currentPos); // Displacement distance from enemy to target
            addSub = !addSub;
        }

        newPosition.nor(); // Give the vector a proper direction
        newPosition.scl(maxSpeed);

        body.setLinearVelocity(newPosition); // Head toward the targets position at max speed

    }

    public float getX() {
        x = body.getPosition().x - 32 /2; // Will be the center of the body
        return x;

    }

    public float getY() {
        y = body.getPosition().y - 32 /2;
        return y;
    }

    public float getRotation(){
        return rotation;
    }
}
