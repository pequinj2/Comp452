package com.bugwars.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Entity;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Projectiles.WebShooter;

import java.util.ArrayList;

public class Spider extends Entity implements Health, Damage {

    private Texture texture;
    private float velX, velY, x, y, width, height;
    private boolean direcLR, direcUD;
    private int rotation = 0;
    private WebShooter web;
    private World world;

    // Web Shooters
    private WebShooter webShots;
    private int webFlag = 0; // Flag that is used to indicate player web pickup
    private WebSac sac;



    // Implement constructor
    public Spider(float width, float height, Body body, float health, World world) {
        super(width, height, body);
        this.speed = 40f;
        this.width = width;
        this.height = height;
        this.setHealth(health);
        this.world = world;
        texture = new Texture(Gdx.files.internal("Spider_Sprite_0.png"));
        body.setUserData(this); // This is used for the identification of the body object for box2Ds collision detection

        // Initialize the Web Shooter (think of this as the 'Gun')
        webShots = new WebShooter(world, this);


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

        //smallRadii.setTransform(body.getPosition(), 0);
        if(webFlag == 1 ) {
            addWebShot();
            webFlag = 0;
        }
        webShots.render(batch); // Render Web Shooter



    }

    private void checkUserInput() {

        velX = 0;
        velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)&&body.getPosition().x<(608-width)){
            velX = 100;
            direcLR = false;
            rotation = -90;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)&&body.getPosition().x>width){
            velX = -100;
            direcLR = true;
            direcUD = false;
            rotation = 90;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)&&body.getPosition().y<(448-height)){
            velY = 100;
            direcUD = false;
            rotation = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)&&body.getPosition().y>height){
            velY = -100;
            direcUD = true;
            rotation = -180;
        }

        // Web shooter
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            // Fire web shooter in the direction character is facing
            if(webShots.getArraySize() != 0){
                webShots.fireWebbing();
            }

        }
        body.setLinearVelocity(velX * speed, velY * speed);

    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getRotation(){
        return rotation;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public WebShooter getWeb(){
        return web;
    }
    public void setWebFlag(WebSac sac){
        this.sac = sac;
        webFlag = 1;
    }

    /**
     * Get the number of webs available ~ get the # of 'bullets' our web shooter has
     * @return
     */
    public int getArraySize(){
        return webShots.getArraySize();
    }

    /**
     * Picked-up ammo, load up the web shooter
     */
    public void addWebShot(){
        webShots.loadWeb(world, sac);

    }





}
