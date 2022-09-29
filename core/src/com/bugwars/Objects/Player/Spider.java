package com.bugwars.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Objects.Entity;

public class Spider extends Entity implements Health, Damage {

    private Texture texture;
    private float velX, velY, x, y, width, height;
    private boolean direcLR, direcUD;
    private int position = 0;

    // Implement constructor
    public Spider(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 40f;
        this.width = width;
        this.height = height;
        texture = new Texture(Gdx.files.internal("Spider_Sprite_0.png"));
    }

    @Override
    public void dealDamage(Entity entity) {

    }

    @Override
    public void recoverHealth() {

    }

    @Override
    public void update() {
        x = body.getPosition().x; // Will be the center of the body
        y = body.getPosition().y;
        //System.out.println("Print floats " + x + "  " + y);
        checkUserInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        texture = new Texture(Gdx.files.internal("Spider_Sprite_0.png"));

        //batch.draw(texture, 10, 10);

    }

    private void checkUserInput() {

        velX = 0;
        velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velX = 100;
            direcLR = false;
            position = -90;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velX = -100;
            direcLR = true;
            direcUD = false;
            position = 90;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velY = 100;
            direcUD = false;
            position = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velY = -100;
            direcUD = true;
            position = -180;
        }
        body.setLinearVelocity(velX * speed, velY * speed);

    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public boolean getFlipLR(){
        return direcLR;
    }
    public boolean getFlipUD(){
        return direcUD;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public int postion(){
        return position;
    }
}
