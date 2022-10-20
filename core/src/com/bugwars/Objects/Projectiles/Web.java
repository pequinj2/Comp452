package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Pickups.WebSac;

import java.util.ArrayList;

public class Web {

    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("maps/WebbingTexures.atlas"));
    private TextureRegion webbingImg = new TextureRegion(textures.findRegion("Web_Shooter"));
    private Body web, radii;
    private WebSac sac;
    private float speed = 500f;
    private float maxAcceleration = 4000f;
    private World world;
    public enum WebState{
        FOLLOW,
        FIRE,
        KILL
    }
    public WebState current;
    private Vector2 result;
    private ArrayList<Web> webs;

    public Web(World world, WebSac sac, Body radii, ArrayList<Web> webs){
        this.radii = radii; // This is the sensor attached to the spider to indicate where the web projectile needs to go
        this.world = world;
        this.webs = webs; // Passing the address because we only want the one list
        web = BodyHelperService.createProjectiles(world, sac.getBody().getPosition().x, sac.getBody().getPosition().y, 4f);
        web.setUserData(this);
        this.sac = sac;
        current = WebState.FOLLOW;
    }

    /**
     *  Arrival AI
     *  Called in WebShooter.java render()
     * @param
     */
    public void followPlayer(){
        float x = radii.getPosition().x;
        float y = radii.getPosition().y;
        float x1 = web.getPosition().x;
        float y1 = web.getPosition().y;

        /**
         * Use players 'position' variable to know which way player is facing
         *  -90  : RIGHT
         *   90  : LEFT
         *    0  : UP
         *  -180 : DOWN
         */
        float timeToTarget = 0.1f;
        Vector2 radiiPos = new Vector2(x, y); // Make coordinates proper Vector2s
        Vector2 currentPos = new Vector2(x1, y1);

        // Get displacement distance from enemy to target
        Vector2 newDirection = radiiPos.sub(currentPos);
        Vector2 temp = newDirection;
        Vector2 vel;

        float distance = newDirection.len();
        float targetRadii = 4f;
        float largeRadii = 64f;
        float targetSpeed;


        if(distance < targetRadii){
            result = temp.nor().scl(0); // At target, have no velocity
        }
        else {

            if (distance > largeRadii) {
                targetSpeed = speed;

            } else {
                targetSpeed = speed * (distance / largeRadii) ;
            }

            vel = temp.nor().scl(targetSpeed); // Target Velocity
            Vector2 currentVelocity = radii.getLinearVelocity(); // Current object velocity

            result = vel.sub(currentVelocity);
            result.scl(1 / timeToTarget);

            // Throttle acceleration if too fast
            if (result.len() > maxAcceleration) {
                result.nor();
                result.scl(maxAcceleration);

            }

        }

    }

    /**
     * Render the visual of the webbing
     */
    public void render(SpriteBatch batch){
        web.setLinearVelocity(result);
        batch.draw(webbingImg,
                web.getPosition().x,
                web.getPosition().y,
                8, // Center of character
                8, // Center of character
                webbingImg.getRegionWidth(),
                webbingImg.getRegionHeight(),
                2, //Resize
                2,
                0); // Rotation);*/



    }

    public Body getBody(){
        return web;
    }

    public void setResult(Vector2 result) {
        this.result = result;
    }

    public void setStateKill(){
        current = WebState.KILL;
    }

}
