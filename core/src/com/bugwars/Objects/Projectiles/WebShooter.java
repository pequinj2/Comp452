package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;

public class WebShooter {

    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("maps/WebbingTexures.atlas"));
    private TextureRegion webbingImg = new TextureRegion(textures.findRegion("Web_Shooter"));
    private int speed = 100;
    private float targetRadii, slowRadii;
    private Body web;

    public WebShooter(World world, Body web){
        this.web = web;
        slowRadii = 12;
        targetRadii = 6;
        web.setUserData(this);


    }

    private void fireWebbing(){

    }

    /**
     * Vector2 newPosition = target.sub(enemy);
     *             newPosition.nor();
     *             newPosition.scl(maxSpeed);
     *
     *             body.setLinearVelocity(newPosition); // Head toward the targets position at max speed
     *             butt.setLinearDamping(1.5f); // Damping placed to keep the end body somewhat in line with the head
     * @param player
     */
    public void followPlayer(Spider player, WebSac sac){
        float x = player.getX();
        float y = player.getY();
        float x1 = sac.getBody().getPosition().x;
        float y1 = sac.getBody().getPosition().y;
        /**
         * Use players 'position' variable to know which way player is facing
         *  -90  : RIGHT
         *   90  : LEFT
         *    0  : UP
         *  -180 : DOWN
         */
        float rot = player.getRotation();
        float timeToTarget = 0.1f;
        Vector2 playerPos = new Vector2(x, y);
        Vector2 sacPos = new Vector2(x1, y1);
        Vector2 newPosition = playerPos.sub(sacPos);
        newPosition.nor();
        newPosition.scl(speed);
        float distance = newPosition.len();
        System.out.println(distance + " new pos " + newPosition);
        if(distance < targetRadii){
            System.out.println("IDK");

        }



    }

    /**
     * Check if webbing collides
     */
    private void update(){

    }

    /**
     * Render the visual of the webbing
     */
    public void render(SpriteBatch batch){

        batch.draw(webbingImg,
                web.getPosition().x,
                web.getPosition().y,
                8, // Center of character
                8, // Center of character
                webbingImg.getRegionWidth(),
                webbingImg.getRegionHeight(),
                2, //Resize
                2,
                0); // Rotation);

    }

}
