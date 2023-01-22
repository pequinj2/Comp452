package com.bugwars.Assignment3.Game2.Objects.Ants;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;

public class Ant {
    public AntMovement movement;
    private float x, y, rotation;
    private Body body;
    private boolean isDead = false;
    private long timer;

    public Ant(Body body){
        this.body = body;
        body.setUserData(this);
        movement = new AntMovement(body);
        timer = TimeUtils.millis();
    }

    public void update(){
        if((TimeUtils.millis()-timer) > 5000){
            timer=TimeUtils.millis();
            movement.wander();
            x = movement.getX();
            y = movement.getY();
        }
        //movement.wander();
        x = movement.getX();
        y = movement.getY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return movement.getRotation();
    }

    public Body getBody(){
        return body;
    }

    public boolean isDeadAnt(){
        return isDead;
    }

    public void setDead(){
        isDead = true;
    }
}
