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
    public Spider(float width, float height, Body body, float health) {
        super(width, height, body);
        this.speed = 40f;
        this.width = width;
        this.height = height;
        this.setHealth(health);
        texture = new Texture(Gdx.files.internal("Spider_Sprite_0.png"));
        body.setUserData(this); // This is used for the identification of the body object for box2Ds collision detection
        System.out.println(health);

    }

    @Override
    public void dealDamage(Entity entity) {

    }

    @Override
    public void recoverHealth() {

    }

    @Override
    public void removeHealth(float damage) {
        float newHealth = health - damage;
        setHealth(newHealth);
    }

    @Override
    public void update() {
        x = body.getPosition().x - getWidth() /2; // Will be the center of the body
        y = body.getPosition().y - getHeight() /2;
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
        if(Gdx.input.isKeyPressed(Input.Keys.D)&&body.getPosition().x<(608-width)){
            velX = 100;
            direcLR = false;
            position = -90;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)&&body.getPosition().x>width){
            velX = -100;
            direcLR = true;
            direcUD = false;
            position = 90;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)&&body.getPosition().y<(448-height)){
            velY = 100;
            direcUD = false;
            position = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)&&body.getPosition().y>height){
            velY = -100;
            direcUD = true;
            position = -180;
        }

        // Web shooter
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            // Fire web shooter in the direction character is facing
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

    public int position(){
        return position;
    }


}
