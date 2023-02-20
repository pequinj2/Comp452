package com.bugwars.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.AssetManagerA3G2;
import com.bugwars.Objects.Entity;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Projectiles.Web;
import com.bugwars.Objects.Projectiles.WebShooter;

/**
 * Main playable character of the game - 'Spider Dude'
 * This class will extend from entity and use the Health and Damage interfaces for guides.
 * This playable object class will handle the input from the user to control it as well as
 * instantiate a 'WebShooter' object
 */
public class Spider extends Entity implements Health, Damage {

    private float velX, velY, x, y, width, height;
    private boolean direcLR, direcUD;
    private int rotation = 0;
    private World world;

    // Web Shooters
    private WebShooter webShots;
    private int webFlag = 0; // Flag that is used to indicate player web pickup has been grabbed
    private WebSac sac;

    // Damage buffer
    private float clock = 0;

    // Implement constructor
    public Spider(float width, float height, Body body, float health, World world) {
        super(width, height, body);
        this.speed = 40f;
        this.width = width;
        this.height = height;
        this.setHealth(health);
        this.world = world;
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

    /**
     * Health will only be removed after a 2 second cooldown to help with the player not being insta killed
     * @param damage
     */
    @Override
    public void removeHealth(float damage) {

        if(clock > 2) { // 2 second cooldown from the when the user takes damage so the user doesn't get insta killed
            float newHealth = health - damage;
            setHealth(newHealth);
            clock = 0;
        }
    }

    /**
     * Update body position, check user's input for character control and increment damage clock
     */
    @Override
    public void update() {
        clock += Gdx.graphics.getDeltaTime();
        x = body.getPosition().x - getWidth() /2; // Will be the center of the body
        y = body.getPosition().y - getHeight() /2;
        checkUserInput();

    }
    public void update2() {
        clock += Gdx.graphics.getDeltaTime();
        x = body.getPosition().x - getWidth() /2; // Will be the center of the body
        y = body.getPosition().y - getHeight() /2;
        checkUserInput2();

    }

    /**
     * If a
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch) {
        if(webFlag == 1 ) {
            addWebShot();
            webFlag = 0;
        }
        webShots.render(batch); // Render Web Shooter



    }

    /**
     * Get user input and direction for proper character texture animation and rotation
     */
    private void checkUserInput() {

        velX = 0;
        velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velX = 100;
            direcLR = false;
            rotation = -90;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velX = -100;
            direcLR = true;
            direcUD = false;
            rotation = 90;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velY = 100;
            direcUD = false;
            rotation = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
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

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void dispose(){
        webShots.dispose();
    }

    // *******************************************************************
    // ASSIGNMENT 3 GAME 2
    /**
     * Get user input and direction for proper character texture animation and rotation
     */
    private void checkUserInput2() {

        velX = 0;
        velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velX = 100;
            direcLR = false;
            rotation = -90;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)&&body.getPosition().x > 8){
            velX = -100;
            direcLR = true;
            direcUD = false;
            rotation = 90;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)&&body.getPosition().y < 260){
            velY = 100;
            direcUD = false;
            rotation = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
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

    public void setSound(AssetManagerA3G2 assetMgr){
        webShots.setAssignmentThree(assetMgr);
    }


}
