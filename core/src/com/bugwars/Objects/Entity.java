package com.bugwars.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity {

    protected float x, y, width, height, speed; // All objects will need position, size and speed
    protected Body body; // All game objects will need a body

    public Entity (float width, float height, Body body){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.body = body;
        this.width = width;
        this.height = height;
        this.speed = 0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}
